package com.grupo3.verificationservice.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import shareddtos.usersmodule.auth.SimpleUserDto;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto extends SimpleUserDto {
    @NotEmpty(message = "el campo password no puede estar vacío")
    @Length(min = 8, message = "el campo password no puede ser mayor a 8 caracteres")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
            message = "La contraseña debe contener al menos una letra mayúscula, una minúscula y un número"
    )
    private String password;

    public SimpleUserDto toSimpleUserDto(){
        return new SimpleUserDto(
                this.getId(),
                this.getUsername(),
                this.getFirstName(),
                this.getLastName(),
                this.getEmail()
        );
    }
}
