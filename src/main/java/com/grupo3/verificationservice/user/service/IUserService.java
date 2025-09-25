package com.grupo3.verificationservice.user.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import shareddtos.usersmodule.auth.SimpleUserDto;
import shareddtos.usersmodule.auth.UserDto;

import java.util.Optional;

public interface IUserService {

    /**
     * Buscar usuario por username, devuelve nulo o datos
     * de un usuario
     * @param username nombre de usuario
     * @return datos de usuario o nulo
     */
    Optional<SimpleUserDto> findByUsername(String username);


    /**
     * Buscar usuario por email
     * @param email email
     * @return nulo o datos de usuario
     */
    Optional<SimpleUserDto> findByEmail(String email);

    /**
     * Crear usuario a partir de un userDto
     * @param userDto DTO de usuario
     * @return datos básicos del usuario
     */
    SimpleUserDto createUser(UserDto userDto);
}
