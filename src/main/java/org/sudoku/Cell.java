package org.sudoku;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Cell implements Comparable {

  private Lane[] lanes;
  private Set<Byte> possibleNumbers;
  private Byte value=null;
  private byte positionX;
  private byte positionY;
  private Sudoku sudoku;

  public static Byte[] numbers = {1,2,3,4,5,6,7,8,9};

  public Cell(byte positionX, byte positionY) {
    this.positionX = positionX;
    this.positionY = positionY;
    this.possibleNumbers = new HashSet<>(9);
    possibleNumbers.addAll(Arrays.asList(numbers));
  }


  public void setLanes(Lane... lanes) {
    this.lanes = lanes;
  }

  public Set<Byte> getPossibleNumbers() {
    return possibleNumbers;
  }

  public void setPossibleNumbers(Set<Byte> possibleNumbers) {
    this.possibleNumbers = possibleNumbers;
  }

  public Byte getValue() {
    return value;
  }

  public void setValue(Byte value) {
    this.value = value;
  }

  public byte getPositionX() {
    return positionX;
  }

  public void setPositionX(byte positionX) {
    this.positionX = positionX;
  }

  public byte getPositionY() {
    return positionY;
  }

  public void setPositionY(byte positionY) {
    this.positionY = positionY;
  }

  public Set<Cell> set(byte value) {
    Set<Cell> cells = new HashSet<>();
    if(this.value!=null) {
      return cells;
    }
    this.possibleNumbers.clear();
    this.value = value;
//    System.out.println("cell["+positionX+"]["+positionY+"] = " + this +" set "+value);
    for(Lane lane: lanes) {
      cells.addAll(lane.remove(this));
    }
    return cells;
  }

  public Set<Cell> remove(byte value) {
    Set<Cell> cells = new HashSet<>();
    if(this.value!=null) {
      if(this.value == value){
        throw new SudokuException("Trying to remove value "+value+" from "+this.debug());
      }
      return cells;
    }
//    System.out.println("cell["+positionX+"]["+positionY+"] = " + this +" remove "+value);
    if(possibleNumbers.remove(value)){
      cells.add(this);
    }
    return cells;
  }

  public boolean contains(byte value) {
    return possibleNumbers.contains(value);
  }

  public int size() {
    return possibleNumbers.size();
  }

  @Override
  public int compareTo(Object o) {
    if(value!=null) return -1;
    Cell cell = (Cell) o;
    return this.size() <= cell.size() ? 1 : -1;
  }

  public Set<Lane> getLanes(){
    return new HashSet<>(Arrays.asList(lanes));
  }


  public String debug(){
    return String.format("Cell[%s,%s]='%s'",positionX,positionY,possibleNumbers.isEmpty()? value: possibleNumbers);
  }
  @Override
  public String toString() {
    if(value!=null){
      return "    "+value+"    ";
    }
    Byte[] nums = possibleNumbers.toArray(new Byte[0]);
    int size= nums.length;
    int leftAppend = (9-size)/2;
    StringBuilder sb = new StringBuilder();
    for(int i=0; i<9; i++) {
      if(i<leftAppend || i>=leftAppend+size) {
        sb.append(" ");
      } else {
        sb.append(nums[i-leftAppend]);
      }
    }
    return sb.toString();
  }
}
