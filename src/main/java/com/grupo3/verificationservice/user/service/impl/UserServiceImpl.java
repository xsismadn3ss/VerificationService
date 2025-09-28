package com.grupo3.verificationservice.user.service.impl;

import com.grupo3.verificationservice.user.entity.User;
import com.grupo3.verificationservice.user.repository.UserRepository;
import com.grupo3.verificationservice.user.service.IUserService;
import org.springframework.stereotype.Service;
import shareddtos.usersmodule.auth.SimpleUserDto;
import shareddtos.usersmodule.auth.UserDto;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserDto getUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getName());
        userDto.setLastName(user.getLastname());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }

    @Override
    public Optional<SimpleUserDto> findByUsername(String username) {
        Optional<User> user = this.userRepository.findByUsername(username);
        return user.<Optional<SimpleUserDto>>map(value -> Optional.of(getUserDto(value))).orElse(null);
    }

    @Override
    public Optional<SimpleUserDto> findByEmail(String email) {
        Optional<User> user = this.userRepository.findByEmail(email);
        return user.<Optional<SimpleUserDto>>map(value -> Optional.of(getUserDto(value))).orElse(null);
    }

    @Override
    public SimpleUserDto createUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName());
        user.setLastname(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return getUserDto(this.userRepository.save(user)).toSimpleUserDto();
    }
}
