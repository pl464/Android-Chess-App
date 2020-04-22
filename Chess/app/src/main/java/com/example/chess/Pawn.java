package com.example.chess;

import java.util.*;
import java.io.*;

/**
 * @author Lance Luo
 * @author Patrick Lee
 * Class to represent a pawn piece.
 */
public class Pawn extends Piece {
    /**
     * Integer to track the turn this pawn has advanced two squares.
     */
    public int passant = -1;

    /**
     * Constructor for a pawn piece.
     * @param color Sets the piece as white or black.
     */
    Pawn (char color) {
        super(color, 'p');
    }

    /**
     * Method to determine whether a move is valid for a pawn.
     * @param board 2D array that represents the chessboard.
     * @param startCol Column where the piece starts.
     * @param startRow Row where the piece starts.
     * @param endCol Column where the piece ends.
     * @param endRow Row where the piece ends.
     * @param turn Odd number means white's turn, even number means black's turn.
     * @return Returns true if the move is valid, false if the move is invalid.
     */
    public boolean validMove(Piece[][] board, int startCol, int startRow, int endCol, int endRow, int turn) {
        int xDist = endCol - startCol;
        int yDist = endRow - startRow;
        int colorRow = (color == 'w') ? 6 : 1;
        int colorDir = (color == 'w') ? -1 : 1;
        Piece end = board[endRow][endCol];
        Piece passing = board[startRow][endCol];
        if (xDist == 0 && end == null) {
            //normal move
            if (yDist == colorDir) {
                return true;
            }
            //initial move
            else if (yDist == (colorDir * 2) && startRow == colorRow && board[startRow + colorDir][startCol] == null) {
                return true;
            }
        }
        else if (Math.abs(xDist) == 1 && yDist == colorDir) {
            //normal capture
            if (end != null) {
                return true;
            }
            //en passant
            else if (passing != null && passing.color != color && passing.type == 'p' && ((Pawn) board[startRow][endCol]).passant == (turn - 1)) {
                board[startRow][startCol] = null;
                board[startRow][endCol] = null;
                board[endRow][endCol] = this;
                if (MainActivity.isCheck(this.color)) {
                    board[startRow][startCol] = this;
                    board[startRow][endCol] = passing;
                    board[endRow][endCol] = null;
                    return false;
                }
                board[startRow][startCol] = this;
                board[startRow][endCol] = passing;
                board[endRow][endCol] = null;
                return true;
            }
        }
        return false;
    }
}