package licenta.example.RestAPI;

import java.util.Map;

public class LinearRegression {
    private double slope;
    private double intercept;

    public void fit(double[] x, double[] y) {
        double sumX = 0;
        double sumY = 0;
        double sumXY = 0;
        double sumXX = 0;
        int n = x.length;

        for (int i = 0; i < n; i++) {
            sumX += x[i];
            sumY += y[i];
            sumXY += x[i] * y[i];
            sumXX += x[i] * x[i];
        }

        slope = (n * sumXY - sumX * sumY) / (n * sumXX - sumX * sumX);
        intercept = (sumY - slope * sumX) / n;
    }

    public double predict(double x) {
        return slope * x + intercept;
    }

    public double getSlope() {
        return slope;
    }

    public double getIntercept() {
        return intercept;
    }

    public double calculateMSE(double[] x, double[] y) {
        double sumSquaredError = 0;
        int n = x.length;

        for (int i = 0; i < n; i++) {
            double predicted = predict(x[i]);
            double error = y[i] - predicted;
            sumSquaredError += Math.pow(error, 2);
        }

        return sumSquaredError / n;
    }

    public static double[] scaleData(double[] x) {
        double[] scaledX = new double[x.length];
        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;

        // Găsirea valorilor minime și maxime
        for (double value : x) {
            if (value < minX) {
                minX = value;
            }
            if (value > maxX) {
                maxX = value;
            }
        }

        // Scalarea datelor
        double range = maxX - minX;
        for (int i = 0; i < x.length; i++) {
            scaledX[i] = (x[i] - minX) / range;
        }

        return scaledX;
    }

    public static double[] normalizeData(double[] x) {
        double[] normalizedX = new double[x.length];
        double sum = 0;

        // Calcularea sumei
        for (double value : x) {
            sum += value;
        }

        // Normalizarea datelor
        double mean = sum / x.length;
        double variance = 0;
        for (double value : x) {
            variance += Math.pow(value - mean, 2);
        }
        double stdDev = Math.sqrt(variance / x.length);
        for (int i = 0; i < x.length; i++) {
            normalizedX[i] = (x[i] - mean) / stdDev;
        }

        return normalizedX;
    }

    public int getPrediction(Map<String, Integer> scores) {
        // Datele istorice furnizate
        double[] dates = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};
        double[] values = {100, 412, 50, 70, 440, 440, 444, 504, 516, 516, 524, 547, 639, 461, 464, 464, 464, 464, 464, 492, 532, 559, 563, 597};

        // Calculul valorilor maxime și minime ale datelor de ieșire
        double minY = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;
        for (double value : values) {
            if (value < minY) {
                minY = value;
            }
            if (value > maxY) {
                maxY = value;
            }
        }

        // Scalarea și normalizarea datelor
        double[] scaledDates = scaleData(dates);
        double[] normalizedValues = normalizeData(values);

        // Antrenarea modelului de regresie liniară cu datele scalate și normalizate
        LinearRegression regression = new LinearRegression();
        regression.fit(scaledDates, normalizedValues);

        // Predictie pentru data viitoare
        double nextDate = 25; // 25 este următoarea dată
        double scaledNextDate = (nextDate - dates[0]) / (dates[dates.length - 1] - dates[0]); // Scalarea datelor pentru data viitoare
        double predictedValue = regression.predict(scaledNextDate);
        double descaledPrediction = predictedValue * (maxY - minY) + minY; // Descaleaza valoarea


        // Calculul MSE
        double mse = regression.calculateMSE(scaledDates, normalizedValues);
        return (int)descaledPrediction;

    }
}
