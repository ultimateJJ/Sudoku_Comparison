import org.example.fields.SimpleSudokuField;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Arrays;

public class UnitTesting {

    @Test
    void getQuadrantCorrect() {
        int[][] test_array_4 =
                {{0, 0, 1, 1},
                        {0, 0, 1, 1},
                        {2, 2, 3, 3},
                        {2, 2, 3, 3}};

        SimpleSudokuField testField_4= new SimpleSudokuField(test_array_4);

        for (int i = 0; i < testField_4.getSideLength(); i++) {
            int[] result = testField_4.getQuadrant(i);
            int[] expected = new int[testField_4.getSideLength()];
            Arrays.fill(expected, i);
            Assertions.assertArrayEquals(expected, result);
        }
        int[][] test_array_9 =
                {{0, 0, 0, 1, 1, 1, 2, 2, 2},
                        {0, 0, 0, 1, 1, 1, 2, 2, 2},
                        {0, 0, 0, 1, 1, 1, 2, 2, 2},
                        {3, 3, 3, 4, 4, 4, 5, 5, 5},
                        {3, 3, 3, 4, 4, 4, 5, 5, 5},
                        {3, 3, 3, 4, 4, 4, 5, 5, 5},
                        {6, 6, 6, 7, 7, 7, 8, 8, 8},
                        {6, 6, 6, 7, 7, 7, 8, 8, 8},
                        {6, 6, 6, 7, 7, 7, 8, 8, 8}};


        SimpleSudokuField testField_9= new SimpleSudokuField(test_array_4);

        for (int i = 0; i < testField_9.getSideLength(); i++) {
            int[] result = testField_9.getQuadrant(i);
            int[] expected = new int[testField_9.getSideLength()];
            Arrays.fill(expected, i);
            Assertions.assertArrayEquals(expected, result);
        }

        int[][] test_array_4_all_same =
                {{0, 1, 0, 1},
                        {2, 3, 2, 3},
                        {0, 1, 0, 1},
                        {2, 3, 2, 3}};

        SimpleSudokuField testField_4_all_same= new SimpleSudokuField(test_array_4_all_same);

        for (int i = 0; i < testField_4_all_same.getSideLength(); i++) {
            int[] result = testField_4_all_same.getQuadrant(i);
            int[] expected = {0, 1, 2, 3};
            Assertions.assertArrayEquals(expected, result);
        }

    }
}
