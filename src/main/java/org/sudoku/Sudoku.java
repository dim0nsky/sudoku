package org.sudoku;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Sudoku {

  private Set<Cell> cells = new TreeSet<>();
  private Lane[] hlanes;
  private Lane[] squares;
  private Lane[] vlanes;
  private Set<Lane> lanes;
  private Map<Lane, Set<Lane>> squaresToLanes = new HashMap<>(9);

  public Sudoku(Set<Cell> cells, Lane[] hlanes, Lane[] vlanes, Lane[] squares) {
    this.cells = cells;
    this.hlanes = hlanes;
    this.vlanes = vlanes;
    this.squares = squares;
    this.lanes = Arrays.stream(hlanes).collect(Collectors.toSet());
    lanes.addAll(Arrays.stream(vlanes).collect(Collectors.toSet()));
    lanes.addAll(Arrays.stream(squares).collect(Collectors.toSet()));
    for(Lane square: squares){
      HashSet<Lane> lanesMapping = new HashSet<>();
      squaresToLanes.put(square, lanesMapping);
      for(Cell cell : square.getCells()){
        Set<Lane> cellLanes = cell.getLanes();
        cellLanes.remove(square);
        lanesMapping.addAll(cellLanes);
      }
    }
  }

  public void setCellValue(int x, int y, int value) {
    Set<Cell> cells = hlanes[y - 1].get((byte) (x-1)).set((byte) value);
    evaluate(cells);
  }

  public void evaluate(Set<Cell> cells) {
    if(cells==null || cells.isEmpty()) return;
    this.cells.removeAll(cells);
    this.cells.addAll(cells);
  }

  public void solve() {
    boolean result = true;
    while (result) {
      result = false;
      Cell firstCell = cells.stream().findFirst().get();

      while (firstCell != null && firstCell.size() == 1) {
        result=true;
        Byte value = firstCell.getPossibleNumbers().stream().findFirst().get();
        Set<Cell> cells = firstCell.set(value);
        evaluate(cells);
        firstCell = this.cells.stream().findFirst().get();
      }

      if(result) continue;

      for (Lane lane : lanes) {
        Set<Cell> cells = lane.checkSingleValue();
        evaluate(cells);
        result = result || !cells.isEmpty();
      }

      if(result) continue;

      for (Lane lane : lanes) {
        Set<Cell> cells = lane.checkCellsWithMatchingNumbers();
        evaluate(cells);
        result = result || !cells.isEmpty();
      }

      if(result) continue;

      Set<Cell> cells = new HashSet<>();
      for(Entry<Lane, Set<Lane>> squareMap : squaresToLanes.entrySet()){
        Lane square = squareMap.getKey();
        for(Lane lane: squareMap.getValue()) {
          Set<Cell> intersect = square.intersect(lane);
          Set<Cell> substractLaneFromSquare = square.substract(lane);
          Set<Cell> substractSquareFromLane = lane.substract(square);

          Set<Byte> intersectNumbers = new HashSet<>();
          Set<Byte> substractLaneFromSquareNumbers = new HashSet<>();
          Set<Byte> substractSquareFromLaneNumbers = new HashSet<>();

          for(Cell cell : intersect) {
            Set<Byte> possibleNumbers = cell.getPossibleNumbers();
            intersectNumbers.addAll(possibleNumbers);
          }

          for(Cell cell : substractLaneFromSquare) {
            Set<Byte> possibleNumbers = cell.getPossibleNumbers();
            substractLaneFromSquareNumbers.addAll(possibleNumbers);
          }

          for(Cell cell : substractSquareFromLane) {
            Set<Byte> possibleNumbers = cell.getPossibleNumbers();
            substractSquareFromLaneNumbers.addAll(possibleNumbers);
          }

          HashSet<Byte> cloneIntersect = new HashSet<>(intersectNumbers);
          cloneIntersect.removeAll(substractLaneFromSquareNumbers);
          if(!cloneIntersect.isEmpty()){
            clean(cells, substractSquareFromLane, cloneIntersect);
          }
          intersectNumbers.removeAll(substractSquareFromLaneNumbers);
          if(!intersectNumbers.isEmpty()) {
            clean(cells, substractLaneFromSquare, intersectNumbers);
          }
        }
      }
      evaluate(cells);
      result = result || !cells.isEmpty();
    }
  }

  private void clean(Set<Cell> aggregator, Set<Cell> cells, Set<Byte> numbers) {
    for(Cell cell: cells) {
      for (Byte number : numbers) {
        aggregator.addAll(cell.remove(number));
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
    for(int i=0;i<79+10;i++) {
      sb.append("-");
    }
    sb.append("|");
    return sb.toString();
  }

  public void evaluateRule1(){

  }
}
