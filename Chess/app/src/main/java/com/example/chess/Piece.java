package com.example.chess;

import java.util.*;
import java.io.*;

/**
 * @author Lance Luo
 * @author Patrick Lee
 * Abstract class to represent a chess piece.
 */
public abstract class Piece {
    /**
     * Character to track whether this piece is white or black.
     */
    public char color;
    /**
     * Character to track whether this piece is a bishop, king, knight, pawn, queen, or rook.
     */
    public char type;

    /**
     * Constructor for a chess piece.
     * @param color Sets the piece as white or black.
     * @param type Sets the piece as a bishop, king, knight, pawn, queen, or rook.
     */
    Piece (char color, char type) {
        this.color = color;
        this.type = type;
    }

    /**
     * Abstract method to determine whether a move is valid for this piece.
     * @param board 2D array that represents the chessboard.
     * @param startCol Column where the piece starts.
     * @param startRow Row where the piece starts.
     * @param endCol Column where the piece ends.
     * @param endRow Row where the piece ends.
     * @param turn Odd number means white's turn, even number means black's turn.
     * @return Returns true if the move is valid, false if the move is invalid.
     */
    public abstract boolean validMove(Piece[][] board, int startCol, int startRow, int endCol, int endRow, int turn);

}