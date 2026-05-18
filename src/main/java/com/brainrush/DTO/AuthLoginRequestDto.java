package com.brainrush.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthLoginRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
