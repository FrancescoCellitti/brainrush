package com.brainrush.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.brainrush.Repository.CategoryRepository;
import com.brainrush.model.Category;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository catRepo;

    public List<Category> findAll() {
        return catRepo.findAll();
    }

    public Category getById(int id) {
        return catRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "nessuna Categoria trovata"));
    }

    public Category create(Category category) {
        if (category == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "categoria non valida");
        }
        return catRepo.save(category);
    }

    public Category update(Integer id, Category category) {
        getById(id);
        category.setId(id);
        return catRepo.save(category);
    }

    public void deleteById(Integer id) {
        Category category = getById(id);
        if (category == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "categoria non valida");
        }
        catRepo.delete(category);
    }
}
