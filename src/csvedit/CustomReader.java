package csvedit;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CustomReader {
    private List<String[]> table = new ArrayList<>();

    public CustomReader() {

    }
    public void read(String fileName) throws IOException {
        read(fileName, ',');
    }
    public void read(String fileName, char delimiter) throws IOException {
        read(fileName, delimiter, true);
    }
    public void read(String fileName, char delimiter, boolean ignoreQuotes) throws IOException {

        FileInputStream inputStream = new FileInputStream(fileName);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        CSVParser parser = new CSVParserBuilder().withSeparator(delimiter).withIgnoreQuotations(ignoreQuotes).withIgnoreLeadingWhiteSpace(false).build();
        CSVReader reader = new CSVReaderBuilder(bufferedReader).withCSVParser(parser).build();
        table = reader.readAll();

        boolean breakRow = true;
        for (int rowIndex = 0; rowIndex < table.size(); ++rowIndex) {
            breakRow = !breakRow;
            if (breakRow) {
                table.remove(rowIndex);
                --rowIndex;
                continue;
            }
            String[] row = table.get(rowIndex);
            for (int valueIndex = 0; valueIndex < row.length; ++valueIndex){
                if (!row[valueIndex].startsWith("\"") && valueIndex == 0 && row[valueIndex].length()>1) {
                    row[valueIndex] = "\""+row[valueIndex];
                }
                while (!row[valueIndex].startsWith("\"") && !row[valueIndex].equals("")) {
                    row[valueIndex] = row[valueIndex].substring(1);
                }
                row[valueIndex] = row[valueIndex].replaceAll("[^- :.,!@#$%^&*(\')_+{}=öäüßÖÄÜß?A-Za-z0-9]", "");
            }
        }
    }

    public List<String[]> getTable() {
        return table;
    }

    public void setTable(List<String[]> table) {
        this.table = table;
    }
}
