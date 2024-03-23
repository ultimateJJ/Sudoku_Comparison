package org.example.fields;

import org.example.SudokuField;

import java.util.LinkedList;
import java.util.List;

public class SmartSudokuField extends SudokuField {
    private int[][][] possibleEntries;
    // y and x same. The last array is the amount of entries with the number of the index
    private int sideLength;
    private int divider;

    public SmartSudokuField(int[][] input) {
        assert input.length == input[0].length: "Side Lengths not equal!";
        this.sideLength = input.length;
        this.fieldEntries = input;
        double sideLengthRoot = Math.sqrt(sideLength);
        assert sideLengthRoot % 1 == 0 : "Side Length not a squared number!";
        this.divider = (int) sideLengthRoot;
        //possible entries: first two are the coordinates and
        // the last ist for the n possible numbers plus 1 for the unset cell
        possibleEntries = new int[sideLength][sideLength][sideLength + 1];

        //populating the possibleEntries with the counted numbers
        for (int row_index = 0; row_index < sideLength; row_index++) {
            for (int column_index = 0; column_index < sideLength; column_index++) {
                possibleEntries[row_index][column_index] = countAllNumbers(row_index, column_index);
            }
        }
    }

    public SmartSudokuField(int[][] input, int[][][] possibleEntries) {
        assert input.length == input[0].length: "Side Lengths not equal!";
        this.sideLength = input.length;
        this.fieldEntries = input;
        double sideLengthRoot = Math.sqrt(sideLength);
        assert sideLengthRoot % 1 == 0 : "Side Length not a square!";
        this.divider = (int) sideLengthRoot;
        this.possibleEntries = possibleEntries;
    }

    @Override
    public SmartSudokuField deepCopy() {
        int[][] newField = new int[sideLength][sideLength];
        int[][][] newPossibleEntries = new int[sideLength][sideLength][sideLength + 1];
        for (int row_index = 0; row_index < sideLength; row_index++) {
            newField[row_index] = this.fieldEntries[row_index].clone();
            for (int column_index = 0; column_index < sideLength; column_index++) {
                newPossibleEntries[row_index][column_index] = this.possibleEntries[row_index][column_index].clone();
            }
        }
        return new SmartSudokuField(newField, newPossibleEntries);
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

    @Override
    public int getSideLength() {
        return this.sideLength;
    }

    @Override
    public void setCell(int row_index, int column_index, int newValue) {
        int oldValue = fieldEntries[row_index][column_index];
        fieldEntries[row_index][column_index] = newValue;

        //updating the possible entries
        for (int i = 0; i < sideLength; i++) {
            possibleEntries[row_index][i][oldValue] -= 1;
            possibleEntries[i][column_index][oldValue] -= 1;

            possibleEntries[row_index][i][newValue] += 1;
            possibleEntries[i][column_index][newValue] += 1;
        }

        int column_offset = Math.floorDiv(column_index, 3);
        int row_offset = Math.floorDiv(row_index, 3);

        for (int col = 0; col < divider; col++) {
            for (int row = 0; row < divider; row++) {
                this.possibleEntries[(row_offset * divider) + row][(column_offset * divider) + col][oldValue] -= 1;
                this.possibleEntries[(row_offset * divider) + row][(column_offset * divider) + col][newValue] += 1;
            }

        }
    }

    @Override
    public int getCell(int row_index, int column_index) {
        return this.fieldEntries[row_index][column_index];
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

    private int[] countAllNumbers(int row_index, int column_index) {
        int[] rowCount = countAllNumbers(getRow(row_index));
        int[] columnCount = countAllNumbers(getColumn(column_index));
        int[] quadrantCount = countAllNumbers(getQuadrant(new int[]{column_index, row_index}));

        return addIntArray(rowCount, columnCount, quadrantCount);
    }

    @Override
    public List<Integer> possibleNumbersInCell(int x, int y){
        List<Integer> result = new LinkedList<>();

        int[] combinedNumbers = this.possibleEntries[y][x];

        for (int index = 1; index < sideLength + 1; index++) {
            if (combinedNumbers[index] == 0){
                result.add(index);
            }
        }
        return result;
    }
}
