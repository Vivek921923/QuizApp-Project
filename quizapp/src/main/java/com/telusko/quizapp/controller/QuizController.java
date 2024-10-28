package com.telusko.quizapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.telusko.quizapp.model.QuestionWapper;
import com.telusko.quizapp.model.Response;
import com.telusko.quizapp.service.QuizService;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    
    @Autowired
    QuizService quizService;
    


    @PostMapping("/create")
    public ResponseEntity<String> creatQuiz(@RequestParam String category, @RequestParam int numQ, @RequestParam String title){
        return quizService.creatQuiz(category,numQ,title);


    }
    
    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionWapper>> getQuizQuestion(@PathVariable int id){
        return quizService.getQuizQuestion(id);


    }
    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable int id, @RequestBody List<Response> responses){
        return quizService.calculateResult(id, responses);

    }
}
