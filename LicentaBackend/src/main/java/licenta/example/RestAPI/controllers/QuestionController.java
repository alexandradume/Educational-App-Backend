package licenta.example.RestAPI.controllers;

import licenta.example.RestAPI.business.Service;
import licenta.example.RestAPI.model.Question;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = "*" )
@RequestMapping("/api/questions")
public class QuestionController {

    private final static Logger log= LogManager.getLogger(UserController.class);

    private final Service service;
    public QuestionController(Service service) {
        this.service = service;
    }

    @GetMapping
    @Description("Retrieves all the questions or questions by category")
    public ResponseEntity<List<Question>> getQuestions(@RequestParam(required = false) String category) {
        log.info("QuestionController - getQuestions");
        List<Question> questions;
        log.info(category);
        if (category != null && !category.isEmpty()) {
            questions = service.findAllQuestionsByCategory(category);
        } else {
            questions = service.findAllQuestions();
        }

        // Shuffle the list of questions
        Collections.shuffle(questions);

        // Get the first 4 questions (or less if there are fewer than 4 questions)
        List<Question> randomQuestions = questions.subList(0, Math.min(5, 5));

        return new ResponseEntity<>(randomQuestions, HttpStatus.OK);
    }


    @GetMapping("/quest")
    @Description("Retrieves questions by category for quest")
    public ResponseEntity<List<Question>> getQuestionsQuest(@RequestParam(required = false) String category) {
        log.info("QuestionController - getQuestions");
        List<Question> questions;
        log.info(category);
        if (category != null && !category.isEmpty()) {
            questions = service.findAllQuestionsByCategory(category);
        } else {
            questions = service.findAllQuestions();
        }

        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
}


