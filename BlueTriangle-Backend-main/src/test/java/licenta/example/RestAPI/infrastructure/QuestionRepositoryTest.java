package licenta.example.RestAPI.infrastructure;

import licenta.example.RestAPI.model.Message;
import licenta.example.RestAPI.model.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataMongoTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;
    @Test
    void FindByCategory() {
        Question question = new Question("categorie","text");
        questionRepository.save(question);
        List<Question> questions = questionRepository.findByCategory("categorie");

        assertFalse(questions.isEmpty());
        assertEquals("text", questions.get(0).getText());
        questionRepository.delete(question);
        List<Question> question1 = questionRepository.findByCategory("categorie");
        assertTrue(question1.isEmpty());
    }
}