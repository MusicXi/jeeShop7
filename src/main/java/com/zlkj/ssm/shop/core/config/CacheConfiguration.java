package com.zlkj.ssm.shop.core.config;
import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zlkj.ssm.shop.core.cache.provider.EhcacheCacheProvider;
import com.zlkj.ssm.shop.core.cache.provider.RedisCacheProvider;
import com.zlkj.ssm.shop.core.cache.provider.SystemManager;
/**
 *    基于普通的Cache实现
 */
@Configuration
public class CacheConfiguration {
	@Autowired
	private Environment env;
	@Bean
	public EhcacheCacheProvider initEhcacheCacheProvider() {
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		EhcacheCacheProvider provider = new EhcacheCacheProvider();
		provider.setCacheName(env.getProperty("cache.file.name"));
		provider.setConfigLocation(resolver.getResource(env.getProperty("cache.file.path")));
		return provider;
	}
	
	@Bean(name="systemManager")
	public SystemManager initSystemManager(){
		SystemManager systemManager=new SystemManager();
		systemManager.setCacheProvider(initEhcacheCacheProvider());
		return systemManager;
	}
	@Bean
	@ConfigurationProperties(prefix="spring.redis")
    public RedisConnectionFactory jedisConnectionFactory(){
        return new JedisConnectionFactory();
    }
    @Bean
    public RedisTemplate<String, Serializable> redisTemplate() {
    	RedisTemplate<String, Serializable> redisTemplate=new RedisTemplate<String, Serializable>();
    	redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }   
    @Bean
    public RedisTemplate<Object, Object>redisTemplate(RedisConnectionFactory redisConnectionFactory){
    	RedisTemplate<Object,Object>redisTemplate=new RedisTemplate<>();
    	redisTemplate.setConnectionFactory(jedisConnectionFactory());
    	/**
    	 * 使用 Jackson2JsonRedisSerializer 序列化和反序列化redis Value值
    	 */
    	Jackson2JsonRedisSerializer<Object>jackson2JsonRedisSerializer=new Jackson2JsonRedisSerializer<>(Object.class);
    	ObjectMapper mapper=new ObjectMapper();
    	mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    	mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    	jackson2JsonRedisSerializer.setObjectMapper(mapper);
    	redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
    	/**
    	 * 使用StringRedisSerializer 来序列化和反序列化redis Key值
    	 */
    	redisTemplate.setKeySerializer(new StringRedisSerializer());
    	redisTemplate.afterPropertiesSet();
    	return redisTemplate;
    }
    @Bean
    public RedisCacheProvider  redisCacheProvider(){
    	RedisCacheProvider redisCacheProvider=new RedisCacheProvider();
    	redisCacheProvider.setRedisTemplate(redisTemplate());
    	return redisCacheProvider;
    }
}
