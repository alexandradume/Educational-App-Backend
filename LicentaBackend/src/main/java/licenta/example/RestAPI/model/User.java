package licenta.example.RestAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private ObjectId id;
    private String username;
    private String password;
    private String description;
    private String birthdate;

    private List<String> tests;
    private Map<String, Integer> scores;

    private int score;
    private int numberOfDoneQuest;


    private int money;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String description, String birthdate) {
        this.username = username;
        this.password = password;
        this.description = description;
        this.birthdate = birthdate;
    }
}