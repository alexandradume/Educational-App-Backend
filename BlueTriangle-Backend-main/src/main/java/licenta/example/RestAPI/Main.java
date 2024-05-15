package licenta.example.RestAPI;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {

    List<String> inputs = new ArrayList<>();
    List<String[]> labels = new ArrayList<>();
    List<String> outputs = new ArrayList<>();

    public void fct() {
        try {
            // Deschide fișierul Excel
            FileInputStream fis = new FileInputStream(new File("C:\\Users\\Alexandra\\OneDrive\\Desktop\\BlueTriangle-Backend-main (5)\\BlueTriangle-Backend-main\\src\\main\\resources\\data.xlsx"));

            // Creați o carte de lucru (Workbook) pentru fișierul Excel
            Workbook workbook = new XSSFWorkbook(fis);

            // Obțineți prima foaie din cartea de lucru
            Sheet sheet = workbook.getSheetAt(0);

            // Iterați peste fiecare rând din foaia de calcul
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                // Obțineți celulele din rând
                Iterator<Cell> cellIterator = row.cellIterator();

                // Extrageți valorile din fiecare celulă
                String input = cellIterator.next().getStringCellValue();
                String[] labelArray = cellIterator.next().getStringCellValue().split(",");
                String output = cellIterator.next().getStringCellValue();

                // Adaugați valorile la listele corespunzătoare
                inputs.add(input);
                labels.add(labelArray); // Trim pentru a elimina spațiile în plus
                outputs.add(output);
            }

            // Închideți fluxul de intrare
            fis.close();

            for (String[] label : labels) {
                String et = "";
                for(String l: label)
                    et += l + " ";
                System.out.println(et);
            }


            // Acum puteți folosi listele `inputs`, `labels` și `outputs` pentru a vă trimite datele la algoritmul NLP și pentru a obține răspunsurile corespunzătoare.

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Main main = new Main();
        main.fct();
    }
}
