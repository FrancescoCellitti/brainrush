package com.brainrush.RestController;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.brainrush.DTO.QuestionCheckDto;
import com.brainrush.DTO.QuestionCheckResponseDto;
import com.brainrush.DTO.QuizSubmitDto;
import com.brainrush.Repository.UserRepository;
import com.brainrush.Security.DatabaseUserDetails;
import com.brainrush.Security.DatabaseUserDetailsService;
import com.brainrush.Service.QuestionService;
import com.brainrush.Service.QuizResultService;
import com.brainrush.model.Category;
import com.brainrush.model.Question;
import com.brainrush.model.QuizResult;
import com.brainrush.model.User;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/quiz-result")
@CrossOrigin(origins = "http://localhost:5173")
public class QuizResultRestController {
    @Autowired
    private QuizResultService resultService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserRepository userRepo;

    @GetMapping
    public ResponseEntity<List<QuizResult>> index() {
        return ResponseEntity.ok(resultService.findAll());
    }

    @GetMapping("/me")
    public ResponseEntity<List<QuizResult>> myResult(@AuthenticationPrincipal DatabaseUserDetails userDetails) {
        return ResponseEntity.ok(resultService.findByUserId(userDetails.getId()));
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
    public ResponseEntity<QuizResult> saveResult(@RequestBody @Valid QuizSubmitDto dto,
            @AuthenticationPrincipal DatabaseUserDetails userDetails) {
        int correct = 0;
        for (QuizSubmitDto.QuestionAnswerDto a : dto.getAnswers()) {
            Question q = questionService.getById(a.getQuestionId());
            if (q.getSolution().equalsIgnoreCase(a.getAnswer())) {
                correct++;
            }
        }

        Integer userId = (userDetails != null) ? userDetails.getId() : null;
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "utente non autenticato");
        }

        User currentUser = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "utente non trovato"));

        QuizResult result = new QuizResult();
        result.setTotalQuestions(dto.getAnswers().size());
        result.setCorrectAnswer(correct);
        result.setScore(correct * 10);
        result.setPlayedAt(Instant.now());
        result.setCategory(new Category());
        result.getCategory().setId(dto.getCategoryId());
        result.setUsers(currentUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(resultService.create(result));
    }

}
