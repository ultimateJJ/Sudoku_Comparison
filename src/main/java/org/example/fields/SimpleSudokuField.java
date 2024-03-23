package org.example.fields;

import org.example.SudokuField;

import java.util.LinkedList;
import java.util.List;

public class SimpleSudokuField extends SudokuField {
    private int sideLength;
    private int divider;

    public SimpleSudokuField(int[][] input) {
        assert input.length == input[0].length: "Side Lengths not equal!";
        assert input.length < 128 : "Side length bigger then 127!";
        this.sideLength = input.length;
        this.fieldEntries = input.clone();
        double sideLengthRoot = Math.sqrt(sideLength);
        assert sideLengthRoot % 1 == 0 : "Side Length not a square!";
        this.divider = (int) sideLengthRoot;
    }

    @Override
    public SimpleSudokuField deepCopy() {
        int[][] newField = new int[sideLength][sideLength];
        for (int row_index = 0; row_index < sideLength; row_index++) {
            newField[row_index] = this.fieldEntries[row_index].clone();
        }
        return new SimpleSudokuField(newField);
    }

    @Override
    public int getCell(int row_index, int column_index) {
        return fieldEntries[row_index][column_index];
    }

    @Override
    public int getSideLength() {
        return this.sideLength;
    }

    @Override
    public void setCell(int row_index, int column_index, int newValue) {
        this.fieldEntries[row_index][column_index] = newValue;
    }

    @Override
    public boolean isCorrect() {
        for (int i = 0; i < sideLength; i++) {
            boolean allOnlyOnce = allNumbersOnlyOnce(getColumn(i))
                    && allNumbersOnlyOnce(getRow(i))
                    && allNumbersOnlyOnce(getQuadrant(i));
            if (!allOnlyOnce) {
                return false;
            }
        }
        return true;
    }



    public static int[] countAllNumbers(int[] numberSet) {
        int[] counter = new int[numberSet.length + 1];
        for (int number: numberSet) {
            counter[number]++;
        }
        return counter;
    }

    public static boolean allNumbersOnlyOnce(int[] numberSet) {
        int[] counter = new int[numberSet.length + 1];
        for (int number: numberSet) {
            if (number == 0) {
                // 0 signals that the number is not yet set
                return false;
            }

            counter[number]++;
            if (counter[number] > 1) {
                return false;
            }
        }
        return true;
    }

    public int[] getRow(int i){
        return this.fieldEntries[i];
    }

    public int[] getColumn(int i){
        int[] column = new int[this.sideLength];
        for (int column_index = 0; column_index < this.sideLength; column_index++) {
            column[column_index] = this.fieldEntries[column_index][i];
        }
        return column;
    }

    public int[] getQuadrant(int[] cellCoordinates) {
        int x = Math.floorDiv(cellCoordinates[0], 3);
        int y = Math.floorDiv(cellCoordinates[1], 3);

        return getQuadrantHelper(x, y);
    }

    public int[] getQuadrant(int quadrant_number) {
        int x_offset = quadrant_number%divider;
        int y_offset = Math.floorDiv(quadrant_number, divider);

        return getQuadrantHelper(x_offset, y_offset);
    }

    private int[] getQuadrantHelper(int x_offset, int y_offset) {
        int[] quadrant = new int[sideLength];
        for (int x = 0; x < divider; x++) {
            for (int y = 0; y < divider; y++) {
                /*
                int newX = (x_offset * divider) + x;
                int newY = (y_offset * divider) + y;
                int quadrantIndex = x + (y * divider);
                int resultEntry = this.fieldEntries[newY][newX];
                quadrant[quadrantIndex] = resultEntry;
                */
                quadrant[x + (y * divider)] = this.fieldEntries[(y_offset * divider) + y][(x_offset * divider) + x];
            }

        }
        return quadrant;
    }

    private static int[] addIntArray(int[] arr1, int[] arr2, int[] arr3) {
        int[] result = new int[arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            result[i] = arr1[i] + arr2[i] + arr3[i];
        }
        return result;
    }

    @Override
    public List<Integer> possibleNumbersInCell(int x, int y){
        List<Integer> result = new LinkedList<>();
        int[] rowCount = countAllNumbers(getRow(y));
        int[] columnCount = countAllNumbers(getColumn(x));
        int[] quadrantCount = countAllNumbers(getQuadrant(new int[]{x, y}));

        int[] combinedNumbers = addIntArray(rowCount, columnCount, quadrantCount);

        for (int index = 1; index < sideLength + 1; index++) {
            if (combinedNumbers[index] == 0){
                result.add(index);
            }
        }
        return result;
    }
}
