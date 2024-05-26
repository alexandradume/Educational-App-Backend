package licenta.example.RestAPI.controllers;

import licenta.example.RestAPI.business.Service;
import licenta.example.RestAPI.model.Question;
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

public class QuestionControllerTest {

    @Mock
    private Service service;

    @InjectMocks
    private QuestionController questionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetQuestionsWithoutCategory() {
        // Arrange
        List<Question> questionList = Arrays.asList(
                new Question("Category1", "Question1"),
                new Question("Category2", "Question2"),
                new Question("Category3", "Question3"),
                new Question("Category3", "Question4"),
                new Question("Category3", "Question5")
        );

        when(service.findAllQuestions()).thenReturn(questionList);

        // Act
        ResponseEntity<List<Question>> response = questionController.getQuestions(null);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(5, response.getBody().size());
        verify(service, times(1)).findAllQuestions();
    }

    @Test
    public void testGetQuestionsWithCategory() {
        // Arrange
        List<Question> questionList = Arrays.asList(
                new Question("Category1", "Question1"),
                new Question("Category1", "Question2"),
                new Question("Category1", "Question3"),
                new Question("Category1", "Question4"),
                new Question("Category1", "Question5")
        );

        when(service.findAllQuestionsByCategory("Category1")).thenReturn(questionList);

        // Act
        ResponseEntity<List<Question>> response = questionController.getQuestions("Category1");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(5, response.getBody().size());
        verify(service, times(1)).findAllQuestionsByCategory("Category1");
    }


}
