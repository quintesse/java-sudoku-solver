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
	private Board board;
	private int x, y;
	private EnumSet<CellState> possibleStates;
	private Set<CellState> possibleStatesReadOnly;
	
	public Cell(Board _board, int _x, int _y) {
		board = _board;
		x = _x;
		y = _y;
		possibleStates = EnumSet.allOf(CellState.class);
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

	protected void addState(CellState _state) {
		possibleStates.add(_state);
	}

	protected void removeState(CellState _state) {
		if (possibleStates.remove(_state)) {
			if (getPossibleStatesCount() == 1) {
				System.out.println("Only possiblity left for " + this + " = " + getState());
				board.updateRowColumnAndQuadrant(this);
			}
		}
	}

	public CellState getState() {
		CellState state;
		if (getPossibleStatesCount() == 1) {
			state = (CellState)(getPossibleStates().toArray()[0]);
		} else {
			state = null;
		}
		return state;
	}

	public void setState(CellState _state) throws IllegalMove {
		if (possibleStates.contains(_state)) {
			possibleStates.clear();
			possibleStates.add(_state);
			System.out.println("Setting " + this + " = " + getState());
			board.updateRowColumnAndQuadrant(this);
		} else {
			throw new IllegalMove("State " + _state + " is not possible for cell " + this);
		}
	}

	@Override
	public String toString() {
		return "Cell<" + (x + 1) + "," + (y + 1) + ">";
	}
}

/*
 * $Log:	$
 */