package org.sudoku;

import java.util.HashSet;
import java.util.Set;

public class SudokuGenerator {
  static Lane[] hLanes = {
      new Lane(1,1),
      new Lane(1,2),
      new Lane(1,3),
      new Lane(1,4),
      new Lane(1,5),
      new Lane(1,6),
      new Lane(1,7),
      new Lane(1,8),
      new Lane(1,9)
  };
  static Lane[] vLanes = {
      new Lane(2,1),
      new Lane(2,2),
      new Lane(2,3),
      new Lane(2,4),
      new Lane(2,5),
      new Lane(2,6),
      new Lane(2,7),
      new Lane(2,8),
      new Lane(2,9)
  };
  static Lane[] squares = {
      new Lane(3,1),
      new Lane(3,2),
      new Lane(3,3),
      new Lane(3,4),
      new Lane(3,5),
      new Lane(3,6),
      new Lane(3,7),
      new Lane(3,8),
      new Lane(3,9)
  };

  public static Sudoku generate() {
    Lane[] clonedH = hLanes.clone();
    Lane[] clonedV = vLanes.clone();
    Lane[] clonedS = squares.clone();

    Set<Cell> cells = new HashSet<>(81);
    for(byte x=1;x<=9;x++){
      for(byte y=1;y<=9;y++) {
        Cell cell = generateCell(x,y,clonedH,clonedV,clonedS);
        cells.add(cell);
      }
    }
    Sudoku sudoku = new Sudoku(cells,clonedH,clonedV,clonedS);
    return sudoku;
  }

  public static Cell generateCell(byte x, byte y,Lane[] hLanes, Lane[] vLanes, Lane[] squares){
    byte vlaneIndex = (byte) (x - 1);
    Lane vLane = vLanes[vlaneIndex];
    byte hlaneIndex = (byte) (y - 1);
    Lane hLane = hLanes[hlaneIndex];
    byte squareIndex = (byte) (3*(hlaneIndex/3) + vlaneIndex/3);
    Lane square = squares[squareIndex];
    Cell cell = new Cell(x, y);
    vLane.add(cell);
    hLane.add(cell);
    square.add(cell);
    cell.setLanes(vLane, hLane, square);
    return cell;
  }
}
