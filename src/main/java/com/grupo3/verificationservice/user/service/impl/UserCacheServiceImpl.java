package com.grupo3.verificationservice.user.service.impl;

import com.grupo3.verificationservice.user.dto.UserRegisterDto;
import com.grupo3.verificationservice.user.service.IUserCacheService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class UserCacheServiceImpl implements IUserCacheService {
    private final RedisTemplate<String, Object> redisTemplate;

    public UserCacheServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void saveUser(UserRegisterDto userDto) {
        redisTemplate.opsForValue().set(userDto.getEmail(), userDto, Duration.ofDays(7));
    }

    @Override
    public UserRegisterDto getUser(String email) {
        return (UserRegisterDto) redisTemplate.opsForValue().get(email);
    }
}
