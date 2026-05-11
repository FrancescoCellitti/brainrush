package com.brainrush.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brainrush.DTO.QuestionDto;
import com.brainrush.Service.QuestionService;
import com.brainrush.model.Question;

@RestController
@RequestMapping("/api/question")
@CrossOrigin(origins = "http://localhost:5173")
public class QuestionRestController {

    @Autowired
    private QuestionService Qserv;

    @GetMapping
    public ResponseEntity<List<QuestionDto>> index() {
        List<QuestionDto> dtos = Qserv.findAll().stream()
                .map(this::toDto)
                .toList();

                
                return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDto> show(@PathVariable Integer id) {
        return ResponseEntity.ok(toDto(Qserv.getById(id)));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<QuestionDto>> byCategory(@PathVariable Integer categoryId){
        List<QuestionDto> dtos = Qserv.findByCategoryId(categoryId).stream().map(this::toDto).toList();
        return ResponseEntity.ok(dtos);
    }



    private QuestionDto toDto(Question q) {
        QuestionDto dto = new QuestionDto();
        dto.setId(q.getId());
        dto.setTitle(q.getTitle());
        dto.setOptionA(q.getOptionA());
        dto.setOptionB(q.getOptionB());
        dto.setOptionC(q.getOptionC());
        dto.setOptionD(q.getOptionD());
        dto.setDifficulty(q.getDifficulty());
        return dto;
    }
}
