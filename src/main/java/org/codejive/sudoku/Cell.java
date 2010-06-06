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

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public class Cell {

    private int x, y;
    private EnumSet<CellState> possibleStates;
    private Set<CellState> possibleStatesReadOnly;

    public Cell(int _x, int _y) {
        x = _x;
        y = _y;
        possibleStates = EnumSet.allOf(CellState.class);
        possibleStatesReadOnly = Collections.unmodifiableSet(possibleStates);
    }

    public Cell(Cell _cell) {
        x = _cell.x;
        y = _cell.y;
        possibleStates = EnumSet.copyOf(_cell.possibleStates);
        possibleStatesReadOnly = Collections.unmodifiableSet(possibleStates);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPossibleStatesCount() {
        return possibleStates.size();
    }

    public Set<CellState> getPossibleStates() {
        return possibleStatesReadOnly;
    }

    public boolean addState(CellState _state) {
        return possibleStates.add(_state);
    }

    public boolean removeState(CellState _state) throws IllegalMove {
        if (possibleStates.size() == 1 && possibleStates.contains(_state)) {
            throw new IllegalMove("Cannot remove last remaining state " + _state + " for " + this);
        }
        return possibleStates.remove(_state);
    }

    public CellState getState() {
        CellState state;
        if (getPossibleStatesCount() == 1) {
            state = (CellState) (getPossibleStates().toArray()[0]);
        } else {
            state = null;
        }
        return state;
    }

    public boolean setState(CellState _state) throws IllegalMove {
        boolean changed = false;
        if (possibleStates.contains(_state)) {
            if (possibleStates.size() > 1) {
                possibleStates.clear();
                possibleStates.add(_state);
                System.out.println("Setting " + this + " = " + getState());
                changed = true;
            }
        } else {
            throw new IllegalMove("State " + _state + " is not possible for cell " + this);
        }
        return changed;
    }

    @Override
    public String toString() {
        return "Cell<" + (x + 1) + "," + (y + 1) + ">";
    }
}
