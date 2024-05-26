package licenta.example.RestAPI.infrastructure;

import licenta.example.RestAPI.model.Recap;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecapRepository extends MongoRepository<Recap, ObjectId> {
    List<Recap> findByCategory(String category);
}
