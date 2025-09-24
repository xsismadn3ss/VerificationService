package com.grupo3.verificationservice.email.service;

public interface IEmailHelper {
    void sendEmail(String to, String subject, String body);
}
