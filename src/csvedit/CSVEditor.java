package csvedit;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSVEditor {
    private String delimiter;
    private String inputFilePath;
    private String outputFilePath;

    public CSVEditor (String inputFilePath, String outputFilePath, String delimiter) {
        this.delimiter = delimiter;
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;

        System.out.println(inputFilePath);
    }

    public void start() {
        File filePattern = new File(inputFilePath);
        String inputPattern = filePattern.getName();
        File[] listOfFiles = filePattern.getParentFile().listFiles();

        int success = 0;
        int fail = 0;
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                inputPattern = inputPattern.replace("*", "\\w*");
                inputPattern = inputPattern.replace("_", "\\w");

                Pattern pattern = Pattern.compile(inputPattern);
                Matcher matcher = pattern.matcher(listOfFiles[i].getName());
                if (matcher.matches()) {
                    if (editFile(filePattern.getParentFile() +"\\" + listOfFiles[i].getName())){
                        ++success;
                    } else {
                        ++fail;
                    }
                }
            }
        }
        System.out.println("Done: " + success + " / " + (success+fail) + " CSV files edited");
    }

    private boolean editFile(String fileName) {
        System.out.println("Editing file " + fileName + " ...");
        CustomReader customReader = new CustomReader();
        List<String[]> table;
        try {
            customReader.read(fileName, delimiter.toCharArray()[0], true);
            table = customReader.getTable();
        } catch (Exception e) {
            System.out.println("Could not read input file " + fileName);
            return false;
        }

        try {
            CustomWriter customWriter = new CustomWriter();
            customWriter.write(outputFilePath, table, delimiter.toCharArray()[0]);
        } catch (Exception e) {
            System.out.println("Could not write output file " + outputFilePath);
            return false;
        }
        return true;
    }
}
