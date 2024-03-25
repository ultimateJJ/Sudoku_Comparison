package org.example.solvers;

import org.example.Exceptions.ImpossibleSudokuError;
import org.example.SudokuField;
import org.example.solvers.BruteForceSolver;

import java.util.Optional;

public class BranchExplorationThread extends Thread{

    public Optional<SudokuField> result = Optional.empty();

    private final ParrallelBruteForceSolver solver;

    public BranchExplorationThread(SudokuField inputField) {
        super();
        this.solver = new ParrallelBruteForceSolver(inputField);
    }

    @Override
    public void run() {
        try {
            this.result = Optional.ofNullable(this.solver.solve());
        } catch (ImpossibleSudokuError | InterruptedException error) {
            System.out.println(error.getMessage());
        }
    }
}
