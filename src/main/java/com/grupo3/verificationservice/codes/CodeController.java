package com.grupo3.verificationservice.codes;

import com.grupo3.verificationservice.codes.dto.EmailDto;
import com.grupo3.verificationservice.codes.service.ICodeCacheService;
import com.grupo3.verificationservice.codes.service.ICodeEmailService;
import com.grupo3.verificationservice.codes.service.IRandomCodeService;
import com.grupo3.verificationservice.user.service.IUserCacheService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import shareddtos.usersmodule.auth.MessageDto;
import shareddtos.usersmodule.auth.UserDto;

@RestController
@RequestMapping("/codes")
public class CodeController {
    @Autowired private IRandomCodeService randomCodeService;
    @Autowired private ICodeCacheService codeCacheService;
    @Autowired private ICodeEmailService codeEmailService;
    @Autowired private IUserCacheService userCacheService;

    @PostMapping("/create")
    public ResponseEntity<MessageDto> createCode(@Valid @RequestBody EmailDto emailDto){
        // Buscar usuario en caché
        UserDto userDto = userCacheService.getUser(emailDto.getEmail());
        if(userDto == null){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El tiempo de verificación a finalizado o no te has registrado correctamente");
        }

        // Crear código de verificación
        String code = randomCodeService.generateCode();

        // Almacenar código en caché
        codeCacheService.saveCode(emailDto.getEmail(), code);

        // Enviar correo electrónico
        codeEmailService.sendCodeEmail(emailDto.getEmail(), code);

        return ResponseEntity.ok(new MessageDto("Código de verificación enviado correctamente al correo electrónico"));
    }
}
