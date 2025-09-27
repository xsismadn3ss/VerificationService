package com.grupo3.verificationservice.codes.service.impl;

import com.grupo3.verificationservice.codes.service.ICodeCacheService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class CodeCacheServiceImpl implements ICodeCacheService {
    private final RedisTemplate<String, Object> redisTemplate;

    public CodeCacheServiceImpl(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void saveCode(String email, String code) {
        redisTemplate.opsForValue().set(email+"_code", code, Duration.ofMinutes(5));
    }

    @Override
    public String getCode(String email) {
        return (String) redisTemplate.opsForValue().get(email+"_code");
    }

    @Override
    public void deleteCode(String email) {
        redisTemplate.delete(email+"_code");
    }
}
