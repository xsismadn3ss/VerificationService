package com.grupo3.verificationservice.user.service;

import com.grupo3.verificationservice.user.dto.UserRegisterDto;

public interface IUserCacheService {
    void saveUser(UserRegisterDto userDto);
    UserRegisterDto getUser(String email);
}
