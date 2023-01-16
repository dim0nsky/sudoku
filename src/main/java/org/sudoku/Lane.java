package org.sudoku;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Lane {

  private Cell[] cells = new Cell[9];
  private int position;
  private int type;
  private byte curIndex = 0;

  public Lane(final int type, int position) {
    this.position = position;
    this.type = type;
  }

  public Set<Cell> collectChangedCells(Byte value) {
    Set<Cell> result = new HashSet<>();
    for (Cell cell : cells) {
      if (cell.removePossibleNumber(value))
        result.add(cell);
    }
    return result;
  }

  public void add(Cell cell) {
    cells[curIndex++] = cell;
  }

  public Cell get(byte index) {
    return cells[index];
  }

  public Set<Cell> checkSingleValue() {
    Map<Byte, Set<Cell>> numbersToCells = new HashMap<>(9);
    for (Cell cell : cells) {
      Set<Byte> possibleNumbers = cell.getPossibleNumbers();
      for (Byte number : possibleNumbers) {
        Set<Cell> cellsForNumber = numbersToCells.getOrDefault(number, new HashSet<>());
        cellsForNumber.add(cell);
        numbersToCells.put(number, cellsForNumber);
      }
    }
    Set<Cell> results = new HashSet<>();
    for (Entry<Byte, Set<Cell>> numberEntry : numbersToCells.entrySet()) {
      Set<Cell> cellsForNumber = numberEntry.getValue();
      if (cellsForNumber.size() == 1) {
        Byte number = numberEntry.getKey();
        Set<Cell> cells = cellsForNumber.stream().findFirst().get().setNumberValue(number);
        results.addAll(cells);
      }
    }
    return results;
  }

  public Set<Cell> checkCellsWithMatchingNumbers() {
    Map<Set<Byte>, Set<Cell>> numbersToCells = new HashMap<>(9);
    for (Cell cell : cells) {
      Set<Byte> possibleNumbers = cell.getPossibleNumbers();
      Set<Cell> cellsForNumber = numbersToCells.get(possibleNumbers);
      if (cellsForNumber == null) {
        cellsForNumber = new HashSet<>();
      }
      cellsForNumber.add(cell);
      numbersToCells.put(possibleNumbers, cellsForNumber);
    }
    Set<Cell> results = new HashSet<>();
    for (Entry<Set<Byte>, Set<Cell>> numberEntry : numbersToCells.entrySet()) {
      Set<Cell> cellsForNumbers = numberEntry.getValue();
      Set<Byte> numbers = numberEntry.getKey();
      if (cellsForNumbers.size() == numbers.size()) {
        Set<Cell> rowcells = getCells();
        rowcells.removeAll(cellsForNumbers);

        for(Byte num : numbers) {
          for (Cell cell : rowcells) {
            if (cell.removePossibleNumber(num))
              results.add(cell);
          }
        }
      }
    }
    return results;
  }

  public String toString() {
    StringBuilder sb2 = new StringBuilder();
    for (Cell cell : cells) {
      sb2.append("|").append(cell.toString());
    }
    sb2.append("|");
    return sb2.toString();

  }

  public Set<Cell> intersect(Lane lane) {
    Set<Cell> otherLaneCells = new HashSet<>(Arrays.asList(lane.cells));
    Set<Cell> laneCells = new HashSet<>(Arrays.asList(cells));
    Set<Cell> result = new HashSet<>();
    for (Cell cell : otherLaneCells) {
      if (laneCells.contains(cell)) {
        result.add(cell);
      }
    }
    return result;
  }

  public Set<Cell> getCells() {
    return new HashSet<>(Arrays.asList(cells));
  }

  public Set<Cell> substract(Lane lane) {
    Set<Cell> laneCells = new HashSet<>(Arrays.asList(cells));
    Set<Cell> otherLaneCells = new HashSet<>(Arrays.asList(lane.cells));
    laneCells.removeAll(otherLaneCells);
    return laneCells;
  }
}
