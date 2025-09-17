package com.grupo3.verificationservice.codes.service;

public interface ICodeEmailService {
    /**
     * Enviar correo de código de verificación creado
     * @param email correo electrónico al que esta dirígidp
     * @param code código de verificación a enviar
     */
    void sendCodeEmail(String email, String code);
}
