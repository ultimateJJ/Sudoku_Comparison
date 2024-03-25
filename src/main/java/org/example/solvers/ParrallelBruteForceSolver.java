package org.example.solvers;

import org.example.Exceptions.ImpossibleSudokuError;
import org.example.SudokuField;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ParrallelBruteForceSolver {

    SudokuField taskField;

    public ParrallelBruteForceSolver(SudokuField inputField) {
        this.taskField = inputField.deepCopy();
    }

    public SudokuField solve() throws ImpossibleSudokuError, InterruptedException {
        if (taskField.isCorrect()) {
            return taskField;
        }
        else {
            int[] firstCoordinates = findFirstEmpty();
            List<Integer> freeNumbers = taskField.possibleNumbersInCell(firstCoordinates[0], firstCoordinates[1]);
            SudokuField newTaskField = taskField.deepCopy();
            //list for all started threads
            List<BranchExplorationThread> solvingThreads = new LinkedList<>();
            for (int testNumber : freeNumbers) {
                // use one of the free numbers and start a new solving thread.
                newTaskField.setCell(firstCoordinates[1], firstCoordinates[0], testNumber);
                BranchExplorationThread explorationThread = new BranchExplorationThread(newTaskField);
                explorationThread.start();
                solvingThreads.add(explorationThread);
            }
            for (BranchExplorationThread thread : solvingThreads) {
                thread.join();
                if (thread.result.isPresent()) {
                    return thread.result.get();
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
