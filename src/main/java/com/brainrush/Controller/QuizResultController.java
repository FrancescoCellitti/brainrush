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

import com.brainrush.Service.QuizResultService;
import com.brainrush.model.QuizResult;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/quiz%20Result")
public class QuizResultController {
    
      @Autowired
    private QuizResultService resultService;

    @GetMapping
    public String read(Model model){
        List<QuizResult> results = resultService.findAll();
        model.addAttribute("results", results);
        return "QuizResult/index";
    }

    @GetMapping("/add")
    public String add(Model model){
        model. addAttribute("result", new QuizResult());
        return "quizResult/add";
    }


    @PostMapping
    public String create(@Valid @ModelAttribute("quizResult") QuizResult formResult, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("result", formResult);
            return "redirect:/add";
        }
        resultService.create(formResult);
        return "redirect:/quiz%20Result";
    }

    @GetMapping("/update/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){
     QuizResult result = resultService.getById(id);
     model.addAttribute("result", result);
     return "QuizResult/update";    
    }


    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Integer id, @ModelAttribute("quizResult") QuizResult formResult, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("result", formResult);
            return "QuizResult/update";
        }
        resultService.update(id, formResult);        
        return "redirect:/quiz%20result";
    }


     @GetMapping("/delete/{id}")
    public String toDelete(@PathVariable("id") Integer id, Model model){
        QuizResult result = resultService.getById(id);
        model.addAttribute("result", result);
        return "QuizResult/delete";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        resultService.deleteById(id);
        return "redirect:/quiz%20result";
    }
}
