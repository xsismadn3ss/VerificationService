package com.grupo3.verificationservice.user.service;

import shareddtos.usersmodule.auth.UserDto;

public interface IUserCacheService {
    void saveUser(UserDto userDto);
    UserDto getUser(String email);
}
