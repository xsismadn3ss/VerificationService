package com.grupo3.verificationservice.user.service.impl;


import com.grupo3.verificationservice.user.service.IUserEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.file.Files;
import java.security.SecureRandom;

@Service
public class UserEmailServiceImpl implements IUserEmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final SecureRandom random = new SecureRandom();

    public UserEmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    private String loadTemplate(String templateName) {
        try {
            ClassPathResource resource = new ClassPathResource("templates/" + templateName);
            return new String(Files.readAllBytes(resource.getFile().toPath()));
        } catch (Exception e) {
            throw new RuntimeException("Error cargando plantilla: " + templateName, e);
        }
    }



    //Falta pulirlo y implementarlo para que se haga automatico al hacer la peticion
    @Override
    public void verificationCode(String email) throws MessagingException {

        // Inyectar la variable en la plantilla
        Integer codigo = generate8DigitCode();

        Context context = new Context();
        context.setVariable("code", codigo);
        String htmlContent = templateEngine.process("email-verification", context);


        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setTo(email);
        helper.setSubject("Tu código de verificación");
        helper.setText(htmlContent, true);
        mailSender.send(mimeMessage);

    }

    @Override
    public void sendGreetingEmail(String email) {
        //cargamos la plantilla de correo
        String htmlContent = loadTemplate("welcome-email.html");
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject("¡Bienvenido a nuestra aplicación!");
            helper.setText(htmlContent, true); // true para HTML

            mailSender.send(message);

            //Si el envio del correo funciona, posteriormente mandara su codigo temporal para validar la cuenta.
            verificationCode(email);
        } catch (Exception e) {
            throw new RuntimeException("Error enviando correo", e);
        }
    }

    //generados de numeros aletorios de 8 cifras
    public Integer generate8DigitCode() {
        int number = random.nextInt(90_000_000) + 10_000_000; // siempre 8 dígitos
        return number;
    }

}
