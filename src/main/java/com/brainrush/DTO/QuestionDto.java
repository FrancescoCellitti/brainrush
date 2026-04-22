package com.brainrush.DTO;

import lombok.Data;

@Data
public class QuestionDto {
    private Integer id;
    private String title;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String difficulty;
}
