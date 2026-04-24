package com.brainrush.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.brainrush.Service.CategoryService;
import com.brainrush.model.Category;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService catService;

    @GetMapping
    public String read(Model model) {
        List<Category> categories = catService.findAll();
        model.addAttribute("categoria", categories);
        return "category/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("category", new Category());
        return "category/add";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("category") Category formCategory, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("category", formCategory);
            return "category/add";
        }
        catService.create(formCategory);
        return "redirect:/category";
    }

    @GetMapping("/update/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Category category = catService.getById(id);
        model.addAttribute("category", category);
        return "category/update";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        Category category = catService.getById(id);
        model.addAttribute("category", category);
        return "category/show";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Integer id, @Valid @ModelAttribute("category") Category formCategory,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("category", formCategory);
            return "category/update";
        }
        catService.update(id, formCategory);
        return "redirect:/category";

    }


    @GetMapping("/delete/{id}")
    public String toDelete(@PathVariable("id") Integer id, Model model){
        Category category = catService.getById(id);
        model.addAttribute("category", category);
        return "redirect:/category";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        catService.deleteById(id);
        return "redirect:/category";
    }
}
