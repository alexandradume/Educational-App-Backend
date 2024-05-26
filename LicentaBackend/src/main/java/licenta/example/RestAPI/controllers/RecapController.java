package licenta.example.RestAPI.controllers;

import licenta.example.RestAPI.business.Service;
import licenta.example.RestAPI.model.Recap;
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
@RequestMapping("/api/recap")
public class RecapController {

    private final static Logger log= LogManager.getLogger(UserController.class);

    private final Service service;
    public RecapController(Service service) {
        this.service = service;
    }




    @GetMapping
    @Description("Retrieves all the questions or questions by category")
    public ResponseEntity<List<Recap>> getRecap(@RequestParam(required = false) String category) {
        log.info("RecapController - getRecap");
        List<Recap> recaps;
        if (category != null && !category.isEmpty()) {
            recaps = service.findAllRecapsByCategory(category);
        } else {
            recaps = service.findRecap();
        }

        // Shuffle the list of questions
        Collections.shuffle(recaps);

        // Get the first 4 questions (or less if there are fewer than 4 questions)
        List<Recap> randomRecaps = recaps.subList(0, Math.min(3, 3));

        return new ResponseEntity<>(randomRecaps, HttpStatus.OK);
    }
}


