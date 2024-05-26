package licenta.example.RestAPI;

import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class LinearRegressionTest {

    @Test
    public void testFit() {
        LinearRegression regression = new LinearRegression();
        double[] x = {1, 2, 3, 4, 5};
        double[] y = {2, 4, 6, 8, 10};
        regression.fit(x, y);
        assertEquals(2.0, regression.getSlope(), 1e-6);
        assertEquals(0.0, regression.getIntercept(), 1e-6);
    }

    @Test
    public void testPredict() {
        LinearRegression regression = new LinearRegression();
        double[] x = {1, 2, 3, 4, 5};
        double[] y = {2, 4, 6, 8, 10};
        regression.fit(x, y);
        assertEquals(12.0, regression.predict(6), 1e-6);
    }

    @Test
    public void testCalculateMSE() {
        LinearRegression regression = new LinearRegression();
        double[] x = {1, 2, 3, 4, 5};
        double[] y = {2, 4, 6, 8, 10};
        regression.fit(x, y);
        double mse = regression.calculateMSE(x, y);
        assertEquals(0.0, mse, 1e-6);
    }

    @Test
    public void testScaleData() {
        double[] x = {1, 2, 3, 4, 5};
        double[] scaledX = LinearRegression.scaleData(x);
        assertArrayEquals(new double[]{0.0, 0.25, 0.5, 0.75, 1.0}, scaledX, 1e-6);
    }

    @Test
    public void testNormalizeData() {
        double[] x = {1, 2, 3, 4, 5};
        double[] normalizedX = LinearRegression.normalizeData(x);
        double mean = 3.0;
        double stdDev = Math.sqrt(2.0);
        assertArrayEquals(new double[]{(1 - mean) / stdDev, (2 - mean) / stdDev, (3 - mean) / stdDev, (4 - mean) / stdDev, (5 - mean) / stdDev}, normalizedX, 1e-6);
    }

    @Test
    public void testGetPrediction() {
        LinearRegression regression = new LinearRegression();
        Map<String, Integer> scores = new HashMap<>();
        int prediction = regression.getPrediction(scores);
        assertTrue(prediction > 0); // Adjust the expected result based on your specific data
    }
}
