package com.brainrush.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainrush.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    
}
