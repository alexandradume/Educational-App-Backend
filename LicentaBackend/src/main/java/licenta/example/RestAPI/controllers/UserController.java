package licenta.example.RestAPI.controllers;

import licenta.example.RestAPI.LinearRegression;
import licenta.example.RestAPI.business.Service;
import licenta.example.RestAPI.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@CrossOrigin(origins = "*" )
@RequestMapping("/api/users")
public class UserController {

    private final static Logger log= LogManager.getLogger(UserController.class);

    private final Service service;
    public UserController(Service service) {
        this.service = service;
    }


    @PutMapping("/updateNumberOfDoneQuest")
    @Description("Updates the money for a user")
    public ResponseEntity<User> updateNumberOfDoneQuest(@RequestParam String username, @RequestParam int newNumberOfDoneQuest) {
        log.info("UserController - updateNumberOfDoneQuest : {}", username);

        // Find the user by username
        User user = service.findUserByUsername(username);

        // Check if user exists
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setUsername(user.getUsername());
        user.setBirthdate(user.getBirthdate());
        user.setPassword(user.getPassword());
        user.setDescription(user.getDescription());
        user.setTests(user.getTests());
        user.setScore(user.getScore());
        // Update the money
        user.setMoney(user.getMoney());
        user.setNumberOfDoneQuest(newNumberOfDoneQuest);
        user.setYearOfLastGift(user.getYearOfLastGift());

        // Save the updated user
        User updatedUser = service.updateUser(user);

        return ResponseEntity.ok(updatedUser);
    }


    @PutMapping("/updateMoney")
    @Description("Updates the money for a user")
    public ResponseEntity<User> updateMoneyForUser(@RequestParam String username, @RequestParam int newMoney) {
        log.info("UserController - updateMoneyForUser : {}", username);

        // Find the user by username
        User user = service.findUserByUsername(username);

        // Check if user exists
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setUsername(user.getUsername());
        user.setBirthdate(user.getBirthdate());
        user.setPassword(user.getPassword());
        user.setDescription(user.getDescription());
        user.setTests(user.getTests());
        user.setScore(user.getScore());
        // Update the money
        user.setMoney(newMoney + user.getMoney());
        user.setNumberOfDoneQuest(user.getNumberOfDoneQuest());
        user.setYearOfLastGift(user.getYearOfLastGift());
        // Save the updated user
        User updatedUser = service.updateUser(user);

        return ResponseEntity.ok(updatedUser);
    }




    @PutMapping("/updateYearOfLastGift")
    @Description("Updates the money for a user")
    public ResponseEntity<User> updateYearOfLastGiftForUser(@RequestParam String username, @RequestParam int yearOfLastGift) {
        log.info("UserController - updateMoneyForUser : {}", username);

        // Find the user by username
        User user = service.findUserByUsername(username);

        // Check if user exists
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setUsername(user.getUsername());
        user.setBirthdate(user.getBirthdate());
        user.setPassword(user.getPassword());
        user.setDescription(user.getDescription());
        user.setTests(user.getTests());
        user.setScore(user.getScore());
        user.setMoney(user.getMoney());
        user.setNumberOfDoneQuest(user.getNumberOfDoneQuest());
        user.setYearOfLastGift(yearOfLastGift);
        // Save the updated user
        User updatedUser = service.updateUser(user);

        return ResponseEntity.ok(updatedUser);
    }


    @PutMapping("/updateScore")
    @Description("Updates score for a user")
    public ResponseEntity<User> updateScoreForUser( @RequestParam String username, @RequestParam int newScore) {
        log.info("UserController - updateScoreForUser : {}", username);

        // Find the user by username
        User user = service.findUserByUsername(username);

        // Check if user exists
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        Map<String, Integer> scores = user.getScores();
        LocalDate currentDate = LocalDate.now();

        // Definirea unui format pentru data dorită
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Convertirea datei în șir de caractere utilizând formatul definit
        String formattedDate = currentDate.format(formatter);
        log.info("Data curentă în formatul dorit: " + formattedDate);
        scores.put(formattedDate, user.getScore()+newScore);

        user.setUsername(user.getUsername());
        user.setBirthdate(user.getBirthdate());
        user.setPassword(user.getPassword());
        user.setDescription(user.getDescription());
        user.setTests(user.getTests());
        user.setScore(user.getScore() + newScore);
        user.setScores(scores);
        // Update the money
        user.setMoney(user.getMoney());
        user.setNumberOfDoneQuest(user.getNumberOfDoneQuest());
        user.setYearOfLastGift(user.getYearOfLastGift());

        // Save the updated user
        User updatedUser = service.updateUser(user);

        return ResponseEntity.ok(updatedUser);
    }




    @PutMapping("/updateTests")
    @Description("Updates the money for a user")
    public ResponseEntity<User> updateTestsForUser(@RequestParam String username, @RequestParam String newTests) {
        log.info("UserController - updateTestsForUser : {}", username);

        // Find the user by username
        User user = service.findUserByUsername(username);

        // Check if user exists


        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        log.info(newTests);
        List<String> teste = new ArrayList<>(user.getTests());
        teste.add(newTests);

        // Get existing tests from the user
        List<String> uniqueTests = new ArrayList<>();

        HashMap<String, Integer> hashMap = new HashMap<>();
        // Add new tests to the set of unique tests

        for (String test : teste) {
            log.info(test);
            String[] parts = test.split(",");
            String category = parts[0].trim(); // Eliminăm spațiile albe din jurul categoriei
            int score = Integer.parseInt(parts[1].trim()); // Eliminăm spațiile albe din jurul scorului
            hashMap.put(category, score);
        }


        HashMap<String, Integer> maxScores = new HashMap<>();

// Iterăm prin fiecare pereche cheie-valoare din hashmap
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();

            // Verificăm dacă cheia există deja în maxScores
            if (maxScores.containsKey(key)) {
                // Dacă valoarea curentă este mai mare decât valoarea maximă asociată cu această cheie
                if (value > maxScores.get(key)) {
                    // Actualizăm valoarea maximă pentru această cheie
                    maxScores.put(key, value);
                }
            } else {
                // Dacă cheia nu există încă în maxScores, adăugăm perechea cheie-valoare în mod direct
                maxScores.put(key, value);
            }
        }


        for (Map.Entry<String, Integer> entry : maxScores.entrySet()){
            String key = entry.getKey();
            int value = entry.getValue();

            uniqueTests.add(key + ", " + value);
        }



        // Set the updated unique tests back to the user
        user.setTests(new ArrayList<>(uniqueTests));

        // Update the user
        User updatedUser = service.updateUser(user);

        return ResponseEntity.ok(updatedUser);
    }



    @GetMapping
    @Description("Retrieves all the users")
    public ResponseEntity<List<User>> getUsers() {
        log.info("UserController - getUsers");
        return new ResponseEntity<List<User>>(service.findAllUsers(), HttpStatus.OK);
    }
    @GetMapping("/{username}")
    @Description("Retrieves the user based on his username")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username){
        log.info("UserController - getUserByUsername : {}",username);
        return new ResponseEntity<User>(service.findUserByUsername(username), HttpStatus.OK);
    }



    @GetMapping("/prediction/{username}")
    @Description("Retrieves the score prediction based on a username")
    public ResponseEntity<Integer> getPrediction(@PathVariable String username) {
        log.info("UserController - getPrediction : {}",username);
        User user = service.findUserByUsername(username);
        LinearRegression lr = new LinearRegression();
        Integer prediction = lr.getPrediction(user.getScores());
        return new ResponseEntity<>(prediction, HttpStatus.OK);

    }

    @PostMapping("/login")
    @Description("Login of a user")
    public ResponseEntity<Optional<User>> login(@RequestBody Map<String, String> payload) {
        log.info("UserController - login : {}",payload);
        Optional<User> user = service.login(payload.get("username"), payload.get("password"));

        if (user.isPresent()) {
            ResponseEntity<Optional<User>> response = new ResponseEntity<>(user, HttpStatus.OK);
            log.info("UserController - response : {}", response);
            return response;
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody Map<String, Object> payload) {
        String username = (String) payload.get("username");
        String password = (String) payload.get("password");
        String birthdate = (String) payload.get("birthdate"); // Assuming birthdate is provided as string in format "yyyy-MM-dd"
        String description = (String) payload.get("description");

        try {
            User newUser = service.createUser(username, password, birthdate, description);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }





    @GetMapping("/findByScore")
    @Description("Retrieves all the users sorted by score in descending order")
    public ResponseEntity<List<User>> getUsersSortedByScore() {
        log.info("UserController - getUsersSortedByScore");

        // Obțineți toți utilizatorii
        List<User> allUsers = service.findAllUsers();

        // Sortați lista de utilizatori în funcție de scor în ordine descrescătoare
        allUsers.sort(Comparator.comparingInt(User::getScore).reversed());

        // Returnați lista sortată de utilizatori
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }



    @GetMapping("/findByMoney")
    @Description("Retrieves all the users sorted by money in descending order")
    public ResponseEntity<List<User>> getUsersSortedByMoney() {
        log.info("UserController - getUsersSortedByMoney");

        // Obțineți toți utilizatorii
        List<User> allUsers = service.findAllUsers();

        // Sortați lista de utilizatori în funcție de scor în ordine descrescătoare
        allUsers.sort(Comparator.comparingInt(User::getMoney).reversed());

        // Returnați lista sortată de utilizatori
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }




}
