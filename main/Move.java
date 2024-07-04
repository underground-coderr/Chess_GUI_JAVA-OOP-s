package main;

import pieces.Piece;

// Class to represent a move on the chessboard
public class Move {

    int oldCol; // Column where the piece was before the move
    int oldRow; // Row where the piece was before the move
    int newCol; // Column where the piece is after the move
    int newRow; // Row where the piece is after the move

    Piece piece; //piece being moved
    Piece capture; // the piece being captured.

    // constructor to create a move object
    public Move(Board board, Piece piece, int newCol, int newRow) {
        // Initialize the old/new position with the current / specific position of the piece
        this.oldCol = piece.col;
        this.oldRow = piece.row;
        this.newCol = newCol;
        this.newRow = newRow;

        //set the piece being moved
        this.piece = piece;
        this.capture = board.getPiece(newCol, newRow);
    }

}
