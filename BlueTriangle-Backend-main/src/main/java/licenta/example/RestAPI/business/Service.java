package licenta.example.RestAPI.business;

import licenta.example.RestAPI.infrastructure.MessageRepository;
import licenta.example.RestAPI.infrastructure.QuestionRepository;
import licenta.example.RestAPI.infrastructure.RecapRepository;
import licenta.example.RestAPI.infrastructure.UserRepository;
import licenta.example.RestAPI.model.Message;
import licenta.example.RestAPI.model.Question;
import licenta.example.RestAPI.model.Recap;
import licenta.example.RestAPI.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class Service {

    private final static Logger log= LogManager.getLogger(Service.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private RecapRepository recapRepository;


    @Autowired
    private MessageRepository messageRepository;

    public List<User> findAllUsers() {
        System.out.println("findAllUsers");
        log.info("Service - findAllUsers");
        return userRepository.findAll();
    }
    public User findUserByUsername(String username) {
        log.info("Service - findByUsername : {}",username);
        return userRepository.findByUsername(username).orElse(null);
    }


    public Optional<User> login(String username, String password) {
        log.info("Service - login : {}, {}",username,password);
        Optional<User> user = userRepository.findUserByUsernameAndPassword(username,password);
        return user;
    }

    public List<Question> findAllQuestions() {
        System.out.println("findAllQuestions");
        log.info("Service - findAllQuestions");
        List<Question> allQuestions = questionRepository.findAll();

        // Shuffle the list of questions
        Collections.shuffle(allQuestions);

        // Return the first 5 questions (or less if there are fewer than 5 questions)
        return allQuestions.subList(0, Math.min(5, allQuestions.size()));
    }

    public List<Question> findAllQuestionsByCategory(String category) {
        // Implement logic to retrieve questions by category from the repository
        return questionRepository.findByCategory(category);
    }


    public List<Recap> findAllRecapsByCategory(String category) {
        // Implement logic to retrieve questions by category from the repository
        return recapRepository.findByCategory(category);
    }

    public Message saveMessage(Message message) {

        return messageRepository.save(message);
    }
    public User createUser(String username, String password, String birthdate, String description) {
        // Check if the username is already taken
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username is already taken");
        }

        // Create a new User object
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password); // You should hash the password before storing it
        newUser.setBirthdate(birthdate);
        newUser.setDescription(description);

        // Save the new user in the database
        return userRepository.save(newUser);
    }
    public User updateUser(User user) {
        return userRepository.save(user);
    }


    public List<Recap> findRecap() {
        return recapRepository.findAll();
    }

    public List<Message> findAllMessagesByUsername(String username) {
        return messageRepository.findByUsername(username);
    }

    public List<Message> findAllMessages() {
        return messageRepository.findAll();
    }



}