package com.brainrush.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthLoginResponseDto {
    private Integer id;
    private String username;
    private List<String> roles;
}
