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

import com.brainrush.Service.QuestionService;
import com.brainrush.model.Question;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/question")
public class QuestionController {
    
    @Autowired
    private QuestionService questionService;

    @GetMapping
    public String read(Model model){
        List<Question> questions = questionService.findAll();
        model.addAttribute("question", questions);
        return "question/index";
    }

    @GetMapping("/add")
    public String add(Model model){
        model. addAttribute("question", new Question());
        return "question/add";
    }


    @PostMapping
    public String create(@Valid @ModelAttribute("question") Question formQuestion, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", formQuestion);
            return "redirect:/add";
        }
        questionService.create(formQuestion);
        return "redirect:/question";
    }

    @GetMapping("/update/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){
     Question question = questionService.getById(id);
     model.addAttribute("question", question);
     return "question/update";    
    }


    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Integer id, @ModelAttribute("question") Question formQuestion, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("question", formQuestion);
            return "question/update";
        }
        questionService.update(id, formQuestion);        
        return "redirect:/question";
    }


     @GetMapping("/delete/{id}")
    public String toDelete(@PathVariable("id") Integer id, Model model){
        Question question = questionService.getById(id);
        model.addAttribute("question", question);
        return "question/delete";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        questionService.deleteById(id);
        return "redirect:/question";
    }
}
