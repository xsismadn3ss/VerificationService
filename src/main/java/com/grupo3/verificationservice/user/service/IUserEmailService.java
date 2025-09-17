package com.grupo3.verificationservice.user.service;

public interface IUserEmailService {
    /**
     * Enviar mensaje de bienvenida al crear una nueva
     * cuenta en el sistema
     * @param email
     */
    void sendGreetingEmail(String email);
}
