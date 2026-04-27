package com.brainrush.DTO;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class QuizSubmitDto {
    @NotNull
    private Integer categoryId;

    @NotNull
    @Size(min = 1)
    private List<QuestionAnswerDto> answers;

    @Data
    public static class QuestionAnswerDto {
        @NotNull
        private Integer questionId;
        @NotBlank
        private String answer;
    }
}
