package com.example.chess;

import java.util.*;
import java.io.*;

/**
 * @author Lance Luo
 * @author Patrick Lee
 * Class to represent a knight piece.
 */
public class Knight extends Piece {
    /**
     * Constructor for a knight piece.
     * @param color Sets the piece as white or black.
     */
    Knight (char color) {
        super(color, 'N');
    }

    /**
     * Method to determine whether a move is valid for a knight.
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
        if (Math.abs(xDist * yDist) == 2) {
            return true;
        }
        return false;
    }
}