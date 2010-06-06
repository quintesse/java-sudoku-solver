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
        /* Nr 2 */
        String[] boardDef1 = {
        "7   2 8 5",
        "  8 6  39",
        "4 9      ",
        "1 32     ",
        "8 23915 4",
        "     53 1",
        "      1 8",
        "38  1 4  ",
        "9 6 7   3"
        };
        /* Nr 201 */
        String[] boardDef2 = {
        "  19573  ",
        " 3     1 ",
        "2       8",
        "9  7 4  5",
        "1       6",
        "3 8   1 2",
        "4  265  3",
        " 9     5 ",
        "  31892  "
        };
        /* Some medium lvl */
        String[] boardDef3 = {
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

        try {
            Board board = readBoard(boardDef3);

            System.out.println("Original state:");
            System.out.println(boardStateString(board));

            board.preSolve();
            System.out.println("Pre-solve state:");
            System.out.println(boardStateString(board));

            solve(board);
        } catch (IllegalMove ex) {
            System.err.println("Could not read board definition");
        }
    }

    public static Board readBoard(String[] boardDef) throws IllegalMove {
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
                    board.getCell(x, y).setState(state);
                }
            }
        }
        return board;
    }

    public static String boardStateString(Board _board) {
        StringBuilder res = new StringBuilder();
        res.append("+---------+---------+---------+\n");
        for (int y = 0; y < 9; y++) {
            res.append("|");
            for (int x = 0; x < 9; x++) {
                Cell c = _board.getCell(x, y);
                CellState state = c.getState();
                if (state != null) {
                    res.append(" ");
                    res.append(state.ordinal() + 1);
                    res.append(" ");
                } else {
                    res.append("   ");
                }
                if (x % 3 == 2) {
                    res.append("|");
                }
            }
            res.append("\n");
            if (y % 3 == 2) {
                res.append("+---------+---------+---------+\n");
            }
        }
        return res.toString();
    }

    public static String boardPossibilitiesString(Board _board) {
        StringBuilder res = new StringBuilder();
        res.append("+---------+---------+---------+\n");
        for (int y = 0; y < 9; y++) {
            res.append("|");
            for (int x = 0; x < 9; x++) {
                Cell c = _board.getCell(x, y);
                res.append(" ");
                res.append(c.getPossibleStatesCount());
                res.append(" ");
                if (x % 3 == 2) {
                    res.append("|");
                }
            }
            res.append("\n");
            if (y % 3 == 2) {
                res.append("+---------+---------+---------+\n");
            }
        }
        return res.toString();
    }

    public static void solve(Board _board) {
        solve(_board, 0, 0);
    }

    private static boolean solve(Board _board, int _startX, int _startY) {
        for (int y = _startY; y < 9; y++) {
            for (int x = _startX; x < 9; x++) {
                Cell c = _board.getCell(x, y);
                if (c.getPossibleStatesCount() > 1) {
                    // Loop over the possible values
                    for (CellState state : c.getPossibleStates()) {
                        Board b = new Board(_board);
                        try {
                            b.setCellState(x, y, state);
                            if (!b.isSolved()) {
                                if (x < 8) {
                                    if (solve(b, x + 1, y)) {
                                        return true;
                                    }
                                } else if (y < 8) {
                                    if (solve(b, 0, y + 1)) {
                                        return true;
                                    }
                                }
                            } else {
                                System.out.println("Solution found:");
                                System.out.println(boardStateString(b));
                                return true;
                            }
                        } catch (IllegalMove e) {
                            // Should never happen here
                            System.err.println("Illegal move: " + e);
                        }
                    }
                }
            }
        }
        return false;
    }
}
