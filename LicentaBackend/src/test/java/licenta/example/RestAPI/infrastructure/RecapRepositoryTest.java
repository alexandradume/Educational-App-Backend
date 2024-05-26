package licenta.example.RestAPI.infrastructure;

import licenta.example.RestAPI.model.Recap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataMongoTest
class RecapRepositoryTest {
    @Autowired
    private RecapRepository recapRepository;
    @Test
    void testFindByCategory() {
        Recap recap = new Recap("first","second","categorie");
        recapRepository.save(recap);
        List<Recap> recapList = recapRepository.findByCategory("categorie");

        assertFalse(recapList.isEmpty());
        assertEquals("first", recapList.get(0).getFirstPart());
        assertEquals("second", recapList.get(0).getSecondPart());
        recapRepository.delete(recap);
        List<Recap> recap1 = recapRepository.findByCategory("categorie");
        assertTrue(recap1.isEmpty());
    }
}