package com.brainrush.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRegisterRequestDto {

    @NotBlank(message = "Username non può essere vuoto")
    @Size(min = 3, max = 50, message = "Username deve essere tra 3 e 50 caratteri")
    private String username;

    @NotBlank(message = "Password non può essere vuota")
    @Size(min = 6, message = "Password deve avere almeno 6 caratteri")
    private String password;

    @NotBlank(message = "Conferma password non può essere vuota")
    private String confirmPassword;
}
