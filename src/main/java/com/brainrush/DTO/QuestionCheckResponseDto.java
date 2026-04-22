package com.brainrush.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionCheckResponseDto {
    private boolean correct;
    private String message;
}
