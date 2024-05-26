package licenta.example.RestAPI.controllers;

import licenta.example.RestAPI.business.AI;
import licenta.example.RestAPI.business.Service;
import licenta.example.RestAPI.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MessageControllerTest {

    @Mock
    private Service service;

    @InjectMocks
    private MessageController messageController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetMessagesWithoutUsername() {
        // Arrange
        List<Message> messageList = Arrays.asList(
                new Message("user1", "text1", "2024-05-18T15:20:00.000Z"),
                new Message("user2", "text2", "2024-05-18T16:20:00.000Z")
        );

        when(service.findAllMessages()).thenReturn(messageList);

        // Act
        ResponseEntity<List<Message>> response = messageController.getMessages(null);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(service, times(1)).findAllMessages();
    }

    @Test
    public void testGetMessagesWithUsername() {
        // Arrange
        List<Message> messageList = Collections.singletonList(
                new Message("user1", "text1", "2024-05-18T15:20:00.000Z")
        );

        when(service.findAllMessagesByUsername("user1")).thenReturn(messageList);

        // Act
        ResponseEntity<List<Message>> response = messageController.getMessages("user1");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(service, times(1)).findAllMessagesByUsername("user1");
    }

    @Test
    public void testAddMessage() {
        // Arrange
        Message message = new Message("user1", "text1", "2024-05-18T15:20:00.000Z");
        when(service.saveMessage(any(Message.class))).thenReturn(message);

        // Act
        ResponseEntity<Message> response = messageController.addMessage(message);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(message, response.getBody());
        verify(service, times(1)).saveMessage(message);
    }

    @Test
    public void testAddMessageInvalid() {
        // Arrange
        Message message = new Message("user1", "text1", null);

        // Act
        ResponseEntity<Message> response = messageController.addMessage(message);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(service, times(0)).saveMessage(any(Message.class));
    }

    @Test
    public void testGetMessageFromAI() {
        // Arrange
        String username = "user1";
        String text = "Hello AI";
        String expectedResponse = "Hello, user1!";
        String currentDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(new Date());

        AI aiMock = mock(AI.class);
        when(aiMock.getAnswer()).thenReturn(expectedResponse);

        MessageController messageControllerWithMockedAI = new MessageController(service) {
            @Override
            public ResponseEntity<Message> getMessage(String username, String text) {
                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                String formattedDate = dateFormat.format(currentDate);
                AI ai = new AI(text) {
                    @Override
                    public String getAnswer() {
                        return aiMock.getAnswer();
                    }
                };
                Message message = new Message(username, ai.getAnswer(), formattedDate);
                Message savedMessage = service.saveMessage(message);
                return new ResponseEntity<>(savedMessage, HttpStatus.OK);
            }
        };

        Message expectedMessage = new Message(username, expectedResponse, currentDate);
        when(service.saveMessage(any(Message.class))).thenReturn(expectedMessage);

        // Act
        ResponseEntity<Message> response = messageControllerWithMockedAI.getMessage(username, text);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedMessage, response.getBody());
        verify(service, times(1)).saveMessage(any(Message.class));
    }
}
