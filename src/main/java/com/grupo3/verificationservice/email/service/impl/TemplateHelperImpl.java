package com.grupo3.verificationservice.email.service.impl;

import com.grupo3.verificationservice.email.service.ITemplateHelper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.nio.file.Files;

@Service
public class TemplateHelperImpl implements ITemplateHelper {

    @Override
    public String getTemplate(String templateName) {
        try{
            ClassPathResource resource = new ClassPathResource("template/" + templateName);
            return new String(Files.readAllBytes(resource.getFile().toPath()));
        } catch (Exception e) {
            throw new RuntimeException("Error cargando plantilla: " + templateName, e);
        }
    }
}
