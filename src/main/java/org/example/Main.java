package org.example;

import org.example.Exceptions.ImpossibleSudokuError;
import org.example.fields.SimpleSudokuField;
import org.example.fields.SmartSudokuField;
import org.example.solvers.BruteForceSolver;
import org.example.solvers.ParrallelBruteForceSolver;

import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        String path = "/Users/julianjohn/programmier_projekte/Sudoku_Solver_Vergleich/example_games/game2_simple.csv";
        try {
            SudokuReader reader = new SudokuReader(path);

            test_parrallel_threading(reader);
            test_single_threading(reader);
            test_smartField(reader);


        } catch (FileNotFoundException error) {
            System.out.println("Die Datei wurde nicht gefunden: " + error.getMessage());
        }
    }

    private static void test_parrallel_threading(SudokuReader reader) {
        Instant parrallel_start = Instant.now();
        SimpleSudokuField exampleField_parrallel = new SimpleSudokuField(reader.getField());
        ParrallelBruteForceSolver solver = new ParrallelBruteForceSolver(exampleField_parrallel);

        try {
            SudokuField resultField = solver.solve();
            Instant parrallel_end = Instant.now();
            long elapsedTime = Duration.between(parrallel_start, parrallel_end).toMillis();
            System.out.println(resultField + "Solved in " + elapsedTime + " milliseconds");

        } catch (ImpossibleSudokuError error) {
            System.out.println("Brute Solver hat keine Lösung gefunden");
        } catch (InterruptedException error) {
            System.out.println("One thread was interrupted");
        }
    }

    private static void test_single_threading(SudokuReader reader) {
        Instant singleThread_start = Instant.now();
        SimpleSudokuField exampleField_singleThread = new SimpleSudokuField(reader.getField());
        BruteForceSolver bruteSolver = new BruteForceSolver(exampleField_singleThread);
        try {
            SudokuField result = bruteSolver.solve();
            Instant normalField_end = Instant.now();
            long elapsedTime = Duration.between(singleThread_start, normalField_end).toMillis();
            System.out.println(result + "Solved in " + elapsedTime + " milliseconds");
        }
        catch (ImpossibleSudokuError error) {
            System.out.println("Brute Solver hat keine Lösung gefunden");
        }
    }

    private static void test_smartField(SudokuReader reader) {
        Instant smartField_start = Instant.now();
        SmartSudokuField exampleField = new SmartSudokuField(reader.getField());
        BruteForceSolver bruteSolver = new BruteForceSolver(exampleField);
        try {
            SudokuField result = bruteSolver.solve();
            Instant smartField_end = Instant.now();
            long elapsedTime = Duration.between(smartField_start, smartField_end).toMillis();
            System.out.println(result + "Solved in " + elapsedTime + " milliseconds");
        }
        catch (ImpossibleSudokuError error) {
            System.out.println("Brute Solver hat keine Lösung gefunden");
        }

    }


}