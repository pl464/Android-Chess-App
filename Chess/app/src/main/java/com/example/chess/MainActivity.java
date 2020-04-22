package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    private static Piece[][] board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("me", "HELLO"); //go to logcat and search "me" in the Debug menu to find this message
        tableLayout = findViewById(R.id.board);

        board = new Piece[8][8];
        initialize();
        drawBoard();
    }

    private void drawBoard(){
        int tileWidth = Math.round(getResources().getDimension(R.dimen.tile_width));
        int tileHeight = Math.round(getResources().getDimension(R.dimen.tile_height));
        //int rowWidth = Math.round(getResources().getDimension(R.dimen.row_width));

        for (int i = 1; i <= 8; i++){
            TableRow tableRow = new TableRow(this);
            //tableRow.setLayoutParams(new TableRow.LayoutParams(rowWidth, tileHeight));
            tableRow.setId(i);
            tableLayout.addView(tableRow);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            for (int j = 1; j <= 8; j++){
                //NOTE: indices i and j start at 1 to generate indices 11, 12, 13, ..., 88 for tableLayout children (0 gets deleted at the beginning),
                //but the underlying board[][] uses indices starting at 0 and 0 because it uses a 2D array. So, 1 must be subtracted from these indices
                //to refer to the board in the line below.
                Piece p = board[i-1][j-1];
                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(new TableRow.LayoutParams(tileWidth, tileHeight));
                //if there is no Piece on the board
                if (p == null) {}
                //if the current Piece is black, see what type it is
                else if (p.color == 'b'){
                    if (p.type == 'B') imageView.setImageResource(R.drawable.ic_black_bishop);
                    else if (p.type == 'K') imageView.setImageResource(R.drawable.ic_black_king);
                    else if (p.type == 'N') imageView.setImageResource(R.drawable.ic_black_knight);
                    else if (p.type == 'p') imageView.setImageResource(R.drawable.ic_black_pawn);
                    else if (p.type == 'Q') imageView.setImageResource(R.drawable.ic_black_queen);
                    else if (p.type == 'R') imageView.setImageResource(R.drawable.ic_black_rook);
                }
                else if (p.color == 'w'){
                    if (p.type == 'B') imageView.setImageResource(R.drawable.ic_white_bishop);
                    else if (p.type == 'K') imageView.setImageResource(R.drawable.ic_white_king);
                    else if (p.type == 'N') imageView.setImageResource(R.drawable.ic_white_knight);
                    else if (p.type == 'p') imageView.setImageResource(R.drawable.ic_white_pawn);
                    else if (p.type == 'Q') imageView.setImageResource(R.drawable.ic_white_queen);
                    else if (p.type == 'R') imageView.setImageResource(R.drawable.ic_white_rook);
                }
                //add the ImageView to the current TableRow
                //TableRow tr = tableRow.findViewById(i);
                tableRow.addView(imageView);
                //for testing purposes
                imageView.setOnClickListener((v)->{
                    String id = Integer.toString(v.getId());
                    Log.d("me", "My ID is: " + id);
                });
                String id = Integer.toString(i) + Integer.toString(j);
                imageView.setId(Integer.parseInt(id));
            }

        }
    }

    public static void initialize() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == 0) {
                    if (j == 0 || j == 7) {
                        board[i][j] = new Rook('b');
                    }
                    else if (j == 1 || j == 6) {
                        board[i][j] = new Knight('b');
                    }
                    else if (j == 2 || j == 5) {
                        board[i][j] = new Bishop('b');
                    }
                    else if (j == 3) {
                        board[i][j] = new Queen('b');
                    }
                    else if (j == 4) {
                        board[i][j] = new King('b');
                    }
                }
                else if (i == 1) {
                    board[i][j] = new Pawn('b');
                }
                else if (i == 6) {
                    board[i][j] = new Pawn('w');
                }
                else if (i == 7) {
                    if (j == 0 || j == 7) {
                        board[i][j] = new Rook('w');
                    }
                    else if (j == 1 || j == 6) {
                        board[i][j] = new Knight('w');
                    }
                    else if (j == 2 || j == 5) {
                        board[i][j] = new Bishop('w');
                    }
                    else if (j == 3) {
                        board[i][j] = new Queen('w');
                    }
                    else if (j == 4) {
                        board[i][j] = new King('w');
                    }
                }
                else {
                    board[i][j] = null;
                }
            }
        }

    }

    /**
     * Method to determine whether a move is illegal.
     * @param s The move entered by the user in chess notation.
     * @param i Odd number means white's turn, even number means black's turn.
     * @return Returns true if the move is valid, false if the move is invalid.
     */
    public static boolean isIllegal(String s, int i) {
        //parse user input
        int startCol = s.toLowerCase().charAt(0) - 97;
        int startRow = 8 - Character.getNumericValue(s.charAt(1));
        int endCol = s.toLowerCase().charAt(3) - 97;
        int endRow = 8 - Character.getNumericValue(s.charAt(4));
        Piece start = board[startRow][startCol];
        Piece end = board[endRow][endCol];

        //check that starting piece exists
        if (start == null) {
            return true;
        }

        //check that starting piece is player's color
        char c = (i % 2 == 1) ? 'w' : 'b';
        if (start.color != c) {
            return true;
        }

        //check that ending piece, if exists, is opponent's color
        if (end != null && end.color == c) {
            return true;
        }

        //check that piece movement is legal
        if (start.validMove(board, startCol, startRow, endCol, endRow, i) == false) {
            return true;
        }

        //test to see if the move produces a check
        board[endRow][endCol] = start;
        board[startRow][startCol] = null;
        if (isCheck(c)) {
            board[startRow][startCol] = start;
            board[endRow][endCol] = end;
            return true;
        }
        board[startRow][startCol] = start;
        board[endRow][endCol] = end;
        return false;
    }

    /**
     * Method to make a move on the chessboard.
     * @param s The move entered by the user in chess notation.
     * @param i Odd number means white's turn, even number means black's turn.
     */
    public static void makeMove(String s, int i) {
        //parse user input
        int startCol = s.toLowerCase().charAt(0) - 97;
        int startRow = 8 - Character.getNumericValue(s.charAt(1));
        int endCol = s.toLowerCase().charAt(3) - 97;
        int endRow = 8 - Character.getNumericValue(s.charAt(4));
        Piece start = board[startRow][startCol];
        Piece end = board[endRow][endCol];

        //move the piece
        board[endRow][endCol] = start;
        board[startRow][startCol] = null;

        //castling
        if (start.type == 'R') {
            ((Rook) start).canCastle = false;
        }
        if (start.type == 'K') {
            if (Math.abs(endCol - startCol) == 2) {
                int rookCol = (endCol > startCol) ? 7 : 0;
                int rookRow = (start.color == 'w') ? 7 : 0;
                int newCol = (endCol > startCol) ? 5 : 3;
                board[rookRow][newCol] = board[rookRow][rookCol];
                board[rookRow][rookCol] = null;
                ((King) start).canCastle = false;
                ((Rook) board[rookRow][newCol]).canCastle = false;
            }
            else {
                ((King) start).canCastle = false;
            }
        }

        //en passant
        if (start.type == 'p') {
            if (Math.abs(endRow - startRow) == 2) {
                ((Pawn) start).passant = i;
            }
            if (endCol != startCol && end == null) {
                board[startRow][endCol] = null;
            }

        }

        //pawn promotion
        int colorRow = (start.color == 'w') ? 0 : 7;
        if (start.type == 'p' && endRow == colorRow) {
            if (s.length() > 6) {
                switch (s.charAt(6)) {
                    case 'R':
                        board[endRow][endCol] = new Rook(start.color);
                        break;
                    case 'N':
                        board[endRow][endCol] = new Knight(start.color);
                        break;
                    case 'B':
                        board[endRow][endCol] = new Bishop(start.color);
                        break;
                    case 'Q':
                    default:
                        board[endRow][endCol] = new Queen(start.color);
                        break;
                }
            }
            else {
                board[endRow][endCol] = new Queen(start.color);
            }
        }
        return;
    }

    /**
     * Method to determine whether the specified king is in checkmate.
     * @param color The color of the king under consideration.
     * @return Returns true if the king is checkmated, false otherwise.
     */
    public static boolean isCheckmate(char color) {
        //test possible moves to see if they end the check; if none, checkmate detected
        for (int j = 0; j < 8; j++) {
            for (int k = 0; k < 8; k++) {
                Piece p0 = board[j][k]; //save the original piece before moving it
                if (p0 == null || p0.color != color) {
                    continue;
                }
                ArrayList<int[]> possMoves = getPossMoves(p0, j, k);
                //for every possible move, try the move
                for (int[] possMove : possMoves) {
                    int testY = possMove[0];
                    int testX = possMove[1];
                    Piece p1 = board[testY][testX]; //save the destination piece
                    board[testY][testX] = p0;
                    board[j][k] = null;
                    if (!isCheck(color)) {
                        board[j][k] = p0;
                        board[testY][testX] = p1;
                        return false;
                    }
                    else {
                        board[j][k] = p0;
                        board[testY][testX] = p1;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Method to determine whether the specified king is in check.
     * @param color The color of the king under consideration.
     * @return Returns true if the king is in check, false otherwise.
     */
    public static boolean isCheck(char color) {
        //get king's position as starting point
        int yPos = getKingPos(color)[0];
        int xPos = getKingPos(color)[1];

        //search diagonally for enemy bishops and queen
        String[] diagDirections = {"NW", "NE", "SE", "SW"};
        for (int i = 0; i < 4; i++) {
            int[] position = searchInDirection(yPos, xPos, diagDirections[i], 7);
            Piece p = board[position[0]][position[1]];
            if (p != null && (p.type == 'B' || p.type == 'Q') && p.color != color) {
                return true;
            }
        }
        //search horizontally/vertically for enemy rooks and queen
        String[] cardDirections = {"N", "E", "S", "W"};
        for (int i = 0; i < 4; i++) {
            int[] position = searchInDirection(yPos, xPos, cardDirections[i], 7);
            Piece p = board[position[0]][position[1]];
            if (p != null && (p.type == 'R' || p.type == 'Q') && p.color != color) {
                return true;
            }
        }
        //search for checking knights
        int[] arr = {1, 2, -1, -2}; //array to get all tiles where checking knight can be
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) { //try each combo of 1 and 2
                int y = yPos + arr[i]; int x = xPos + arr[j]; //add 1 or 2 to y and x
                if ((arr[i] + arr[j]) % 2 == 0)
                    continue; //don't try combos that aren't a 1 and 2
                if (!(0 <= y && y <= 7 && 0 <= x && x <= 7))
                    continue; //checks if tile is on the board
                if (board[y][x] == null)
                    continue;
                if (board[y][x].type == 'N' && board[y][x].color != color)
                    return true;
            }
        }
        //search for checking kings
        String[] allDirections = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
        for (int i = 0; i < 8; i++) {
            int[] position = searchInDirection(yPos, xPos, allDirections[i], 1);
            Piece p = board[position[0]][position[1]];
            if (p != null && (p.type == 'K') && p.color != color) {
                return true;
            }
        }
        //search for checking pawns
        //reminder: diagDirections = {"NW", "NE", "SE", "SW"};
        int a = 0; int b = 1;
        if (color == 'b') { a = 2; b = 3;}
        for (int i = a; i <= b; i++) {
            int[] position = searchInDirection(yPos, xPos, diagDirections[i], 1);
            Piece p = board[position[0]][position[1]];

            if (p != null && (p.type == 'p') && p.color != color) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns an integer array of length 2 with the position of the King of the specified color.
     * The first entry in the array holds the row index (0 for the first row and 7 for the last),
     * and the second holds the column index (0 for the first column, 7 for the last).
     *
     * @param color The color of the king under consideration.
     * @return Returns an integer array of 2 integers which store the row and column index of the King,
     * respectively, to be used in accessing the King in the 2D-array "board".
     */
    public static int[] getKingPos(char color) {
        int[] kingPos = {-1, -1};
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null &&
                        board[i][j].color == color && board[i][j].type == 'K') {
                    kingPos[0] = i;
                    kingPos[1] = j;
                    return kingPos;
                }
            }
        }
        return kingPos;
    }

    /**
     * Given a starting tile on the "board" and a direction, returns an integer array with the position
     * of the first Piece found. If no piece is found, then the last position checked on the board is returned.
     *
     * @param yPos The row index (0-7) of the starting tile.
     * @param xPos The column index (0-7) of the starting tile.
     * @param direction One of the 8 directions ("NE", "E", "SE", ...) that will be searched for a Piece.
     * @param dist The maximum distance that will be searched in the direction.
     * @return Returns an integer array storing the row and column index and the first Piece found, or
     * the last board position checked (if no Piece is found).
     */
    public static int[] searchInDirection(int yPos, int xPos, String direction, int dist) {
        switch (direction) {
            case "NE":
                while (yPos > 0 && xPos < 7 && dist > 0) {
                    yPos--; xPos++; dist--;
                    if (board[yPos][xPos] != null) break;
                } break;
            case "SE":
                while (yPos < 7 && xPos < 7 && dist > 0) {
                    yPos++; xPos++; dist--;
                    if (board[yPos][xPos] != null) break;
                } break;
            case "SW":
                while (yPos < 7 && xPos > 0 && dist > 0) {
                    yPos++; xPos--; dist--;
                    if (board[yPos][xPos] != null) break;
                } break;
            case "NW":
                while (yPos > 0 && xPos > 0 && dist > 0) {
                    yPos--; xPos--; dist--;
                    if (board[yPos][xPos] != null) break;
                } break;
            case "N":
                while (yPos > 0 && dist > 0) {
                    yPos--; dist--;
                    if (board[yPos][xPos] != null) break;
                } break;
            case "E":
                while (xPos < 7 && dist > 0) {
                    xPos++; dist--;
                    if (board[yPos][xPos] != null) break;
                } break;
            case "S":
                while (yPos < 7 && dist > 0) {
                    yPos++; dist--;
                    if (board[yPos][xPos] != null) break;
                } break;
            case "W":
                while (xPos > 0 && dist > 0) {
                    xPos--; dist--;
                    if (board[yPos][xPos] != null) break;
                } break;
        }
        return new int[] {yPos, xPos};
    }

    /**
     * Given a piece and its location, returns a list of possible moves, stored as integer arrays.
     * The 2 entries in each array hold the row and column index, respectively, of the possible positions
     * which the Piece may move to. This method does not check if the possible moves result in a check.
     *
     * @param p The Piece to be checked for possible moves.
     * @param yPos The row index (0-7) of the starting tile of p.
     * @param xPos The column index (0-7) of the starting tile of p.
     * @return Returns an ArrayList of integer arrays.
     */
    public static ArrayList<int[]> getPossMoves(Piece p, int yPos, int xPos){
        ArrayList<int[]> possMoves = new ArrayList<int[]>();
        switch (p.type) {
            case 'B':
                String[] B_Directions = {"NW", "NE", "SE", "SW"};
                int[][] B_Moves = {{-1, -1}, {-1, 1}, {1, 1}, {1, -1}};
                for (int i = 0; i < 4; i++) {

                    int[] position = searchInDirection(yPos, xPos, B_Directions[i], 7);
                    int tilesMoved = Math.abs(position[1] - xPos);
                    //if you found a friendly piece, don't count its location as a possible move
                    if (board[position[0]][position[1]] != null &&
                            board[position[0]][position[1]].color == p.color) {
                        tilesMoved--;
                    }
                    int y = yPos; int x = xPos;
                    for (int j = 0; j < tilesMoved; j++) {
                        y += B_Moves[i][0];
                        x += B_Moves[i][1];
                        possMoves.add(new int[] {y, x});
                    }
                }
                break;

            case 'R':
                String[] R_Directions = {"N", "E", "S", "W"};
                int[][] R_Moves = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
                for (int i = 0; i < 4; i++) {
                    int[] position = searchInDirection(yPos, xPos, R_Directions[i], 7);
                    int tilesMoved = Math.abs((position[0] - yPos) + (position[1] - xPos));

                    if (board[position[0]][position[1]] != null &&
                            board[position[0]][position[1]].color == p.color) tilesMoved--;
                    //System.out.println("In direction " + R_Directions[i] + ", moved " + tilesMoved + " times");
                    int y = yPos; int x = xPos;
                    for (int j = 0; j < tilesMoved; j++) {
                        y += R_Moves[i][0];
                        x += R_Moves[i][1];
                        possMoves.add(new int[] {y, x});
                    }
                }
                break;

            case 'Q':
                String[] Q_Directions = {"NW", "NE", "SE", "SW", "N", "E", "S", "W"};
                int[][] Q_Moves = {{-1, -1}, {-1, 1}, {1, 1}, {1, -1}, {-1, 0}, {0, 1}, {1, 0}, {0, -1}};
                for (int i = 0; i < 8; i++) {

                    int[] position = searchInDirection(yPos, xPos, Q_Directions[i], 7);
                    int tilesMoved = Math.abs(position[0] - yPos);
                    if (tilesMoved == 0) tilesMoved += Math.abs(position[1] - xPos);
                    //if you found a friendly piece, don't count its location as a possible move
                    if (board[position[0]][position[1]] != null &&
                            board[position[0]][position[1]].color == p.color) {
                        tilesMoved--;
                    }
                    int y = yPos; int x = xPos;
                    for (int j = 0; j < tilesMoved; j++) {
                        y += Q_Moves[i][0];
                        x += Q_Moves[i][1];
                        possMoves.add(new int[] {y, x});
                    }
                }
                break;

            case 'N':
                int[] arr = {1, 2, -1, -2}; //array to get all tiles where checking knight can be
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) { //try each combo of 1 and 2
                        int y = yPos + arr[i]; int x = xPos + arr[j]; //add 1 or 2 to y and x
                        if ((arr[i] + arr[j]) % 2 == 0)
                            continue; //don't try combos that aren't a 1 and 2
                        if (!(0 <= y && y <= 7 && 0 <= x && x <= 7))
                            continue; //checks if tile is on the board
                        if (board[y][x] == null) {
                            possMoves.add(new int[] {y, x});
                            continue;}
                        if (board[y][x].color != p.color) {
                            possMoves.add(new int[] {y, x});
                            continue;}
                    }
                }
                break;

            case 'K':
                String[] K_Directions = {"NW", "NE", "SE", "SW", "N", "E", "S", "W"};
                int[][] K_Moves = {{-1, -1}, {-1, 1}, {1, 1}, {1, -1}, {-1, 0}, {0, 1}, {1, 0}, {0, -1}};
                for (int i = 0; i < 8; i++) {
                    int[] position = searchInDirection(yPos, xPos, K_Directions[i], 1);
                    int tilesMoved = Math.abs(position[0] - yPos);
                    if (tilesMoved == 0) tilesMoved += Math.abs(position[1] - xPos);
                    if (board[position[0]][position[1]] != null &&
                            board[position[0]][position[1]].color == p.color) {
                        tilesMoved--;
                    }
                    int y = yPos; int x = xPos;
                    if (tilesMoved > 0) {
                        y += K_Moves[i][0];
                        x += K_Moves[i][1];
                        possMoves.add(new int[] {y, x});
                    }
                }
                break;

            case 'p':
                int y; int x = xPos;
                if (p.color == 'b') {
                    y = yPos + 1; if (y <= 7 && board[y][x] == null) possMoves.add(new int[] {y, xPos});
                    if (yPos == 1 && board[3][xPos] == null) possMoves.add(new int[] {3, xPos});
                } else {
                    y = yPos - 1; if (y >= 0 && board[y][x] == null) possMoves.add(new int[] {y, xPos});
                    if (yPos == 6 && board[4][xPos] == null) possMoves.add(new int[] {4, xPos});
                }
                if (x != 7) {
                    x = xPos + 1;
                    if (board[y][x] != null && board[y][x].color != p.color)
                        possMoves.add(new int[] {y, x});
                }
                if (xPos != 0) {
                    x = xPos - 1;
                    if (board[y][x] != null && board[y][x].color != p.color)
                        possMoves.add(new int[] {y, x});
                }
                break;
        }
        return possMoves;
    }
}
