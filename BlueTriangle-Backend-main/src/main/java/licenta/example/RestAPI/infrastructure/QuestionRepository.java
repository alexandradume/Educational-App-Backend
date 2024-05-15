package licenta.example.RestAPI.infrastructure;

import licenta.example.RestAPI.model.Question;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends MongoRepository<Question, ObjectId> {


    List<Question> findByCategory(String category);
}
