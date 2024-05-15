package licenta.example.RestAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "questions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    private ObjectId id;
    private String category;
    private String text;
    private List<String> answers;
    private String correctAnswer;
    private String photo;


    public Question(String category, String text) {
        this.category = category;
        this.text = text;
    }


}