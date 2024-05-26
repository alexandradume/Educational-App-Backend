package licenta.example.RestAPI.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import licenta.example.RestAPI.model.Message;
import licenta.example.RestAPI.model.Question;
import licenta.example.RestAPI.model.Recap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import licenta.example.RestAPI.infrastructure.MessageRepository;
import licenta.example.RestAPI.infrastructure.QuestionRepository;
import licenta.example.RestAPI.infrastructure.RecapRepository;
import licenta.example.RestAPI.infrastructure.UserRepository;
import licenta.example.RestAPI.model.User;

public class ServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private RecapRepository recapRepository;

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private Service service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllUsers() {
        // Given
        List<User> users = new ArrayList<>();
        users.add(new User("username1", "password1"));
        users.add(new User("username2", "password2"));
        when(userRepository.findAll()).thenReturn(users);

        // When
        List<User> result = service.findAllUsers();

        // Then
        assertEquals(2, result.size());
        assertEquals("username1", result.get(0).getUsername());
        assertEquals("username2", result.get(1).getUsername());
    }

    @Test
    public void testFindByUsername() {
        // Given
        User user1 = new User("username1", "password1");
        when(userRepository.findByUsername("username1")).thenReturn(Optional.of(user1));

        // When
        User result1 = service.findUserByUsername("username1");

        // Then
        assertEquals("username1", result1.getUsername());
        assertEquals("password1", result1.getPassword());

    }

    @Test
    public void testLogIn() {
        String username = "username";
        String password = "password";
        User user = new User(username, password);
        when(userRepository.findUserByUsernameAndPassword(username, password)).thenReturn(Optional.of(user));

        // When
        Optional<User> result = service.login(username, password);

        // Then
        assertTrue(result.isPresent());
        assertEquals(user, result.get());


        String username1 = "username";
        String password1 = "password";
        when(userRepository.findUserByUsernameAndPassword(username1, password1)).thenReturn(Optional.empty());

        // When
        Optional<User> result1 = service.login(username1, password1);

        // Then
        assertFalse(result1.isPresent());
    }


    @Test
    public void testFindAllQuestions() {
        List <Question> mockedQuestions = Arrays.asList(
                new Question("category","Question 1"),
                new Question("category","Question 2"),
                new Question("category","Question 3"),
                new Question("category","Question 4"),
                new Question("category","Question 5")
        );
        when(questionRepository.findAll()).thenReturn(mockedQuestions);

        // Calling the method to be tested
        List<Question> result = service.findAllQuestions();

        // Verifying that the repository method was called
        verify(questionRepository, times(1)).findAll();

        // Verifying that the returned list has at most 5 questions
        assertTrue(result.size() <= 5);

        // Verifying that the returned list contains the same elements as the mocked list
        assertTrue(result.containsAll(mockedQuestions));
        assertTrue(mockedQuestions.containsAll(result));
    }


    @Test
    public void testFindAllQuestionsByCategory() {
        String category = "Test Category";
        List<Question> mockedQuestions = Arrays.asList(
                new Question(category,"Question 1"),
                new Question(category,"Question 2"),
                new Question(category,"Question 3")
        );
        when(questionRepository.findByCategory(category)).thenReturn(mockedQuestions);

        // Calling the method to be tested
        List <Question> result = service.findAllQuestionsByCategory(category);

        // Verifying that the repository method was called with the correct category
        verify(questionRepository, times(1)).findByCategory(category);

        // Verifying that the returned list contains the same elements as the mocked list
        assertEquals(mockedQuestions.size(), result.size());
        assertTrue(result.containsAll(mockedQuestions));
    }


    @Test
    public void testFindAllRecapsByCategory() {
        String category = "Test Category";
        List<Recap> mockedQuestions = Arrays.asList(
                new Recap("Question 1","",category),
                new Recap("Question 2","",category),
                new Recap("Question 3","",category)
        );
        when(recapRepository.findByCategory(category)).thenReturn(mockedQuestions);

        // Calling the method to be tested
        List <Recap> result = service.findAllRecapsByCategory(category);

        // Verifying that the repository method was called with the correct category
        verify(recapRepository, times(1)).findByCategory(category);

        // Verifying that the returned list contains the same elements as the mocked list
        assertEquals(mockedQuestions.size(), result.size());
        assertTrue(result.containsAll(mockedQuestions));
    }

    @Test
    public void testSaveMessage() {
        // Creating a mock message
        Message message = new Message();
        message.setText("Test content");

        // Mocking the behavior of the repository
        when(messageRepository.save(message)).thenReturn(message);

        // Calling the method to be tested
        Message savedMessage = service.saveMessage(message);

        // Verifying that the repository method was called with the correct message
        verify(messageRepository, times(1)).save(message);

        // Verifying that the returned message is the same as the mock message
        assertEquals(message, savedMessage);
    }


    @Test
    public void testCreateUser_UsernameAvailable() {
        // Mocking the behavior of userRepository.findByUsername() to return an empty optional,
        // indicating that the username is available
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        // Mocking the behavior of userRepository.save() to return the saved user
        User savedUser = new User();
        savedUser.setUsername("testUser");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Calling the method to be tested
        User newUser = service.createUser("testUser", "password", "birthdate", "description");

        // Verifying that userRepository.findByUsername() was called once with the correct username
        verify(userRepository, times(1)).findByUsername("testUser");

        // Verifying that userRepository.save() was called once with the correct user object
        verify(userRepository, times(1)).save(any(User.class));

        // Verifying that the returned user object is the same as the saved user
        assertEquals(savedUser, newUser);
    }

    @Test
    public void testCreateUser_UsernameTaken() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(new User()));

           assertThrows(RuntimeException.class, () -> {
            service.createUser("existingUser", "password", "birthdate", "description");
        });

        verify(userRepository, times(1)).findByUsername("existingUser");


        verify(userRepository, never()).save(any(User.class));
    }
    @Test
    public void testUpdateUser() {
        // Creating a mock user
        User userToUpdate = new User();

        userToUpdate.setUsername("testUser");
        userToUpdate.setPassword("password");
        userToUpdate.setBirthdate("birthdate");
        userToUpdate.setDescription("description");

        // Mocking the behavior of userRepository.save() to return the updated user
        when(userRepository.save(userToUpdate)).thenReturn(userToUpdate);

        // Calling the method to be tested
        User updatedUser = service.updateUser(userToUpdate);

        // Verifying that userRepository.save() was called once with the correct user object
        verify(userRepository, times(1)).save(userToUpdate);

        // Verifying that the returned user object is the same as the updated user
        assertEquals(userToUpdate, updatedUser);
    }


    @Test
    public void testFindRecap() {
        // Creating a list of mock recap objects
        Recap recap1 = new Recap();
        recap1.setFirstPart("Recap 1");

        Recap recap2 = new Recap();
        recap2.setFirstPart("Recap 2");

        List<Recap> recapList = Arrays.asList(recap1, recap2);

        when(recapRepository.findAll()).thenReturn(recapList);

        // Calling the method to be tested
        List <Recap> foundRecaps = service.findRecap();


        verify(recapRepository, times(1)).findAll();

        assertEquals(recapList.size(), foundRecaps.size());
        assertTrue(foundRecaps.containsAll(recapList));
    }


    @Test
    public void testFindAllMessagesByUsername() {
        // Creating a list of mock messages
        Message message1 = new Message();
        message1.setUsername("testUser");

        Message message2 = new Message();
        message2.setUsername("testUser");

        List<Message> messageList = Arrays.asList(message1, message2);

        // Mocking the behavior of messageRepository.findByUsername() to return the list of mock messages
        when(messageRepository.findByUsername("testUser")).thenReturn(messageList);

        // Calling the method to be tested
        List<Message> foundMessages = service.findAllMessagesByUsername("testUser");

        // Verifying that messageRepository.findByUsername() was called once with the correct username
        verify(messageRepository, times(1)).findByUsername("testUser");

        // Verifying that the returned list contains the same elements as the list of mock messages
        assertEquals(messageList.size(), foundMessages.size());
        assertTrue(foundMessages.containsAll(messageList));
    }


    @Test
    public void testFindAllMessages() {
        // Creating a list of mock messages
        Message message1 = new Message();
        message1.setText("Message 1");

        Message message2 = new Message();
        message2.setText("Message 2");

        List<Message> messageList = Arrays.asList(message1, message2);

        // Mocking the behavior of messageRepository.findAll() to return the list of mock messages
        when(messageRepository.findAll()).thenReturn(messageList);

        // Calling the method to be tested
        List<Message> foundMessages = service.findAllMessages();

        // Verifying that messageRepository.findAll() was called once
        verify(messageRepository, times(1)).findAll();

        // Verifying that the returned list contains the same elements as the list of mock messages
        assertEquals(messageList.size(), foundMessages.size());
        assertTrue(foundMessages.containsAll(messageList));
    }
}


