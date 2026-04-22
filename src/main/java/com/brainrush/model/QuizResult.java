package com.brainrush.model;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "result")
public class QuizResult {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotNull
    private Integer score;
   
    @NotNull
    private Integer totalQuestions;

    @NotNull
    private Integer correctAnswer;

    
    @NotNull
    private Instant playedAt;
    

    /* Relations */
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    /* Getter e setter  e toString generati automaticamente da Lombok  con l'annotation @Data*/

}
