package com.grupo3.verificationservice.codes.service.impl;

import com.grupo3.verificationservice.codes.service.ICodeEmailService;
import com.grupo3.verificationservice.email.service.IEmailHelper;
import com.grupo3.verificationservice.email.service.ITemplateHelper;
import org.springframework.stereotype.Service;

@Service
public class CodeEmailServiceImpl implements ICodeEmailService {
    private final IEmailHelper emailHelper;
    private final ITemplateHelper templateHelper;

    public CodeEmailServiceImpl(IEmailHelper emailHelper, ITemplateHelper templateHelper) {
        this.emailHelper = emailHelper;
        this.templateHelper = templateHelper;
    }

    @Override
    public void sendCodeEmail(String email, String code) {
        String htmlContent = templateHelper.getTemplate("email-verification.html");
        emailHelper.sendEmail(
                email,
                "Código de verificación",
                htmlContent.replace("{{code}}", code)
        );
    }
}
