package com.brainrush.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthRegisterResponseDto {
    private Integer id;
    private String username;
    private String message;
}
