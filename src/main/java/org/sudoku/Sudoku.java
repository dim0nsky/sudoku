package org.sudoku;

import java.util.*;
import java.util.Map.Entry;

public class Sudoku {

  private final Set<Cell> cells = new TreeSet<>();
  private final Lane[] hlanes;
  private final Set<Lane> lanes = new HashSet<>(27);
  private final Map<Lane, Set<Lane>> squaresToLanes = new HashMap<>(9);

  public Sudoku(Set<Cell> cells, Lane[] hlanes, Lane[] vlanes, Lane[] squares) {
    this.cells.addAll(cells);
    this.hlanes = hlanes;
    this.lanes.addAll(Arrays.asList(hlanes));
    this.lanes.addAll(Arrays.asList(vlanes));
    this.lanes.addAll(Arrays.asList(squares));
    for(Lane square: squares){
      HashSet<Lane> lanesMapping = new HashSet<>();
      squaresToLanes.put(square, lanesMapping);
      for(Cell cell : square.getCells()){
        Set<Lane> cellLanes = cell.getBelongToLanes();
        cellLanes.remove(square);
        lanesMapping.addAll(cellLanes);
      }
    }
  }

  public void setCellValue(int x, int y, int value) {
    Set<Cell> cells = hlanes[y - 1].get((byte) (x-1)).setNumberValue((byte) value);
    reorderCells(cells);
  }

  public void reorderCells(Set<Cell> cells) {
    if (cells==null || cells.isEmpty()) return;
    this.cells.removeAll(cells);
    this.cells.addAll(cells);
  }

  public void solve() {
    boolean result = true;
    while (result) {
      result = false;
      checkCellsWithSinglePossibleNumber();
      for (Lane lane : lanes) {
        Set<Cell> cells = lane.checkSingleValue();
        reorderCells(cells);
        result = result || !cells.isEmpty();
      }

      if(result) continue;

      for (Lane lane : lanes) {
        Set<Cell> cells = lane.checkCellsWithMatchingNumbers();
        reorderCells(cells);
        result = result || !cells.isEmpty();
      }

      if(result) continue;

      Set<Cell> changedCells = new HashSet<>();
      for(Entry<Lane, Set<Lane>> squareMap : squaresToLanes.entrySet()){
        Lane square = squareMap.getKey();
        for(Lane lane: squareMap.getValue()) {
          Set<Cell> intersectionCells = square.intersect(lane);
          Set<Cell> substractionLaneFromSquareCells = square.substract(lane);
          Set<Cell> substractionSquareFromLaneCells = lane.substract(square);

          Set<Byte> intersectionNumbers = new HashSet<>();
          Set<Byte> substractionLaneFromSquareNumbers = new HashSet<>();
          Set<Byte> substractionSquareFromLaneNumbers = new HashSet<>();

          for(Cell cell : intersectionCells) {
            Set<Byte> possibleNumbers = cell.getPossibleNumbers();
            intersectionNumbers.addAll(possibleNumbers);
          }

          for(Cell cell : substractionLaneFromSquareCells) {
            Set<Byte> possibleNumbers = cell.getPossibleNumbers();
            substractionLaneFromSquareNumbers.addAll(possibleNumbers);
          }

          for(Cell cell : substractionSquareFromLaneCells) {
            Set<Byte> possibleNumbers = cell.getPossibleNumbers();
            substractionSquareFromLaneNumbers.addAll(possibleNumbers);
          }

          HashSet<Byte> clonedIntersectionNumbers = new HashSet<>(intersectionNumbers);
          clonedIntersectionNumbers.removeAll(substractionLaneFromSquareNumbers);
          if(!clonedIntersectionNumbers.isEmpty()){
            removeNumbersFromCells(changedCells, substractionSquareFromLaneCells, clonedIntersectionNumbers);
          }
          intersectionNumbers.removeAll(substractionSquareFromLaneNumbers);
          if(!intersectionNumbers.isEmpty()) {
            removeNumbersFromCells(changedCells, substractionLaneFromSquareCells, intersectionNumbers);
          }
        }
      }
      reorderCells(changedCells);
      result = !changedCells.isEmpty();
    }
  }

  private void checkCellsWithSinglePossibleNumber() {
    Iterator<Cell> cellIterator = cells.stream().filter(cell -> cell.possibleNumbersSize() == 1).iterator();

    while (cellIterator.hasNext()) {
      Cell firstCell = cellIterator.next();
      Byte value = firstCell.getPossibleNumbers().stream().findFirst().get();
      Set<Cell> updatedCells = firstCell.setNumberValue(value);
      reorderCells(updatedCells);
      cellIterator = cells.stream().filter(cell -> cell.possibleNumbersSize() == 1).iterator();
    }
  }

  private void removeNumbersFromCells(Set<Cell> changedCellsCollector, Set<Cell> cells, Set<Byte> numbers) {
    for(Cell cell: cells) {
      for (Byte number : numbers) {
        if (cell.removePossibleNumber(number))
          changedCellsCollector.add(cell);
      }
    }
  }

  public void print() {
    String sep = getSeparatorLane();
    System.out.println(sep);
    for(Lane lane: hlanes) {
      System.out.println(lane.toString());
      System.out.println(sep);
    }
  }

  private static String getSeparatorLane() {
    StringBuilder sb = new StringBuilder();
    sb.append("|");
    sb.append("-".repeat(79 + 10));
    sb.append("|");
    return sb.toString();
  }

}
