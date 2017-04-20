package com.dingfei.api.aop;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import com.dingfei.api.cache.CacheService;
import com.dingfei.api.common.UserCacheInfo;
import com.dingfei.api.common.annotation.Auth;
import com.dingfei.api.common.annotation.EventLog;
import com.dingfei.api.controller.BaseController;
import com.dingfei.api.util.APIUtil;
import com.dingfei.api.util.ApiException;
import com.dingfei.api.util.RedisConstants;

/**
 * ControllerAspect
 */
@Aspect
public class ControllerAspect extends BaseController {
	private static final Logger LOGGER = Logger.getLogger(ControllerAspect.class);
	@Autowired
	private CacheService cacheService;

	/**
	 * controllerMethodCall
	 */
	@Pointcut("execution(public * com.dingfei.api.controller.*.*(..)) || execution(public * com.dingfei.api.controller.*.*.*(..))")
	public void controllerMethodCall() {
	}

	/**
	 * logSuccessEvent
	 * 
	 * @param joinPoint
	 *        JoinPoint
	 */
	@AfterReturning(value = "controllerMethodCall()")
	public void logSuccessEvent(JoinPoint joinPoint) {
		Method method = getControllerMethod(joinPoint);
		EventLog eventLog = method.getAnnotation(EventLog.class);
		if (eventLog != null) {
			HttpServletRequest request = getRequestArg(joinPoint.getArgs());
			UserCacheInfo userCacheInfo = getUserCacheInfo(request);
			String result = APIUtil.getEventStr(eventLog.action(), eventLog.value(), "", request, "",
					userCacheInfo.getOrgId(), userCacheInfo.getUserId());
			LOGGER.trace(result);
		}
	}

	/**
	 * logFailEvent
	 * 
	 * @param joinPoint
	 *        JoinPoint
	 * @param ex
	 *        Exception
	 */
	@AfterThrowing(value = "controllerMethodCall()", throwing = "ex")
	public void logFailEvent(JoinPoint joinPoint, Exception ex) {
		Method method = getControllerMethod(joinPoint);
		EventLog eventLog = method.getAnnotation(EventLog.class);
		if (eventLog != null && ex instanceof ApiException) {
			HttpServletRequest request = getRequestArg(joinPoint.getArgs());
			UserCacheInfo userCacheInfo = getUserCacheInfo(request);
			String result = APIUtil.getEventStr(eventLog.action(), eventLog.value(), ((ApiException) ex).getErrorKey(),
					request, "", userCacheInfo.getOrgId(), userCacheInfo.getUserId());
			LOGGER.trace(result);
		}
	}

	private HttpServletRequest getRequestArg(Object[] args) {
		HttpServletRequest request = null;
		if (args != null && args.length > 0) {
			for (Object arg : args) {
				if (arg instanceof HttpServletRequest) {
					request = (HttpServletRequest) arg;
					break;
				}
			}
		}
		if (request == null) {
			throw new ApiException("global.ServiceInternalError");
		}
		return request;
	}

	/**
	 * authorize
	 * 
	 * @param joinPoint
	 *        JoinPoint
	 */
	@Before(value = "controllerMethodCall()")
	public void authorize(JoinPoint joinPoint) {
		Method method = getControllerMethod(joinPoint);
		Auth auth = method.getAnnotation(Auth.class);
		if (auth != null) {
			HttpServletRequest request = getRequestArg(joinPoint.getArgs());
			verifyToken(request);
			if (auth.authorize()) {
				// checkPermission(auth.value(), auth.action(), token);
			}
		}
	}

	public static final String SUPERADMIN_ROLEID = "8008";

	protected void checkPermission(String resourceName, int operationMethod, String token) {
		String roleId = cacheService.getPropertyValueByToken(token, RedisConstants.REDIS_ROLE_ID);
		if (SUPERADMIN_ROLEID.equals(roleId)) {
			return;
		}
		String strPermissionArray = cacheService.getPropertyValueByToken(token,
				RedisConstants.REDIS_NAME_PERMISSIONLIST);
		JSONArray permissionArray = JSONArray.fromObject(strPermissionArray);
		if (strPermissionArray == null) {
			throw new ApiException("no permission");
		}
		boolean hasPermission = hasPermission(resourceName, operationMethod, permissionArray);
		// not add permission on method so set true temporily
		if (!hasPermission) {
			throw new ApiException("no permission");
		}
	}

	private boolean hasPermission(String resourceName, int operationMethod, JSONArray permissionArray) {
		JSONObject permissionJson;
		boolean hasPermission = false;
		String resourceStr;
		for (int i = 0; i < permissionArray.size(); i++) {
			permissionJson = permissionArray.getJSONObject(i);
			resourceStr = permissionJson.getString("resourceName") + permissionJson.getString("operationMethod");
			if ((resourceName + operationMethod).equals(resourceStr)) {
				hasPermission = true;
			}

		}
		return hasPermission;
	}

	private Method getControllerMethod(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		return signature.getMethod();
	}
}
