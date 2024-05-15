package licenta.example.RestAPI.controllers;

import licenta.example.RestAPI.business.AI;
import licenta.example.RestAPI.business.Service;
import licenta.example.RestAPI.model.Message;
import licenta.example.RestAPI.model.Question;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*" )
@RequestMapping("/api/messages")
public class MessageController {

    private final static Logger log= LogManager.getLogger(UserController.class);

    private final Service service;
    public MessageController(Service service) {
        this.service = service;
    }

    @GetMapping
    @Description("Retrieves all the messages or messages for/from a username")
    public ResponseEntity<List<Message>> getMessages(@RequestParam(required = false) String username) {
        log.info("MessageController - getMessages");
        List<Message> messages;
        if (username != null && !username.isEmpty()) {
            messages = service.findAllMessagesByUsername(username);
        } else {
            messages = service.findAllMessages();
        }
        return new ResponseEntity<>(messages, HttpStatus.OK);

    }


    @PostMapping("/add")
    @Description("Adding a new message")
    public ResponseEntity<Message> addMessage(@RequestBody Message message) {
        log.info("MessageController - addMessage");

        // Validează dacă mesajul trimis este complet
        if (message.getUsername() == null || message.getText() == null || message.getDate() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Salvează mesajul în baza de date
        Message savedMessage = service.saveMessage(message);

        // Returnează răspunsul HTTP cu mesajul salvat și codul de stare OK
        return new ResponseEntity<>(savedMessage, HttpStatus.OK);
    }


    @GetMapping("/ai")
    @CrossOrigin(origins = "http://127.0.0.1:5173")
    @Description("Retrieves message from ai")
    public ResponseEntity<Message> getMessage(@RequestParam String username,  @RequestParam String text ) {
        log.info("MessageController - getMessage");
        Date currentDate = new Date();

        // Formatează data și ora curentă în formatul ISO 8601
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String formattedDate = dateFormat.format(currentDate);
        AI ai = new AI(text);
        // Construiește obiectul Message
        Message message = new Message(username, ai.getAnswer(), formattedDate);

        // Salvează mesajul în baza de date (dacă este necesar)

        Message savedMessage = service.saveMessage(message);
        // Returnează răspunsul HTTP cu mesajul creat și codul de stare OK
        return new ResponseEntity<>(savedMessage, HttpStatus.OK);

    }

}


