package com.dingfei.api.controller;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dingfei.api.bean.MatchHistory4Get;
import com.dingfei.api.cache.CacheService;
import com.dingfei.api.common.UserCacheInfo;
import com.dingfei.api.common.annotation.Auth;
import com.dingfei.api.common.annotation.EventLog;
import com.dingfei.api.dao.entity.MatchHistory;
import com.dingfei.api.service.MatchService;
import com.dingfei.api.util.APIConstants;
import com.dingfei.api.util.BeanUtil;
import com.dingfei.api.util.CommonUtil;

@RestController
public class SignController extends BaseController {

	@Autowired
	private MatchService faceService;
	@Autowired
	private CacheService cacheService;

	@RequestMapping(value = "/targets/{targetId}/histories", method = RequestMethod.GET, produces = APIConstants.MEDIATYPE)
	@ResponseStatus(HttpStatus.OK)
	public Object listTarget(HttpServletRequest request, @PathVariable String targetId) {
		Page<MatchHistory> page = faceService.findByTargetId(targetId, getPageRequest(request));
		return BeanUtil.convert2PagingListExt(page, MatchHistory.class, MatchHistory4Get.class);
	}

	@RequestMapping(value = "/sign", method = RequestMethod.POST, produces = APIConstants.MEDIATYPE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@EventLog(value = APIConstants.RESOURCE_OBJ_SIGN, action = APIConstants.RESOURCE_ACTION_CREATESINGLE)
	@Auth(value = APIConstants.RESOURCE_OBJ_SIGN, action = APIConstants.RESOURCE_ACTION_CREATESINGLE, authorize = false)
	public Object sign(HttpServletRequest request, @RequestParam String targetId, @RequestParam String sessionId) {
		UserCacheInfo cacheUser = getUserCacheInfo(request);
		String userId = cacheUser.getUserId();
		String signKey = getSignKey4User(userId, targetId, sessionId);
		String signTime = CommonUtil.now(CommonUtil.SOURCE_1);
		cacheService.setValue(signKey, signTime);
		cacheService.setExpireTime(signKey, 24, TimeUnit.HOURS);

		String mapKey = getSignKey4Target(targetId, sessionId);
		Map<String, String> cacheMap = cacheService.getHashOps().entries(mapKey);
		cacheMap.put(userId, signTime);
		cacheService.setHash(mapKey, cacheMap);
		cacheService.setExpireTime(mapKey, APIConstants.DEFAULT_TIMEOUNT, TimeUnit.HOURS);

		JSONObject result = new JSONObject();
		result.put("signTime", signTime);
		return result;
	}

	@RequestMapping(value = "/sign", method = RequestMethod.GET, produces = APIConstants.MEDIATYPE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@EventLog(value = APIConstants.RESOURCE_OBJ_SIGN, action = APIConstants.RESOURCE_ACTION_GETSINGLE)
	@Auth(value = APIConstants.RESOURCE_OBJ_SIGN, action = APIConstants.RESOURCE_ACTION_GETSINGLE, authorize = false)
	public Object getSign(HttpServletRequest request, @RequestParam String targetId, @RequestParam String sessionId,
			@RequestParam(required = false) Integer range) {
		UserCacheInfo cacheUser = getUserCacheInfo(request);
		String userId = cacheUser.getUserId();
		String signKey = getSignKey4User(userId, targetId, sessionId);
		String signTime = cacheService.getPropertyValueByPropertyName(signKey);

		JSONObject result = new JSONObject();
		result.put("signTime", signTime);
		return result;
	}

	private String getSignKey4User(String userId, String targetId, String sessionId) {
		String signKey = "component:sign:user:" + userId + "_" + targetId + "_" + sessionId;
		return signKey;
	}

	private String getSignKey4Target(String targetId, String sessionId) {
		String mapKey = "component:sign:target:" + targetId + "_" + sessionId;
		return mapKey;
	}

}
