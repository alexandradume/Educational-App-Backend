package licenta.example.RestAPI.infrastructure;

import licenta.example.RestAPI.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataMongoTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Test
    void testFindUserByCriteria() {
        User user = new User("James","123");
        userRepository.save(user);
        Optional<User> user1 = userRepository.findByUsername("James");

        assertFalse(user1.isEmpty());
        assertEquals("James", user1.get().getUsername());


        Optional<User> user2 = userRepository.findUserByUsernameAndPassword("James", "123");
        assertEquals("James", user2.get().getUsername());
        userRepository.delete(user);
        Optional<User> user3 = userRepository.findByUsername("James");
        assertTrue(user3.isEmpty());

    }
}