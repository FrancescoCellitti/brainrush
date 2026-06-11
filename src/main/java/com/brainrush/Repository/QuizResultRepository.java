package com.brainrush.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainrush.model.QuizResult;

public interface QuizResultRepository extends JpaRepository<QuizResult, Integer> {
    List<QuizResult> findByUsersId(Integer userId);
}
