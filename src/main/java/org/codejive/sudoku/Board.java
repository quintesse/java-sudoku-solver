/*
 * [sudoku-solver] Sudoku solver
 * 
 * Copyright (C) 2005 Tako Schotanus
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * Created on Oct 4, 2005
 */
package org.codejive.sudoku;

public class Board {

    private Cell[][] cells;
    private long stateCount;

    public Board() {
        stateCount = 0;
        cells = new Cell[9][9];
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                Cell c = new Cell(x, y);
                cells[y][x] = c;
                stateCount += c.getPossibleStatesCount();
            }
        }
    }

    public Board(Board _board) {
        stateCount = 0;
        cells = new Cell[9][9];
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                Cell c = new Cell(_board.getCell(x, y));
                cells[y][x] = c;
                stateCount += c.getPossibleStatesCount();
            }
        }
    }

    public Cell getCell(int _x, int _y) {
        return cells[_y][_x];
    }

    public void setCellState(int _x, int _y, CellState _state) throws IllegalMove {
        Cell c = cells[_y][_x];
        int cnt = c.getPossibleStatesCount();
        if (c.setState(_state)) {
            stateCount -= (cnt - 1);
            updateRowColumnAndQuadrant(c);
        }
    }

    public void preSolve() throws IllegalMove {
        stateCount = 0;
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                Cell c = cells[y][x];
                stateCount += c.getPossibleStatesCount();
            }
        }
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                Cell c = cells[y][x];
                if (c.getPossibleStatesCount() == 1) {
                    updateRowColumnAndQuadrant(c);
                }
            }
        }
    }

    private void updateRowColumnAndQuadrant(Cell _cell) throws IllegalMove {
        CellState state = _cell.getState();
        // Update row
        for (int x = 0; x < 9; x++) {
            if (x != _cell.getX()) {
                Cell c = cells[_cell.getY()][x];
                removeCellState(c, state);
            }
        }
        // Update column
        for (int y = 0; y < 9; y++) {
            if (y != _cell.getY()) {
                Cell c = cells[y][_cell.getX()];
                removeCellState(c, state);
            }
        }
        // Update quadrant
        int x = _cell.getX() - (_cell.getX() % 3);
        int y = _cell.getY() - (_cell.getY() % 3);
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                if (((x + i) != _cell.getX()) && ((y + j) != _cell.getY())) {
                    Cell c = cells[y + j][x + i];
                    removeCellState(c, state);
                }
            }
        }
    }

    private void removeCellState(Cell _cell, CellState _state) throws IllegalMove {
        if (_cell.removeState(_state)) {
            stateCount--;
            if (_cell.getPossibleStatesCount() == 1) {
                System.out.println("Only possiblity left for " + _cell + " = " + _cell.getState());
                updateRowColumnAndQuadrant(_cell);
            }
        }
    }

    public boolean isSolved() {
        return (stateCount == 81);
    }
}
