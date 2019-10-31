package org.sudoku;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Sudoku sudoku = SudokuGenerator.generate();
        sudoku126(sudoku);

        sudoku.solve();
        sudoku.print();
    }

    private static void sudoku128(Sudoku sudoku) {
        sudoku.setCellValue(4,1,9);
        sudoku.setCellValue(5,1,2);
        sudoku.setCellValue(6,1,3);

        sudoku.setCellValue(2,2,5);
        sudoku.setCellValue(5,2,7);
        sudoku.setCellValue(8,2,8);

        sudoku.setCellValue(1,3,9);
        sudoku.setCellValue(9,3,4);

        sudoku.setCellValue(3,4,1);
        sudoku.setCellValue(7,4,6);

        sudoku.setCellValue(1,5,3);
        sudoku.setCellValue(2,5,7);
        sudoku.setCellValue(8,5,2);
        sudoku.setCellValue(9,5,8);

        sudoku.setCellValue(3,6,4);
        sudoku.setCellValue(7,6,3);

        sudoku.setCellValue(1,7,4);
        sudoku.setCellValue(9,7,7);

        sudoku.setCellValue(2,8,8);
        sudoku.setCellValue(5,8,9);
        sudoku.setCellValue(8,8,6);

        sudoku.setCellValue(4,9,6);
        sudoku.setCellValue(5,9,3);
        sudoku.setCellValue(6,9,1);
    }
    private static void sudoku126(Sudoku sudoku) {
        sudoku.setCellValue(3,1,1);
        sudoku.setCellValue(7,1,7);

        sudoku.setCellValue(2,2,2);
        sudoku.setCellValue(3,2,5);
        sudoku.setCellValue(5,2,4);
        sudoku.setCellValue(7,2,6);
        sudoku.setCellValue(8,2,1);

        sudoku.setCellValue(1,3,6);
        sudoku.setCellValue(5,3,3);
        sudoku.setCellValue(9,3,4);

        sudoku.setCellValue(1,4,2);
        sudoku.setCellValue(4,4,7);
        sudoku.setCellValue(6,4,9);
        sudoku.setCellValue(9,4,6);

        sudoku.setCellValue(1,6,8);
        sudoku.setCellValue(4,6,1);
        sudoku.setCellValue(6,6,5);
        sudoku.setCellValue(9,6,2);

        sudoku.setCellValue(1,7,7);
        sudoku.setCellValue(5,7,9);
        sudoku.setCellValue(9,7,5);

        sudoku.setCellValue(2,8,3);
        sudoku.setCellValue(3,8,2);
        sudoku.setCellValue(5,8,7);
        sudoku.setCellValue(7,8,8);
        sudoku.setCellValue(8,8,9);

        sudoku.setCellValue(3,9,8);
        sudoku.setCellValue(7,9,2);
    }
}
