package com.brainrush.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brainrush.model.User;

public interface AuthRepository extends JpaRepository< User, Integer> {
    
}
