package licenta.example.RestAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "recap")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recap {
    @Id
    private ObjectId id;
    private String firstPart;
    private String secondPart;
    private String correctAnswer;
    private String category;


    public Recap(String fistPart, String secondPart, String category) {
        this.firstPart = fistPart;
        this.secondPart = secondPart;
        this.category = category;
    }


}