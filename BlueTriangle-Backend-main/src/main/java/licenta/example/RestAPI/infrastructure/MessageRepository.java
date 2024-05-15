package licenta.example.RestAPI.infrastructure;

import licenta.example.RestAPI.model.Message;
import licenta.example.RestAPI.model.Question;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends MongoRepository<Message, ObjectId> {
    List<Message> findByUsername(String text);


}
