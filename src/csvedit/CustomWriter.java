package csvedit;

import java.io.*;
import java.util.List;

public class CustomWriter {
    public CustomWriter() {
    }

    public void write(String fileName, List<String[]> table) throws IOException {
        write(fileName, table, ',');
    }
    public void write(String fileName, List<String[]> table, char delimiter) throws IOException {
        write(fileName, table, delimiter, true);
    }
    public void write(String fileName, List<String[]> table, char delimiter, boolean useQuotes) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(fileName);
        OutputStreamWriter inputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
        BufferedWriter bufferedWriter = new BufferedWriter(inputStreamWriter);

        for (int rowIndex = 0; rowIndex < table.size(); ++rowIndex) {
            String[] row = table.get(rowIndex);
            for (int valueIndex = 0; valueIndex < row.length; ++valueIndex){
                if (useQuotes && (row[valueIndex].length() > 0 || row.length > 1)) {
                    bufferedWriter.write("\""+row[valueIndex]+"\"");
                } else {
                    bufferedWriter.write(row[valueIndex]);
                }
                if (valueIndex != row.length -1) {
                    bufferedWriter.write(delimiter);
                }
            }
            bufferedWriter.write("\n");
        }
        bufferedWriter.flush();
    }
}