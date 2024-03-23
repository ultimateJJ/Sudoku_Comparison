package org.example;

import java.util.List;

public abstract class SudokuField {
    protected int[][] fieldEntries;
    // int[y][x] where x is horizontal and y vertical

    public abstract int getSideLength();

    public abstract void setCell(int row_index, int column_index, int newValue);

    public abstract boolean isCorrect();

    public abstract List<Integer> possibleNumbersInCell(int x, int y);

    public abstract SudokuField deepCopy();

    public abstract int getCell(int row_index, int column_index);


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int y = 0; y < getSideLength(); y++) {
            for (int x = 0; x < getSideLength(); x++) {
                result.append(fieldEntries[y][x]);
                result.append(" ");
                if ((x + 1) % 3 == 0){
                    result.append("|");
                }
            }
            result.append("\n");
            if ((y + 1) % 3 == 0){
                result.append("______________\n");
            }
        }

        return result.toString();
    }
}
