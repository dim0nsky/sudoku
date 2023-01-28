package org.sudoku;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Sudoku {
    private List<Cell> cells;

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
        a.clearPossibleValues();
        for(int i = 1; i < 10; i++){
            if(fitTest(a, i)){
                a.addPossibleValue(i);
            }
        }
    }

    public boolean fitTest(Cell a, int n){
        List<Cell> horiz = cells.stream().filter(c->c.getY()==a.getY()).collect(Collectors.toList());
        List<Cell> vert = cells.stream().filter(c->c.getX()==a.getX()).collect(Collectors.toList());
        List<Cell> sq = cells.stream().filter(c->c.getSquare()==a.getSquare()).collect(Collectors.toList());
        for (Cell ce : horiz) {
            if(ce.getValue() == n){
                return false;
            }
        }
        for (Cell ce : vert) {
            if(ce.getValue() == n){
                return false;
            }
        }

        for (Cell ce : sq) {
            if(ce.getValue() == n){
                return false;
            }
        }
        return true;
    }

    public List<Cell> solveLVL1(){
        this.cells.stream().filter(cell -> cell.getValue()==0).forEach(cel-> this.recalcCell(cel));

        List<Cell> easyCells = cells.stream().filter(c->c.getPossibleValues().size() == 1).collect(Collectors.toList());
        for (Cell cel : easyCells) {
            cel.setValue(cel.getPossibleValues().get(0));
        }
        return easyCells;
    }

    public boolean isLast(){
        return this.cells.stream().noneMatch(t -> t.getValue() == 0);
    }

    public void solving(){
        List<Cell>easyCells = solveLVL1();
        
        for (Cell cell : this.cells) {
            if(cell.getValue() == 0){
                for (int i = 0; i < 10;i++) {
                    if(fitTest(cell, i)){
                        cell.setValue(i);
                        solving();
                    }
                }
                if(!isLast()){
                    cell.setValue(0);

                    easyCells.stream().forEach(element->element.setValue(0));
                    
                }
                return;
            }
        }
        return;

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
            System.out.print(cell.getValue() + " ");
            if(cell.getX()==9){
                System.out.println();
            }
        }
        System.out.println();

    }

    
}
