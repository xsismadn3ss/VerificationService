package com.grupo3.verificationservice.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * <h2>Confirm Code DTO</h2>
 * <p>DTO utilizado para validar código de
 * confirmación al crear una nueva cuenta</p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmAccountDto {
    /**
     * Correo electrónico al que fue enviado el código
     */
    @NotBlank(message = "el campo email no debe estar vacío")
    @Email(message = "el email debe ser valido")
    private String email;

    /**
     * Código de verificación ingresado por el usuario
     */
    @NotBlank(message = "el campo code no puede estar vacío")
    @Length(min = 6, max = 6, message = "el campo code no debe ser de solo 6 dígitos")
    private String code;
}
