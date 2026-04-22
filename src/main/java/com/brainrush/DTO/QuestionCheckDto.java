package com.brainrush.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QuestionCheckDto {

    @NotBlank
    private String answer;
    
}
