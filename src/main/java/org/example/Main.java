package org.example;

import org.example.Exceptions.ImpossibleSudokuError;
import org.example.fields.SimpleSudokuField;
import org.example.fields.SmartSudokuField;
import org.example.solvers.BruteForceSolver;

import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        String path = "/Users/julianjohn/programmier_projekte/Sudoku_Solver_Vergleich/example_games/game2_simple.csv";

        try {
            Instant normalField_start = Instant.now();
            int[][] numberArray = SudokuReader.readSudokuFromCSV(path);
            SimpleSudokuField exampleField = new SimpleSudokuField(numberArray);
            BruteForceSolver bruteSolver = new BruteForceSolver(exampleField);
            try {
                SudokuField result = bruteSolver.solve();
                Instant normalField_end = Instant.now();
                long elapsedTime = Duration.between(normalField_start, normalField_end).toMillis();
                System.out.println(result + "Solved in " + elapsedTime + " milliseconds");
            }
            catch (ImpossibleSudokuError error) {
                System.out.println("Brute Solver hat keine Lösung gefunden");
            }
        } catch (FileNotFoundException error) {
            System.out.println("Die Datei wurde nicht gefunden: " + error.getMessage());
        }
        /*
        try {
            Instant smartField_start = Instant.now();
            int[][] numberArray_2 = SudokuReader.readSudokuFromCSV(path);
            SmartSudokuField exampleField = new SmartSudokuField(numberArray_2);
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
        } catch (FileNotFoundException error) {
            System.out.println("Die Datei wurde nicht gefunden: " + error.getMessage());
        }
        
         */
    }
}