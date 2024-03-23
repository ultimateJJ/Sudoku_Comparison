package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SudokuReader {
    public static int[][] readSudokuFromCSV(String path) throws FileNotFoundException{
        List<String[]> rawRecords = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                rawRecords.add(getRecordFromLine(scanner.nextLine()));
            }
        }
        String[][] stringArray = rawRecords.toArray(new String[0][0]);
        return convert2dStringArrayToInt(stringArray);
    }

    private static String[] getRecordFromLine(String line) {
        List<String> values = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values.toArray(new String[0]);
    }

    private static int[][] convert2dStringArrayToInt(String[][] inputArray) {
        int[][] output = new int[inputArray.length][inputArray[0].length];

        for (int i = 0; i < inputArray.length; i++) {
            for (int j = 0; j < inputArray[0].length; j++) {
                output[i][j] = Integer.parseInt(inputArray[i][j]);
            }
        }

        return output;
    }
}
