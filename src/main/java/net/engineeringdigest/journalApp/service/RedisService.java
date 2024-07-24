package net.engineeringdigest.journalApp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T get(String key, Class<T> entityClass) {
        try {
        Object object = redisTemplate.opsForValue().get(key);
        ObjectMapper objectMapper = new ObjectMapper();
            if (object != null) {
                return objectMapper.readValue(object.toString(), entityClass);
            } else {
                return null;
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void set(String key, Object object, Long ttl) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonValue = null;
        try {
            jsonValue = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        redisTemplate.opsForValue().set(key, jsonValue, ttl, TimeUnit.SECONDS);
    }

}
