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

public class Solver {

	public Solver() {
	}

	public static void main(String[] args) {
		/* Nr 2
		String[] boardDef = {
				"7   2 8 5",
				"  8 6  39",
				"4 9      ",
				"1 32     ",
				"8 23915 4",
				"     53 1",
				"      1 8",
				"38  1 4  ",
				"9 6 7   3"
		};*/
		/* Nr 201
		String[] boardDef = {
				"  19573  ",
				" 3     1 ",
				"2       8",
				"9  7 4  5",
				"1       6",
				"3 8   1 2",
				"4  265  3",
				" 9     5 ",
				"  31892  "
		}; */
		/* Some medium lvl */
		String[] boardDef = {
				"    9 78 ",
				" 1  8 25 ",
				"2        ",
				"     5 9 ",
				"   6    1",
				"5 3  2   ",
				"  73     ",
				"    189 4",
				"6        "
		};

		Board board = new Board();
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				char c = boardDef[y].charAt(x);
				CellState state = null;
				switch (c) {
					case '1' : state = CellState.one; break;
					case '2' : state = CellState.two; break;
					case '3' : state = CellState.three; break;
					case '4' : state = CellState.four; break;
					case '5' : state = CellState.five; break;
					case '6' : state = CellState.six; break;
					case '7' : state = CellState.seven; break;
					case '8' : state = CellState.eight; break;
					case '9' : state = CellState.nine; break;
				}
				if (state != null) {
					try {
						board.getCell(x, y).setState(state);
					} catch (IllegalMove e) {
						// Oh oh
						e.printStackTrace();
					}
				}
			}
		}
		
		System.out.println("State:");
		System.out.println(boardStateString(board));
		System.out.println(boardPossibilitiesString(board));
	}
	
	public static String boardStateString(Board _board) {
		StringBuilder res = new StringBuilder();
		res.append("+---------+\n");
		for (int y = 0; y < 9; y++) {
			res.append("|");
			for (int x = 0; x < 9; x++) {
				Cell c = _board.getCell(x, y);
				CellState state = c.getState();
				if (state != null) {
					res.append(state.ordinal() + 1);
				} else {
					res.append(" ");
				}
			}
			res.append("|\n");
		}
		res.append("+---------+");
		return res.toString();
	}
	
	public static String boardPossibilitiesString(Board _board) {
		StringBuilder res = new StringBuilder();
		res.append("+---------+\n");
		for (int y = 0; y < 9; y++) {
			res.append("|");
			for (int x = 0; x < 9; x++) {
				Cell c = _board.getCell(x, y);
				res.append(c.getPossibleStatesCount());
			}
			res.append("|\n");
		}
		res.append("+---------+");
		return res.toString();
	}
}


/*
 * $Log:	$
 */