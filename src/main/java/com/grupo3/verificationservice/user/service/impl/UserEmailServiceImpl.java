package com.grupo3.verificationservice.user.service.impl;


import com.grupo3.verificationservice.email.service.IEmailHelper;
import com.grupo3.verificationservice.email.service.ITemplateHelper;
import com.grupo3.verificationservice.user.service.IUserEmailService;
import org.springframework.stereotype.Service;

@Service
public class UserEmailServiceImpl implements IUserEmailService {
    private final ITemplateHelper templateHelper;
    private final IEmailHelper emailHelper;

    public UserEmailServiceImpl(ITemplateHelper templateHelper, IEmailHelper emailHelper) {
        this.templateHelper = templateHelper;
        this.emailHelper = emailHelper;
    }

    @Override
    public void sendGreetingEmail(String email) {
        String htmlContent = templateHelper.getTemplate("welcome-email.html");
        emailHelper.sendEmail(
                email,
                "!Bienvenido a nuestro servicio",
                // TODO: agregar reemplazo para poner el nombre de la persona o su email en la plantilla
                htmlContent
        );
    }

}
