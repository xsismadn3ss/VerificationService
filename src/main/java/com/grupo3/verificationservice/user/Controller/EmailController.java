package com.grupo3.verificationservice.user.Controller;


import com.grupo3.verificationservice.user.service.IUserEmailService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final IUserEmailService emailService;

    public EmailController(IUserEmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<String> sendEmail(@RequestBody String email){
        emailService.sendGreetingEmail(email);
        return new ResponseEntity<>("Correo Enviado Exitosamente",HttpStatus.OK);
    }
}
