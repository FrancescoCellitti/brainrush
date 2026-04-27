package com.brainrush.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainrush.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByCategory_id(Integer categoryId);
}
