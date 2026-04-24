package com.brainrush.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

import com.brainrush.Service.CategoryService;
import com.brainrush.model.Category;

@RestController
@RequestMapping("/api/category")
public class CategoryRestController {
    
    @Autowired
    private CategoryService catservice;

    @GetMapping 
    public ResponseEntity<List<Category>> index(){
        return ResponseEntity.ok(catservice.findAll());
    }
}
