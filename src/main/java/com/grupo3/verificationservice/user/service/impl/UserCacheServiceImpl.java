package com.grupo3.verificationservice.user.service.impl;

import com.grupo3.verificationservice.user.service.IUserCacheService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import shareddtos.usersmodule.auth.UserDto;

import java.time.Duration;

@Service
public class UserCacheServiceImpl implements IUserCacheService {
    private final RedisTemplate<String, Object> redisTemplate;

    public UserCacheServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void saveUser(UserDto userDto) {
        redisTemplate.opsForValue().set(userDto.getEmail(), userDto, Duration.ofDays(7));
    }

    @Override
    public UserDto getUser(String email) {
        return (UserDto) redisTemplate.opsForValue().get(email);
    }
}
