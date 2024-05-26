package licenta.example.RestAPI;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SpellChecker {
    private ArrayList<String> words = new ArrayList<>();
    private ArrayList<String> correctWords = new ArrayList<>();


    public static int levenshteinDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= s2.length(); j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
            }
        }

        return dp[s1.length()][s2.length()];
    }

    public static String correctSpelling(String word, List<String> vocabulary) {
        int minDistance = Integer.MAX_VALUE;
        String closestWord = null;

        for (String validWord : vocabulary) {
            int distance = levenshteinDistance(word, validWord);
            if (distance < minDistance) {
                minDistance = distance;
                closestWord = validWord;
            }
        }

        return closestWord;
    }

    public String verify(String word) {
        List<String> vocabulary = new ArrayList<>();
        vocabulary.add("unde");
        vocabulary.add("este");
        vocabulary.add("testul");
        vocabulary.add("Elemente");
        vocabulary.add("de");
        vocabulary.add("bază");
        vocabulary.add("lecția");
        vocabulary.add("bază");
        vocabulary.add("Subprograme");
        vocabulary.add("Recursivitate");
        vocabulary.add("Instrucțiuni");
        vocabulary.add("Tablouri");
        vocabulary.add("undiminensionale");
        vocabulary.add("bidiminensionale");
        vocabulary.add("Sortări");
        vocabulary.add("Divide");
        vocabulary.add("Impera");
        vocabulary.add("Șiruri");
        vocabulary.add("caractere");
        vocabulary.add("Stiva");
        vocabulary.add("Coada");
        vocabulary.add("Backtracking");
        vocabulary.add("Greedy");
        vocabulary.add("Programe");
        vocabulary.add("dinamică");
        vocabulary.add("grafuri");
        vocabulary.add("Teoria");
        vocabulary.add("quest");
        vocabulary.add("profil");
        vocabulary.add("podiumul");
        vocabulary.add("grafic");
        vocabulary.add("log out");


        String correctedWord = correctSpelling(word, vocabulary);
        return correctedWord;

    }
}
