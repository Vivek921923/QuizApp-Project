package com.telusko.quizapp.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.telusko.quizapp.dao.QuestionDao;
import com.telusko.quizapp.dao.QuizDao;
import com.telusko.quizapp.model.Question;
import com.telusko.quizapp.model.QuestionWapper;
import com.telusko.quizapp.model.Quiz;

import com.telusko.quizapp.model.Response;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> creatQuiz(String category, int numQ, String title) {

        List<Question> questions = questionDao.findRandomQusetionByCategory(category,numQ);


        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestion(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Sucess",HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWapper>> getQuizQuestion(int id) {
       Optional<Quiz> quiz= quizDao.findById(id);
       List<Question> questionFromDB = quiz.get().getQuestion();
       List<QuestionWapper> questionForUser = new ArrayList<>();
       for(Question q : questionFromDB){
        QuestionWapper qw = new QuestionWapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
        questionForUser.add(qw);
       }


       return new ResponseEntity<>(questionForUser,HttpStatus.OK);
     
    }

    public ResponseEntity<Integer> calculateResult(int id, List<Response> responses) {
        Optional<Quiz> quiz= quizDao.findById(id);
       List<Question> question = quiz.get().getQuestion();
       int right=0;
       int i =0;

       for(Response response : responses){
        if (response.getResponse().equals(question.get(i).getRightAnswer())) {
            right++;

         i++;
            
        }

       }
       return new ResponseEntity<>(right,HttpStatus.OK);
       
    }

   

}
