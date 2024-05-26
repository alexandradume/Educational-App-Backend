package licenta.example.RestAPI.controllers;

import licenta.example.RestAPI.business.Service;
import licenta.example.RestAPI.model.Recap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RecapControllerTest {

    @Mock
    private Service service;

    @InjectMocks
    private RecapController recapController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetRecapWithoutCategory() {
        // Arrange
        List<Recap> recapList = Arrays.asList(
                new Recap("Category1", "Question1", "Answer1"),
                new Recap("Category2", "Question2", "Answer2"),
                new Recap("Category3", "Question3", "Answer3")
        );

        when(service.findRecap()).thenReturn(recapList);

        // Act
        ResponseEntity<List<Recap>> response = recapController.getRecap(null);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().size());
        verify(service, times(1)).findRecap();
    }

    @Test
    public void testGetRecapWithCategory() {
        // Arrange
        List<Recap> recapList = Arrays.asList(
                new Recap("Category1", "Question1", "Answer1"),
                new Recap("Category1", "Question2", "Answer2"),
                new Recap("Category1", "Question2", "Answer2")
        );

        when(service.findAllRecapsByCategory("Category1")).thenReturn(recapList);

        // Act
        ResponseEntity<List<Recap>> response = recapController.getRecap("Category1");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().size());
        verify(service, times(1)).findAllRecapsByCategory("Category1");
    }


}
