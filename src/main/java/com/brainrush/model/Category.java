package com.brainrush.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "category")
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(min = 2, max = 50, message = "Il nome della categoria deve essere tra 2 e 50 caratteri")
    private String name;

    @NotBlank
    @Size(min = 10, max = 200, message = "la descrizione deve essere tra 10 e 200 caratteri")
    private String description;

    /* Relations */

    @OneToMany(mappedBy = "category")
    @JsonBackReference
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    @JsonBackReference
    private List<QuizResult> quizResults = new ArrayList<>();



    /* Getter e setter  e toStign generati automaticamente da Lombok  con l'annotation @Data*/
}
