package com.brainrush.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.brainrush.Repository.QuizResultRepository;
import com.brainrush.model.QuizResult;

@Service
public class QuizResultService {
    
     @Autowired
    private QuizResultRepository resultRepo;

    public List<QuizResult> findAll(){
        return resultRepo.findAll();
    }

    public QuizResult getById(Integer id){
        return resultRepo.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "risposta non trovata"));
    }

    public List<QuizResult> getAllById(List<Integer> id){
        return resultRepo.findAllById(id);
    }

    public QuizResult create(QuizResult result){
        if (result == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "risposta non valida");
        }
        return resultRepo.save(result);
    }


    public QuizResult update(Integer id, QuizResult result){
        getById(id);
        result.setId(id);
        return resultRepo.save(result);
    }

    public void deleteById(Integer id){
        QuizResult result = getById(id);
        if (result == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "risposta non valida");
        }
        resultRepo.delete(result);
    }
}
