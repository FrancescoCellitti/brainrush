package com.brainrush.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainrush.model.QuizResult;

public interface QuizResultRepository extends JpaRepository<QuizResult, Integer> {
    
}
