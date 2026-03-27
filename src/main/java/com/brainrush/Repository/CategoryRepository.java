package com.brainrush.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainrush.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
    
}
