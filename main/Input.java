package main;

import pieces.Piece;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input extends MouseAdapter{

    Board board;

    // Constructor to initialize the Input with the board
    public Input(Board board){
        this.board = board;
    }

    @Override
    public void mousePressed(MouseEvent e) {

        // Calculate the column and row based on mouse position
        int col =e.getX() / board.tileSize;
        int row = e.getY() / board.tileSize;

        // Get the piece at the clicked position
        Piece pieceXY= board.getPiece(col, row);
        if(pieceXY != null){
            board.selectedPiece = pieceXY;
        }
    }

    // Method triggered when the mouse is dragged
    @Override
    public void mouseDragged(MouseEvent e) {

        if(board.selectedPiece != null){
            board.selectedPiece.xPos = e.getX() - board.tileSize / 2;
            board.selectedPiece.yPos = e.getY() - board.tileSize / 2;

            // Repaint the board to reflect the new position
            board.repaint();
        }

    }

    // Method triggered when the mouse is released
    @Override
    public void mouseReleased(MouseEvent e) {

        // Calculate the column and row based on mouse position
        int col =e.getX() / board.tileSize;
        int row = e.getY() / board.tileSize;

        if(board.selectedPiece != null){
            //create a move object for selected piece to the new position
            Move move = new Move(board, board.selectedPiece, col, row);

            if(board.isValidMove(move)){
                // If the move is valid, make the move
                board.makeMove(move);
            }else{
                // If the move is invalid, reset the piece to its original position
                board.selectedPiece.xPos = board.selectedPiece.col * board.tileSize;
                board.selectedPiece.yPos = board.selectedPiece.row * board.tileSize;
            }
        }

        // Deselect the piece and repaint the board
        board.selectedPiece = null;
        board.repaint();


    }



}
