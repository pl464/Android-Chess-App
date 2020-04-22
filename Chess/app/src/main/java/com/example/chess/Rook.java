package com.example.chess;

import java.util.*;
import java.io.*;

/**
 * @author Lance Luo
 * @author Patrick Lee
 * Class to represent a rook piece.
 */
public class Rook extends Piece {
    /**
     * Boolean to track whether this rook has been moved yet.
     */
    public boolean canCastle = true;

    /**
     * Constructor for a rook piece.
     * @param color Sets the piece as white or black.
     */
    Rook (char color) {
        super(color, 'R');
    }

    /**
     * Method to determine whether a move is valid for a rook.
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
        if (xDist == 0 ^ yDist == 0) {
            int xDir = (xDist < 0) ? -1 : (xDist > 0) ? 1 : 0;
            int yDir = (yDist < 0) ? -1 : (yDist > 0) ? 1 : 0;
            for (int i = 1; i < Math.max(Math.abs(xDist), Math.abs(yDist)); i++) {
                if (board[startRow + (yDir * i)][startCol + (xDir * i)] != null) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}