package com.grupo3.verificationservice.user.service;

import jakarta.mail.MessagingException;

public interface IUserEmailService {
    /**
     * Enviar mensaje de bienvenida al crear una nueva
     * cuenta en el sistema
     * @param email
     */
    void sendGreetingEmail(String email);

    void verificationCode(String email) throws MessagingException;
}
