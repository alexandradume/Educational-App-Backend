package licenta.example.RestAPI.business;

import licenta.example.RestAPI.SpellChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AITest {

    private SpellChecker spellChecker;

    @BeforeEach
    public void setUp() {
        spellChecker = new SpellChecker();
    }

    @Test
    public void testGetAnswerForLecture() {
        String input = "unde este lecția Elemente de bază";
        AI ai = new AI(input);
        String expectedAnswer = "Lecția se află în a trei secțiune din cadrul barei de navigație din partea de sus, în secțiunea Clasa a IX-a";
        assertEquals(expectedAnswer, ai.getAnswer());
    }

    @Test
    public void testGetAnswerForTest() {
        String input = "unde este testul Sortări";
        AI ai = new AI(input);
        String expectedAnswer = "Testul se află în a doua secțiune din cadrul barei de navigație din partea de sus, în secțiunea Clasa a IX-a";
        assertEquals(expectedAnswer, ai.getAnswer());
    }

    @Test
    public void testGetAnswerForQuest() {
        String input = "unde este questul";
        AI ai = new AI(input);
        String expectedAnswer = "Poți găsi quest-uri în a patra secțiune din cadrul barei de navigație din partea de sus";
        assertEquals(expectedAnswer, ai.getAnswer());
    }

    @Test
    public void testGetAnswerForProfile() {
        String input = "unde este profilul";
        AI ai = new AI(input);
        String expectedAnswer = "Poți găsi profilul în prima secțiune din cadrul barei de navigație din partea de sus";
        assertEquals(expectedAnswer, ai.getAnswer());
    }

    @Test
    public void testGetAnswerForPodium() {
        String input = "unde este podiumul";
        AI ai = new AI(input);
        String expectedAnswer = "Poți găsi podiumul în a cincea secțiune din cadrul barei de navigație din partea de sus";
        assertEquals(expectedAnswer, ai.getAnswer());
    }

    @Test
    public void testGetAnswerForGraph() {
        String input = "unde este graficul";
        AI ai = new AI(input);
        String expectedAnswer = "Poți găsi graficul în a șasea secțiune din cadrul barei de navigație din partea de sus";
        assertEquals(expectedAnswer, ai.getAnswer());
    }

    @Test
    public void testGetAnswerForLogout() {
        String input = "unde este logout";
        AI ai = new AI(input);
        String expectedAnswer = "Poți găsi butonul de logout în cea mai din dreapta secțiune din cadrul barei de navigație din partea de sus";
        assertEquals(expectedAnswer, ai.getAnswer());
    }

    @Test
    public void testGetAnswerForNonExistentLecture() {
        String input = "unde este lecția Avansată";
        AI ai = new AI(input);
        String expectedAnswer = "Lecția cerută nu se găsește în aplicație, deci nu intră la bacalaureat.";
        assertEquals(expectedAnswer, ai.getAnswer());
    }
}
