package org.sudoku;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private int y;
    private int x;
    private int value;
    public List<Integer> possibleValues;
    private byte square;
    public byte id;

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
        this.id = (byte)(x - 1 + (y - 1) * 9);
        this.value = 0;
        this.square = (byte)(3*((y-1)/3) + (x-1)/3);
        this.possibleValues = new ArrayList<Integer>();
    }

    public void setCell(int x, int y, int value){
        this.x = x;
        this.y = y;
        this.id = (byte)(x - 1 + (y - 1) * 9);
        this.value = value;
        this.square = (byte)(3*((y-1)/3) + (x-1)/3);
        this.possibleValues = new ArrayList<Integer>();
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getVal(){
        return this.value;
    }

    public byte getSquare(){
        return this.square;
    }

    public void setValue(int value){
        this.value = value;
    }

    public byte getId(){
        return this.id;
    }

    public void printPossVals(){
        System.out.print(this.x + " " + this.y + " square:" + this.square + "||");
        for(int val: possibleValues){
            System.out.print(val + " |");
        }
    }

}
