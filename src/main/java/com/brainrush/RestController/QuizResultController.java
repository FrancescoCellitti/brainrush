package com.brainrush.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainrush.DTO.QuestionCheckDto;
import com.brainrush.DTO.QuestionCheckResponseDto;
import com.brainrush.Service.QuestionService;
import com.brainrush.Service.QuizResultService;
import com.brainrush.model.Question;
import com.brainrush.model.QuizResult;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/quiz-results")
public class QuizResultController {
    @Autowired
    private  QuizResultService resultService;

    @Autowired
    private QuestionService questionService;


    @GetMapping
    public ResponseEntity<List<QuizResult>> index(){
         return ResponseEntity.ok(resultService.findAll());
    }

    @PostMapping("/{id}/check")
    public ResponseEntity<QuestionCheckResponseDto> checkAnswer(@PathVariable Integer id, @RequestBody @Valid QuestionCheckDto dto){
        Question question = questionService.getById(id);
        boolean isCorrect = question.getSolution().equalsIgnoreCase(dto.getAnswer());

        QuestionCheckResponseDto response = new QuestionCheckResponseDto(
                isCorrect,
                isCorrect ? "Risposta corretta" : "Risposta sbagliata"
        );

        return ResponseEntity.ok(response);
    }
}
