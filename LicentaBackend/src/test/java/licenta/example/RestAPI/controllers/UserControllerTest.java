package licenta.example.RestAPI.controllers;

import licenta.example.RestAPI.business.Service;
import licenta.example.RestAPI.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private Service service;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUsers() {
        User user1 = new User("user1", "password1");
        User user2 = new User("user2", "password2");
        List<User> users = Arrays.asList(user1, user2);
        when(service.findAllUsers()).thenReturn(users);

        ResponseEntity<List<User>> response = userController.getUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(service, times(1)).findAllUsers();
    }

    @Test
    public void testGetUserByUsername() {
        User user = new User("user1", "password1");
        when(service.findUserByUsername("user1")).thenReturn(user);

        ResponseEntity<User> response = userController.getUserByUsername("user1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("user1", response.getBody().getUsername());
        verify(service, times(1)).findUserByUsername("user1");
    }

    @Test
    public void testUpdateNumberOfDoneQuest() {
        User user = new User("user1", "password1");
        user.setNumberOfDoneQuest(5);
        when(service.findUserByUsername("user1")).thenReturn(user);
        when(service.updateUser(any(User.class))).thenReturn(user);

        ResponseEntity<User> response = userController.updateNumberOfDoneQuest("user1", 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(10, response.getBody().getNumberOfDoneQuest());
        verify(service, times(1)).findUserByUsername("user1");
        verify(service, times(1)).updateUser(any(User.class));
    }

    @Test
    public void testUpdateMoneyForUser() {
        User user = new User("user1", "password1");
        user.setMoney(100);
        when(service.findUserByUsername("user1")).thenReturn(user);
        when(service.updateUser(any(User.class))).thenReturn(user);

        ResponseEntity<User> response = userController.updateMoneyForUser("user1", 50);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(150, response.getBody().getMoney());
        verify(service, times(1)).findUserByUsername("user1");
        verify(service, times(1)).updateUser(any(User.class));
    }

    @Test
    public void testLogin() {
        User user = new User("user1", "password1");
        when(service.login("user1", "password1")).thenReturn(Optional.of(user));

        ResponseEntity<Optional<User>> response = userController.login(Map.of("username", "user1", "password", "password1"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("user1", response.getBody().get().getUsername());
        verify(service, times(1)).login("user1", "password1");
    }

    @Test
    public void testRegisterUser() {
        User user = new User("user1", "password1", "description", "2000-01-01");
        when(service.createUser(anyString(), anyString(), anyString(), anyString())).thenReturn(user);

        ResponseEntity<User> response = userController.registerUser(Map.of(
                "username", "user1",
                "password", "password1",
                "birthdate", "2000-01-01",
                "description", "description"
        ));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("user1", response.getBody().getUsername());
        verify(service, times(1)).createUser(anyString(), anyString(), anyString(), anyString());
    }
}
