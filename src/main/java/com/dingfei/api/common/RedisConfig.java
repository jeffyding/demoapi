package com.dingfei.api.common;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * RedisConfig
 */
@Configuration
public class RedisConfig {
	@Value("${spring.redis.host:192.168.1.191}")
	private String redisHost;

	@Value("${spring.redis.port:63781}")
	private String redisPort;

	@Value("${spring.redis.password:password}")
	private String redisPassword;

	@Value("${spring.redis.host2:192.168.1.192}")
	private String redisHost2;

	@Value("${spring.redis.port2:63782}")
	private String redisPort2;

	@Value("${spring.redis.password2:}")
	private String redisPassword2;

	@Bean
	@Primary
	public StringRedisTemplate stringRedisTemplate() {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setHostName(redisHost);
		jedisConnectionFactory.setPort(Integer.parseInt(redisPort));
		jedisConnectionFactory.setPassword(redisPassword);
		jedisConnectionFactory.setUsePool(true);
		jedisConnectionFactory.setDatabase(0);
		jedisConnectionFactory.afterPropertiesSet();
		return new StringRedisTemplate(jedisConnectionFactory);
	}

	@Bean
	@Qualifier("stringRedisTemplate2")
	public StringRedisTemplate stringRedisTemplate2() {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setHostName(redisHost2);
		jedisConnectionFactory.setPort(Integer.parseInt(redisPort2));
		jedisConnectionFactory.setPassword(redisPassword2);
		jedisConnectionFactory.setUsePool(true);
		jedisConnectionFactory.setDatabase(0);
		jedisConnectionFactory.afterPropertiesSet();
		return new StringRedisTemplate(jedisConnectionFactory);
	}
}
