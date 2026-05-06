package com.brainrush.RestController;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainrush.DTO.QuestionCheckDto;
import com.brainrush.DTO.QuestionCheckResponseDto;
import com.brainrush.DTO.QuizSubmitDto;
import com.brainrush.Service.QuestionService;
import com.brainrush.Service.QuizResultService;
import com.brainrush.model.Category;
import com.brainrush.model.Question;
import com.brainrush.model.QuizResult;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/quiz-result")
@CrossOrigin(origins = "http://localhost:5173")
public class QuizResultRestController {
    @Autowired
    private QuizResultService resultService;

    @Autowired
    private QuestionService questionService;

    @GetMapping
    public ResponseEntity<List<QuizResult>> index() {
        return ResponseEntity.ok(resultService.findAll());
    }

    @PostMapping("/{id}/check")
    public ResponseEntity<QuestionCheckResponseDto> checkAnswer(@PathVariable Integer id,
            @RequestBody @Valid QuestionCheckDto dto) {
        Question question = questionService.getById(id);
        boolean isCorrect = question.getSolution().equalsIgnoreCase(dto.getAnswer());

        QuestionCheckResponseDto response = new QuestionCheckResponseDto(
                isCorrect,
                isCorrect ? "Risposta corretta" : "Risposta sbagliata");

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<QuizResult> saveResult(@RequestBody @Valid QuizSubmitDto dto) {
        int correct = 0;
        for (QuizSubmitDto.QuestionAnswerDto a : dto.getAnswers()) {
            Question q = questionService.getById(a.getQuestionId());
            if (q.getSolution().equalsIgnoreCase(a.getAnswer())) {
                correct++;
            }
        }

        QuizResult result = new QuizResult();
        result.setTotalQuestions(dto.getAnswers().size());
        result.setCorrectAnswer(correct);
        result.setScore(correct * 10); 
        result.setPlayedAt(Instant.now()); 
        result.setCategory(new Category());
        result.getCategory().setId(dto.getCategoryId());

        return ResponseEntity.status(HttpStatus.CREATED).body(resultService.create(result));
    }

}
