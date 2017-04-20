package com.dingfei.api.controller;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.dingfei.api.cache.CacheService;
import com.dingfei.api.common.UserCacheInfo;
import com.dingfei.api.util.APIConstants;
import com.dingfei.api.util.APIUtil;
import com.dingfei.api.util.ApiException;
import com.dingfei.api.util.RedisConstants;

public class BaseController {

	@Autowired
	private CacheService cacheService;

	protected void verifyToken(HttpServletRequest request) {

		String token = request.getHeader(APIConstants.HEADER_NAME_TOKEN);
		if (StringUtils.isBlank(token)) {
			throw new ApiException("apis.auth.token.required");
		}

		// 临时身份验证
		String HEADER_TEMP_TOKEN = "9898a8d5222676b311385bd21da8f33da0fa6f6895b91e5397987e5c13e357c2";
		if (HEADER_TEMP_TOKEN.equals(token)) {
			return;
		}

		String userId = getUserIdByToken(token);
		if (StringUtils.isBlank(userId)) {
			throw new ApiException("apis.auth.token.invalid");
		} else {
			updateUserCache(token, userId);
		}
	}

	private void updateUserCache(String token, String userId) {
		if (cacheService.getExpireTime(RedisConstants.REDIS_NAME_TOKEN + token, TimeUnit.MILLISECONDS) < 60 * 1000) {
			cacheService.setExpireTime(RedisConstants.REDIS_NAME_TOKEN + token, APIConstants.DEFAULT_TIMEOUNT,
					TimeUnit.HOURS);
			cacheService.setExpireTime(RedisConstants.REDIS_NAME_UIDS_TOKEN + userId, APIConstants.DEFAULT_TIMEOUNT,
					TimeUnit.HOURS);
			cacheService.setExpireTime(RedisConstants.REDIS_NAME_UIDS_PROPERTY + userId, APIConstants.DEFAULT_TIMEOUNT,
					TimeUnit.HOURS);
		}
	}

	protected String getUserIdByToken(String token) {
		String userId = null;
		if (StringUtils.isNotBlank(token)) {
			userId = cacheService.getPropertyValueByToken(token, RedisConstants.REDIS_NAME_USERID);
		}
		return userId;
	}

	protected UserCacheInfo getUserCacheInfo(HttpServletRequest request) {
		return getUserCacheInfo(request.getHeader(APIConstants.HEADER_NAME_TOKEN));
	}

	protected UserCacheInfo getUserCacheInfo(String token) {
		Map<Object, Object> tokenCacheMap = null;
		if (StringUtils.isNotBlank(token)) {
			tokenCacheMap = cacheService.getUserMapByToken(token);
		}
		UserCacheInfo result;
		if (tokenCacheMap != null) {
			String orgId = (String) tokenCacheMap.get(RedisConstants.REDIS_NAME_ORGID);
			String orgName = (String) tokenCacheMap.get(RedisConstants.REDIS_NAME_ORGNAME);
			String userId = (String) tokenCacheMap.get(RedisConstants.REDIS_NAME_USERID);
			String roleId = (String) tokenCacheMap.get(RedisConstants.REDIS_ROLE_ID);
			result = new UserCacheInfo(orgId, orgName, userId, roleId, "", "");
		} else {
			result = new UserCacheInfo("", "", "", "", "", "");
		}
		return result;
	}

	protected PageRequest getPageRequest(HttpServletRequest request) {
		int offset = APIUtil.str2Int(request.getParameter(APIConstants.PARAM_NAME_OFFSET), 0);
		int limit = APIUtil.str2Int(request.getParameter(APIConstants.PARAM_NAME_LIMIT), APIConstants.DEFAULT_LIMIT);
		if (limit > 100) {
			limit = 100;
		}
		String orderBy = request.getParameter(APIConstants.PARAM_NAME_ORDERBY);
		if (StringUtils.isBlank(orderBy)) {
			orderBy = "createTime";
		}
		String direction = request.getParameter(APIConstants.PARAM_NAME_DIRECTION);
		Sort.Direction d = Sort.Direction.DESC;
		if ("ASC".equalsIgnoreCase(direction)) {
			d = Sort.Direction.ASC;
		}
		Sort st = new Sort(d, orderBy);

		return new PageRequest(offset / limit, limit, st);
	}

}
