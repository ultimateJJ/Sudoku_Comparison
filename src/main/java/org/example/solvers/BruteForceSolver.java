package org.example.solvers;

import org.example.Exceptions.ImpossibleSudokuError;
import org.example.SudokuField;

import java.util.Arrays;
import java.util.List;

public class BruteForceSolver {

    SudokuField taskField;

    public BruteForceSolver(SudokuField inputField) {
        taskField = inputField;
    }

    public SudokuField solve() throws ImpossibleSudokuError {
        if (taskField.isCorrect()) {
            return taskField;
        }
        else {
            int[] firstCoordinates = findFirstEmpty();
            List<Integer> freeNumbers = taskField.possibleNumbersInCell(firstCoordinates[0], firstCoordinates[1]);
            SudokuField newTaskField = taskField.deepCopy();
            for (int testNumber : freeNumbers) {
                try {
                    newTaskField.setCell(firstCoordinates[1], firstCoordinates[0], testNumber);
                    BruteForceSolver newAgent = new BruteForceSolver(newTaskField);
                    return newAgent.solve();
                }
                catch (ImpossibleSudokuError error) {
                    System.out.println(error.getMessage() + " in Coordinate: " + Arrays.toString(firstCoordinates));
                }
            }
            throw new ImpossibleSudokuError("All free numbers exhausted without solution");
        }
    }

    private int[] findFirstEmpty() throws ImpossibleSudokuError {
        for (int x_index = 0; x_index < this.taskField.getSideLength(); x_index++) {
            for (int y_index = 0; y_index < this.taskField.getSideLength(); y_index++) {
                if (taskField.getCell(y_index, x_index) == 0) {
                    return new int[]{x_index, y_index};
                }
            }
        }
        throw new ImpossibleSudokuError("No entry found that isn't already Set");
    }


}
