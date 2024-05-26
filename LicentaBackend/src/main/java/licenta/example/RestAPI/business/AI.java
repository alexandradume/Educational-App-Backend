package licenta.example.RestAPI.business;


import licenta.example.RestAPI.SpellChecker;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;

import java.util.*;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class AI {

    private String text;

    private SpellChecker spellChecker;
    private String answer;
    private String[] aux;

    private Set<String> words = new HashSet<>();


    public AI(String text) {
        this.text = text;
        answer = "";
        this.spellChecker = new SpellChecker();
        buildAnswer();
    }

    private void buildAnswer() {
        getWordsFromText();

    }

    private void getWordsFromText() {
        Tokenizer tokenizer = SimpleTokenizer.INSTANCE;
        String[] aux = tokenizer.tokenize(text);

        words.addAll(Arrays.asList(aux));

        List<String> tokens = new ArrayList<>();
        for(String word:words){
            String newWord = spellChecker.verify(word);
            tokens.add(newWord);

        }
        if (tokens.contains("unde")) {
            if (words.contains("lecția") || words.contains("lecția")) {
                answer += "Lecția se află în a trei secțiune din cadrul barei de navigație din partea de sus";
                if ((words.contains("Elemente") && words.contains("bază")) || words.contains("Instrucțiuni") || (words.contains("Tablouri") && words.contains("unidimensionale")) || (words.contains("Tablouri") && words.contains("bidimensionale")) || words.contains("Sortări"))
                    answer += ", în secțiunea Clasa a IX-a";
                else if (words.contains("Subprograme") || words.contains("Recursivitate") || words.contains("Divie") || words.contains("Impera") || (words.contains("Șiruri") && words.contains("caractere")) || words.contains("Stiva") || words.contains("Coada")) {
                    answer += ", în secțiunea Clasa a X-a";
                } else if (words.contains("Backtracking") || words.contains("Greedy") || (words.contains("Programare") && words.contains("dinamică")) || words.contains("grafuri") || (words.contains("Teoria") && words.contains("grafurilor"))) {
                    answer += ", în secțiunea Clasa a XI-a";
                } else {
                    answer = "Lecția cerută nu se găsește în aplicație, deci nu intră la bacalaureat.";
                }

            } else if (words.contains("test") || words.contains("testul")) {
                answer += "Testul se află în a doua secțiune din cadrul barei de navigație din partea de sus";
                if ((words.contains("Elemente") && words.contains("bază")) || words.contains("Instrucțiuni") || (words.contains("Tablouri") && words.contains("unidimensionale")) || (words.contains("Tablouri") && words.contains("bidimensionale")) || words.contains("Sortări"))
                    answer += ", în secțiunea Clasa a IX-a";
                else if (words.contains("Subprograme") || words.contains("Recursivitate") || words.contains("Subprograme\\Recursivitate") || words.contains("Divie") || words.contains("Impera") || (words.contains("Șiruri") && words.contains("caractere")) || words.contains("Stiva") || words.contains("Coada") || words.contains("Stiva\\Coada")) {
                    answer += ", în secțiunea Clasa a X-a";
                } else if (words.contains("Backtracking") || words.contains("Greedy") || (words.contains("Programare") && words.contains("dinamică")) || words.contains("grafuri") || (words.contains("Teoria") && words.contains("grafurilor"))){
                    answer += ", în secțiunea Clasa a XI-a";
                } else {
                    answer = "Testul cerut nu există.";
                }

            } else if (words.contains("quest") || words.contains("questul") || words.contains("quest-ul")) {
                answer += "Poți găsi quest-uri în a patra secțiune din cadrul barei de navigație din partea de sus";

            } else if (words.contains("profil") || words.contains("profilul")) {
                answer += "Poți găsi profilul în prima secțiune din cadrul barei de navigație din partea de sus";

            } else if (words.contains("podiumul")) {
                answer += "Poți găsi podiumul în a cincea secțiune din cadrul barei de navigație din partea de sus";

            } else if (words.contains("grafic") || words.contains("graficul")) {
                answer += "Poți găsi graficul în a șasea secțiune din cadrul barei de navigație din partea de sus";

            } else if (words.contains("logout") || (words.contains("log") && words.contains("out"))) {
                answer += "Poți găsi butonul de logout în cea mai din dreapta secțiune din cadrul barei de navigație din partea de sus";

            }
        }
    }

    public String getAnswer(){

        return answer;
    }



}
