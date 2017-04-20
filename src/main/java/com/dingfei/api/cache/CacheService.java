package com.dingfei.api.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.dingfei.api.util.RedisConstants;

/**
 * The Class CacheService.
 */
@Service
public class CacheService {
	/**
	 * The string redis template.
	 */
	@Autowired
	@Qualifier("stringRedisTemplate")
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	@Qualifier("stringRedisTemplate2")
	private StringRedisTemplate stringRedisTemplate2;

	/**
	 * getPropertyValue
	 * 
	 * @param propertyName String
	 * @return String
	 */
	public String getPropertyValue(String propertyName) {
		return stringRedisTemplate2.opsForValue().get(propertyName);
	}

	/**
	 * Gets the user map by token.
	 * 
	 * @param token the token
	 * @return the user map by token
	 */
	public Map<Object, Object> getUserMapByToken(String token) {
		return stringRedisTemplate.opsForHash().entries(RedisConstants.REDIS_NAME_TOKEN + token);
	}

	/**
	 * getUserProfile
	 * 
	 * @param userId String
	 * @return Map
	 */
	public Map<Object, Object> getUserProfile(String userId) {
		return stringRedisTemplate.opsForHash().entries(RedisConstants.REDIS_NAME_UIDS_PROPERTY + userId);
	}

	/**
	 * Gets the property value by token.
	 * 
	 * @param token the token
	 * @param propertyName the property name
	 * @return the property value by token
	 */
	public String getPropertyValueByToken(String token, String propertyName) {
		return (String) stringRedisTemplate.opsForHash().get(RedisConstants.REDIS_NAME_TOKEN + token, propertyName);
	}

	public void setValue(String key, String value) {
		stringRedisTemplate.opsForValue().set(key, value);
	}

	/**
	 * setValue
	 * 
	 * @param key String
	 * @param value String
	 * @param ttl long
	 * @param timeUnit TimeUnit
	 */
	public void setValue(String key, String value, long ttl, TimeUnit timeUnit) {
		stringRedisTemplate.opsForValue().set(key, value, ttl, timeUnit);
	}

	/**
	 * Gets the expire time.
	 * 
	 * @param key the key
	 * @param timeUnit the time unit
	 * @return the expire time
	 */
	public long getExpireTime(String key, TimeUnit timeUnit) {
		return stringRedisTemplate.getExpire(key, timeUnit);
	}

	/**
	 * Sets the expire time.
	 * 
	 * @param key the key
	 * @param value the value
	 * @param timeUnit the time unit
	 */
	public void setExpireTime(String key, int value, TimeUnit timeUnit) {
		stringRedisTemplate.expire(key, value, timeUnit);
	}

	/**
	 * setExpireAt
	 * 
	 * @param key String
	 * @param date Date
	 */
	public void setExpireAt(String key, final Date date) {
		stringRedisTemplate.expireAt(key, date);
	}

	/**
	 * Sets the hash value.
	 * 
	 * @param key the key
	 * @param hashKey the hash key
	 * @param hashValue the hash value
	 */
	public void setHashValue(String key, String hashKey, String hashValue) {
		if (hashValue != null) {
			stringRedisTemplate.opsForHash().put(key, hashKey, hashValue);
		}
	}

	/**
	 * setHash
	 * 
	 * @param key String
	 * @param map Map
	 */
	public void setHash(String key, Map<String, String> map) {
		stringRedisTemplate.opsForHash().putAll(key, map);
	}

	/**
	 * Gets the hash value.
	 * 
	 * @param key the key
	 * @param hashKey the hash key
	 * @return the hash value
	 */
	public String getHashValue(String key, String hashKey) {
		return (String) stringRedisTemplate.opsForHash().get(key, hashKey);
	}

	public boolean hasHashKey(String key, String hashKey) {
		return this.stringRedisTemplate.opsForHash().hasKey(key, hashKey);
	}

	/**
	 * Gets the list.
	 * 
	 * @return the list
	 */
	public ListOperations<String, String> getList() {
		return stringRedisTemplate.opsForList();
	}

	/**
	 * getSetOps
	 * 
	 * @return SetOperations
	 */
	public SetOperations<String, String> getSetOps() {
		return stringRedisTemplate.opsForSet();
	}

	/**
	 * getHashOps
	 * 
	 * @return HashOperations
	 */
	public HashOperations<String, String, String> getHashOps() {
		return stringRedisTemplate.opsForHash();
	}

	/**
	 * Delete.
	 * 
	 * @param key the key
	 */
	public void delete(String key) {
		stringRedisTemplate.delete(key);
	}

	/**
	 * delete
	 * 
	 * @param keys Collection
	 */
	public void delete(Collection<String> keys) {
		stringRedisTemplate.delete(keys);
	}

	public void deleteHashKey(String key, String hashKey) {
		this.stringRedisTemplate.opsForHash().delete(key, hashKey);
	}

	/**
	 * Gets the property value by property name.
	 * 
	 * @param propertyName the property name
	 * @return the property value by property name
	 */
	public String getPropertyValueByPropertyName(String propertyName) {
		return stringRedisTemplate.opsForValue().get(propertyName);
	}

	/**
	 * deleteUserCache
	 * 
	 * @param userId String
	 */
	public void deleteUserCache(String userId) {
		String key = RedisConstants.REDIS_NAME_UIDS_TOKEN + userId;
		long size = getList().size(key);
		if (size > 0) {
			List<String> list = getList().range(key, 0, size);
			for (String token : list) {
				delete(RedisConstants.REDIS_NAME_TOKEN + token);
			}
		}
		delete(key);
	}

	/**
	 * getUserCache
	 * 
	 * @param userId String
	 */
	public List<String> getUserCache(String userId) {
		List<String> list = new ArrayList<String>();
		String key = RedisConstants.REDIS_NAME_UIDS_TOKEN + userId;
		long size = getList().size(key);
		if (size > 0) {
			list = getList().range(key, 0, size);
		}
		return list;
	}

	private ValueOperations<String, String> getValOps() {
		return stringRedisTemplate.opsForValue();
	}

	/**
	 * setNX
	 * 
	 * @param key String
	 * @param value String
	 * @return boolean
	 */
	public boolean setNX(String key, String value) {
		return getValOps().setIfAbsent(key, value);
	}

	/**
	 * getLock
	 * 
	 * @param key String
	 * @return String
	 */
	public String getLock(String key) {
		return getValOps().get(key);
	}

	/**
	 * getSet
	 * 
	 * @param key String
	 * @param value String
	 * @return String
	 */
	public String getSet(String key, String value) {
		return getValOps().getAndSet(key, value);
	}

	/**
	 * releaseLock
	 * 
	 * @param key String
	 */
	public void releaseLock(String key) {
		delete(key);
	}

	/**
	 * validInternalCaptcha
	 * 
	 * @param setKey String
	 * @param captcha String
	 * @return boolean
	 */
	public boolean validInternalCaptcha(String setKey, String captcha) {
		boolean valid = false;
		if (StringUtils.isNotBlank(setKey) && StringUtils.isNotBlank(captcha)) {
			valid = stringRedisTemplate.opsForSet().isMember(setKey, captcha);
		}
		if (valid) {
			stringRedisTemplate.opsForSet().remove(setKey, captcha);
		}
		return valid;
	}

	/**
	 * addInternalCaptcha
	 * 
	 * @param setKey String
	 * @param captcha String
	 * @return boolean
	 */
	public void addInternalCaptcha(String setKey, String captcha) {
		if (StringUtils.isNotBlank(setKey) && StringUtils.isNotBlank(captcha)) {
			stringRedisTemplate.opsForSet().add(setKey, captcha);
			setExpireTime(setKey, 60, TimeUnit.SECONDS);
		}
	}

}
