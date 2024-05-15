package licenta.example.RestAPI.infrastructure;

import licenta.example.RestAPI.model.Message;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
@DataMongoTest
class MessageRepositoryTest {
    @Autowired
    private MessageRepository messageRepository ;
    @Test
    void testFindByUsername() {
        Message message = new Message("john", "Hello world", "2022-04-09");
        messageRepository.save(message);
        List<Message> messages = messageRepository.findByUsername("john");

        assertFalse(messages.isEmpty());
        assertEquals("Hello world", messages.get(0).getText());
        messageRepository.delete(message);
        List<Message> messages1 = messageRepository.findByUsername("john");
        assertTrue(messages1.isEmpty());
    }

}