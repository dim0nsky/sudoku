package org.sudoku;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Cell implements Comparable {

  private Set<Lane> belongToLanes = new HashSet<>(3);
  private Set<Byte> possibleNumbers = new HashSet<>(9);
  private Byte value = null;
  private byte positionX;
  private byte positionY;

  public static final Byte[] numbers = {1,2,3,4,5,6,7,8,9};

  public Cell(byte positionX, byte positionY) {
    this.positionX = positionX;
    this.positionY = positionY;
    this.possibleNumbers.addAll(Arrays.asList(numbers));
  }


  public void setBelongToLanes(Lane vLane, Lane hLane, Lane square) {
    this.belongToLanes.add(vLane);
    this.belongToLanes.add(hLane);
    this.belongToLanes.add(square);
  }

  public Set<Byte> getPossibleNumbers() {
    return possibleNumbers;
  }

  public Set<Cell> setNumberValue(byte value) {
    if(this.value!=null) {
      throw new SudokuException("Tried to set \""+value+"\" for "+this.debug());
    }
    this.possibleNumbers.clear();
    this.value = value;
    System.out.println(debug());

    Set<Cell> cells = new HashSet<>();

    for(Lane lane: belongToLanes) {
      cells.addAll(lane.collectChangedCells(value));
    }
    return cells;
  }

  public boolean removePossibleNumber(byte value) {
    if(this.value!=null) {
      return false;
    }
//    System.out.println("cell["+positionX+"]["+positionY+"] = " + this +" remove "+value);
    return possibleNumbers.remove(value);
  }

  public boolean contains(byte value) {
    return possibleNumbers.contains(value);
  }

  public int possibleNumbersSize() {
    return possibleNumbers.size();
  }

  @Override
  public int compareTo(Object o) {
    if (value!=null) return -1;
    Cell cell = (Cell) o;
    return cell.possibleNumbersSize() - this.possibleNumbersSize();
  }

  public Set<Lane> getBelongToLanes(){
    return belongToLanes;
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
