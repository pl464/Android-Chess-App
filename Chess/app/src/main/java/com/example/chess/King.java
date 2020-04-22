package com.example.chess;

import java.util.*;
import java.io.*;

/**
 * @author Lance Luo
 * @author Patrick Lee
 * Class to represent a king piece.
 */
public class King extends Piece {
    /**
     * Boolean to track whether this king has been moved yet.
     */
    public boolean canCastle = true;

    /**
     * Constructor for a king piece.
     * @param color Sets the piece as white or black.
     */
    King (char color) {
        super(color, 'K');
    }

    /**
     * Method to determine whether a move is valid for a king.
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
        int rookCol = (endCol > startCol) ? 7 : 0;
        int rookRow = (color == 'w') ? 7 : 0;
        int xDir = (xDist < 0) ? -1 : 1;
        Piece corner = board[rookRow][rookCol];
        if (xDist == 0 && yDist == 0) {
            return false;
        }
        if (Math.abs(xDist) <= 1 && Math.abs(yDist) <= 1) {
            return true;
        }
        //castling
        if (canCastle && Math.abs(xDist) == 2 && Math.abs(yDist) == 0) {
            if (MainActivity.isCheck(this.color)) {
                return false;
            }
            if (corner != null && corner.type == 'R' && ((Rook) corner).canCastle) {
                for (int i = 1; i < Math.abs(rookCol - startCol); i++) {
                    if (board[startRow][startCol + (xDir * i)] != null) {
                        return false;
                    }
                    else {
                        board[startRow][startCol + (xDir * i)] = this;
                        board[startRow][startCol] = null;
                        if (MainActivity.isCheck(this.color)) {
                            board[startRow][startCol] = this;
                            board[startRow][startCol + (xDir * i)] = null;
                            return false;
                        }
                        board[startRow][startCol] = this;
                        board[startRow][startCol + (xDir * i)] = null;
                    }
                }
                return true;
            }
        }
        return false;
    }
}