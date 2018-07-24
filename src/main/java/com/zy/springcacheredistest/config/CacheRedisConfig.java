package com.zy.springcacheredistest.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zy.springcacheredistest.constants.CacheRedisConstants;

@Configuration
@EnableCaching//开启缓存支持
public class CacheRedisConfig extends CachingConfigurerSupport {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Bean(name = CacheRedisConstants.CACHE_REDIS_MANAGER_NAME)
	public CacheManager cacheManager() {
		this.logger.info("init cacheRedisManager...");
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate());
		cacheManager.setDefaultExpiration(60); //设置key-value超时时间
		List<String> cacheNames = new ArrayList<String>();
		cacheNames.add(CacheRedisConstants.USER_CACHE_NAME);
		cacheManager.setCacheNames(cacheNames);
		return cacheManager;
	}
	
	/**

	 * 注入 RedisConnectionFactory

	 */
	@Autowired 
	private RedisConnectionFactory redisConnectionFactory;

	
	@SuppressWarnings("rawtypes")
	@Bean
    public RedisTemplate redisTemplate() {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        initDomainRedisTemplate(redisTemplate);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
	
	/**

	 * 设置数据存入 redis 的序列化方式

	 * </br>redisTemplate 序列化默认使用的jdkSerializeable, 存储二进制字节码, 所以自定义序列化类

	 *

	 * @param redisTemplate

	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initDomainRedisTemplate(RedisTemplate redisTemplate) {
		//key序列化方式;（不然会出现乱码;）,但是如果方法上有Long等非String类型的话，会报类型转换错误；
		//所以在没有自己定义key生成策略的时候，以下这个代码建议不要这么写，可以不配置或者自己实现ObjectRedisSerializer
		//或者JdkSerializationRedisSerializer序列化方式;
		// 使用Jackson2JsonRedisSerialize 替换默认序列化
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		// string结构的数据，设置value的序列化规则和 key的序列化规则
		//StringRedisSerializer解决key中午乱码问题。//Long类型不可以会出现异常信息;
//		redisTemplate.setKeySerializer(new StringRedisSerializer());
		//value乱码问题：Jackson2JsonRedisSerializer
		redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		//设置Hash结构的key和value的序列化方式
		//redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
		//redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
	}

	
}
