package org.sudoku;

public class Main {

    public static void main(String[] args) {
        
            Sudoku puzzle = new Sudoku();

            puzzle146(puzzle);
            puzzle.solving();
            puzzle.solution();

            
        }

    public static void puzzle146(Sudoku sudoku){
        sudoku.setCellValue(3,1,2);
        sudoku.setCellValue(4,1,9);
        sudoku.setCellValue(5,1,5);
        sudoku.setCellValue(6,1,7);
        sudoku.setCellValue(7,1,8);

        sudoku.setCellValue(1,2,8);
        sudoku.setCellValue(2,2,7);
        sudoku.setCellValue(8,2,9);
        sudoku.setCellValue(9,2,6);


        sudoku.setCellValue(2,4,5);
        sudoku.setCellValue(4,4,1);
        sudoku.setCellValue(6,4,8);
        sudoku.setCellValue(8,4,7);

        sudoku.setCellValue(1,5,4);
        sudoku.setCellValue(9,5,2);

        sudoku.setCellValue(2,6,6);
        sudoku.setCellValue(4,6,4);
        sudoku.setCellValue(6,6,3);
        sudoku.setCellValue(8,6,5);


        sudoku.setCellValue(1,8,9);
        sudoku.setCellValue(2,8,4);
        sudoku.setCellValue(8,8,8);
        sudoku.setCellValue(9,8,1);

        sudoku.setCellValue(3,9,6);
        sudoku.setCellValue(4,9,5);
        sudoku.setCellValue(5,9,8);
        sudoku.setCellValue(6,9,9);
        sudoku.setCellValue(7,9,3);
    }
}
