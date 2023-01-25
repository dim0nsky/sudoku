package org.sudoku;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Sudoku {
    public List<Cell> cells;

    public Sudoku(){
        List<Cell> initCells = new ArrayList<Cell>();
        for(int i = 1; i < 10; i++){
            for(int j = 1; j < 10; j++){
                initCells.add(new Cell(j,i));
            }
        }
        this.cells = initCells;
    }

    public void setCellValue(int x, int y, int value){
        this.cells.get(x - 1 + (y - 1) * 9).setCell(x, y, value);

    }

    public void recalcCell(Cell a){
        a.possibleValues.clear();
        for(int i = 1; i < 10; i++){
            if(fitTest(a, i)){
                a.possibleValues.add(i);
            }
        }
    }

    public boolean fitTest(Cell a, int n){
        List<Cell> horiz = cells.stream().filter(c->c.getY()==a.getY()).collect(Collectors.toList());
        List<Cell> vert = cells.stream().filter(c->c.getX()==a.getX()).collect(Collectors.toList());
        List<Cell> sq = cells.stream().filter(c->c.getSquare()==a.getSquare()).collect(Collectors.toList());
        for (Cell ce : horiz) {
            if(ce.getVal() == n){
                return false;
            }
        }
        for (Cell ce : vert) {
            if(ce.getVal() == n){
                return false;
            }
        }

        for (Cell ce : sq) {
            if(ce.getVal() == n){
                return false;
            }
        }
        return true;
    }

    public List<Cell> solveLVL1(){
        this.cells.stream().filter(cell -> cell.getVal()==0).forEach(cel-> this.recalcCell(cel));

        List<Cell> easyCells = cells.stream().filter(c->c.possibleValues.size() == 1).collect(Collectors.toList());
        for (Cell cel : easyCells) {
            cel.setValue(cel.possibleValues.get(0));
        }
        return easyCells;
    }

    public boolean isLast(){
        for (Cell cell : this.cells) {
            if(cell.getVal() == 0){
                return false;
            }
        }
        return true;
    }

    public int solving(){
        solveLVL1();
        
        for (Cell cell : this.cells) {
            if(cell.getVal() == 0){
                for (int i = 0; i < 10;i++) {
                    if(fitTest(cell, i)){
                        cell.setValue(i);
                        solving();
                    }
                }
                if(!isLast()){
                    cell.setValue(0);

                    for (Cell easyCell : solveLVL1()) {
                        easyCell.setValue(0);
                    }
                    
                }
                return 0;
            }
        }
        return 0;

    }


    /*
     I want to make the solving process more efficient by only looping thrrough possible values instead of all 1 through 9,
     But it gives me an error because I am looping through possible values and at the same time altering them

     * public int solving(){
        solveLVL1();
        this.cells.stream().filter(cell -> cell.getVal()==0).forEach(cel-> this.recalcCell(cel));

        for (Cell cell : this.cells) {
            if(cell.getVal() == 0){
                for (int i: cell.possibleValues) {
                    if(fitTest(cell, i)){
                        cell.setValue(i);
                        solving();
                    }
                }

                if(!isLast()){
                    cell.setValue(0);

                    for (Cell easyCell : solveLVL1()) {
                        easyCell.setValue(0);
                    }
                    
                }
                return 0;
            }
        }
        return 0;

    }
     */

    public void solution(){
        for (Cell cell : this.cells) {
            System.out.print(cell.getVal() + " ");
            if(cell.getX()==9){
                System.out.println();
            }
        }
        System.out.println();

    }

    
}
