package com.brainrush.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
@Entity
@Table(name = "question")
public class Question {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotBlank
    @Size
    private String title;

    
    @NotBlank
    @Size
    private String optionA;

    
    @NotBlank
    @Size
    private String OptionB;

    
    @NotBlank
    @Size
    private String OptionC;

    
    @NotBlank
    @Size
    private String optionD;

    
    @NotBlank
    @Size
    private String solution;

    
    @NotBlank
    @Size
    private String difficulty;

    
    @NotBlank
    @Size
    private String creation;

    
    /*Relations */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private  Category category;


    /* Getter e setter  e toStign generati automaticamente da Lombok  con l'annotation @Data*/
    
    
}
