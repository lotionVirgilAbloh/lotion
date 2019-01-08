package org.lotionvirgilabloh.lotionwebzuul;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Serializable> redisCacheTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Serializable> template = new RedisTemplate<> ();
        template.setKeySerializer(new StringRedisSerializer ());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer ());
        template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer ());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer ());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
