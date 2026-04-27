package com.brainrush.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.brainrush.Repository.QuestionRepository;
import com.brainrush.model.Question;

@Service
public class QuestionService {
    
    @Autowired
    private QuestionRepository questionRepo;

    public List<Question> findAll(){
        return questionRepo.findAll();
    }

    public Question getById(int id){
        return questionRepo.findById(id)
        .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "domanda non trovata"));
    }

    public List<Question> findByCategoryId(Integer categoryId) {
    return questionRepo.findByCategory_id(categoryId);
}

    public List<Question> getAllById(List<Integer> id){
        if(id == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "domanada non valida");
        }
        return questionRepo.findAllById(id);
    }

    public Question create(Question question){
        if (question == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "domanda non valida");
        }
        return questionRepo.save(question);
    }


    public Question update(Integer id, Question question){
        getById(id);
        question.setId(id);
        return questionRepo.save(question);
    }

    public void deleteById(Integer id){
        Question question = getById(id);
        if (question == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "domanda non valida");
        }
        questionRepo.delete(question);
    }
}
