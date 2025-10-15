package com.grupo3.verificationservice.user.controller;

import com.grupo3.verificationservice.user.dto.ConfirmAccountDto;
import com.grupo3.verificationservice.codes.service.ICodeCacheService;
import com.grupo3.verificationservice.encrypt.client.EncryptServiceClient;
import com.grupo3.verificationservice.user.service.IUserCacheService;
import com.grupo3.verificationservice.user.service.IUserEmailService;
import com.grupo3.verificationservice.user.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import shareddtos.usersmodule.auth.EncryptDto;
import shareddtos.usersmodule.auth.MessageDto;
import shareddtos.usersmodule.auth.SimpleUserDto;
import shareddtos.usersmodule.auth.UserDto;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired private EncryptServiceClient encryptServiceClient;
    @Autowired private IUserService userService;
    @Autowired private IUserCacheService userCacheService;
    @Autowired private IUserEmailService userEmailService;
    @Autowired private ICodeCacheService codeCacheService;

    @PostMapping("/register")
    @Operation(
            summary = "Registra un usuario en el sistema",
            description = "Devuelve un mensaje, notificando que los datos han sido guardados y están pendientes de verificación"
    )
    public ResponseEntity<MessageDto> registerUser(@Valid @RequestBody UserDto userDto){
        // validar si hay una cuenta existente
        Optional<SimpleUserDto> username_match = userService.findByUsername(userDto.getUsername());
        Optional<SimpleUserDto> email_match = userService.findByEmail(userDto.getEmail());
        if(username_match != null || email_match != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario ya existe");
        }

        // verificar si no esta en cache
        if(userCacheService.getUser(userDto.getEmail()) != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya esta en uso");
        }

        // encriptar contraseña
        String hashedPassword = encryptServiceClient.encrypt(new EncryptDto(userDto.getPassword())).getMessage();
        userDto.setPassword(hashedPassword);

        // guardar datos en cache
        userCacheService.saveUser(userDto);

        // enviar respuesta
        return ResponseEntity.ok(new MessageDto("Cuenta registrada correctamente, verifica tu correo electrónico"));
    }

    @PostMapping("/persist")
    @Operation(
            summary = "Persistir cuenta",
            description = "Devuelve un mensaje, notificando que la cuenta ha sido persistida y se ha verificado"
    )
    public ResponseEntity<MessageDto> persistUser(@Valid @RequestBody ConfirmAccountDto confirmAccountDto){
        // Buscar código en cache
        String code = codeCacheService.getCode(confirmAccountDto.getEmail());
        if(code == null){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El tiempo de confirmación ha finalizado o no se ha solicitado un código de verificación.");
        }

        // Validar código
        if(!code.equals(confirmAccountDto.getCode())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código de verificación incorrecto");
        }

        // Buscar usuario en caché
        UserDto userDto = userCacheService.getUser(confirmAccountDto.getEmail());
        if(userDto == null){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El tiempo de verificación a finalizado o no te has registrado correctamente"
            );
        }

        // Guardar en la base de datos
        userService.createUser(userDto);

        // enviar correo electrónico
        userEmailService.sendGreetingEmail(userDto.getEmail(), userDto.getFirstName());

        // eliminar código
        codeCacheService.deleteCode(confirmAccountDto.getEmail());

        return ResponseEntity.ok(new MessageDto("Cuenta verificada con éxito, inicia sesión para usar las funciones del sistema"));
    }
}
