package licenta.example.RestAPI;

import java.util.Map;
import java.util.*;
import java.text.*;

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

    public static double[] normalizeData(double[] x, double minY, double maxY) {
        return Arrays.stream(x).map(d -> (d - minY) / (maxY - minY)).toArray();
    }

    public int getPrediction(Map<String, Integer> scores) {
        // Convert date strings to numerical values
        double[] dates = new double[scores.size()];
        double[] values = new double[scores.size()];

        int index = 0;
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            dates[index] = convertDateToNumber(entry.getKey());
            values[index] = entry.getValue();
            index++;
        }

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
        double[] normalizedValues = normalizeData(values, minY, maxY);

        // Antrenarea modelului de regresie liniară cu datele scalate și normalizate
        LinearRegression regression = new LinearRegression();
        regression.fit(scaledDates, normalizedValues);

        // Predictie pentru data viitoare
        String nextDateString = getNextDateString();
        System.out.println(nextDateString);
        double nextDate = convertDateToNumber(nextDateString); // Convert next date to numerical value
        double scaledNextDate = (nextDate - dates[0]) / (dates[dates.length - 1] - dates[0]); // Scalarea datelor pentru data viitoare
        double predictedValue = regression.predict(scaledNextDate);
        double descaledPrediction = predictedValue * (maxY - minY) + minY; // Descaleaza valoarea

        // Calculul MSE
        double mse = regression.calculateMSE(scaledDates, normalizedValues);
        System.out.println("Mean Squared Error: " + mse);

        return (int)descaledPrediction;
    }

    // Helper method to convert date string to a numerical value
    private double convertDateToNumber(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = sdf.parse(date);
            return parsedDate.getTime() / (1000 * 60 * 60 * 24); // Convert to days since epoch
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Helper method to get the next date as a string
    private String getNextDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        return sdf.format(cal.getTime());
    }
}



