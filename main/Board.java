package main;

import pieces.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

// Class definition for Board, extending JPanel (Inheritance)
public class Board extends JPanel {

    // attributes
    public int tileSize= 75;

    int rows= 8;
    int cols= 8;

    ArrayList<Piece> pieceList= new ArrayList<>();

    public Piece selectedPiece;

    Input input = new Input(this); // object of input class

    public CheckScanner checkScanner = new CheckScanner(this); //object of check scanner class

    public int enPassantTile = -1;

    private boolean isWhiteToMove = true;
    private boolean isGameOver = false;

    private JLabel statusLabel;

    // Constructor
    public Board(JLabel statusLabel){
        this.statusLabel = statusLabel;
        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
        addPiece();

        // adding event listeners (composition)
        this.addMouseListener(input);
        this.addMouseMotionListener(input);

        updateStatusLabel();


    }


    // Method to get a piece at a specific Location
    public Piece getPiece(int col, int row){

        for(Piece piece: pieceList){
            if(piece.col == col && piece.row == row){
                return piece;

            }
        }

        return null;
    }

    // Method to get the tile number based on column and row
    public int getTileNum(int col, int row){
        return row * row + col;
    }

    // Method to make a move
    public void makeMove(Move move){

        if (!isValidMove(move)) {
            statusLabel.setText("Invalid move");
            return;
        }

        if(move.newCol < 0 || move.newCol >= cols || move.newRow < 0 || move.newRow >= rows) {
            return;
        }

        if(move.piece.name.equals("Pawn")){
            movePawn(move);
        }else if(move.piece.name.equals("King")) {
            moveKing(move);
        }


            move.piece.col = move.newCol;
            move.piece.row = move.newRow;
            move.piece.xPos = move.newCol * tileSize;
            move.piece.yPos = move.newRow * tileSize;

            move.piece.isfirstmove = false;

            capture(move.capture);

            isWhiteToMove = !isWhiteToMove;

            updateGameState();
            updateStatusLabel();

    }

    // Private methods to do specific task (Encapsulation)
    private void moveKing(Move move){

        if(Math.abs(move.piece.col - move.newCol) == 2){
            Piece rook;
            if(move.piece.col < move.newCol){
                rook = getPiece(7, move.piece.row);
                rook.col = 5;
            }else{
                rook = getPiece(0, move.piece.row);
                rook.col = 3;
            }

            rook.xPos = rook.col * tileSize;
        }

    }

    private void movePawn(Move move){

        // en passant
        int colorIndex = move.piece.isWhite ? 1 : -1;

        if(getTileNum(move.newCol, move.newRow) == enPassantTile){
            move.capture = getPiece(move.newCol, move.newRow + colorIndex);
        }
        if(Math.abs(move.piece.row - move.newRow) == 2 ){
            enPassantTile = getTileNum(move.newCol, move.newRow + colorIndex);
        }else{
            enPassantTile = -1;
        }

        //promotion
        colorIndex = move.piece.isWhite ? 0 : 7;
        if(move.newRow == colorIndex){
            promotePawn(move);
        }


    }

    private void promotePawn(Move move){
        pieceList.add(new Queen(this, move.newCol, move.newRow, move.piece.isWhite));
        capture(move.piece);
    }

    public void capture(Piece piece){
        pieceList.remove(piece);
    }

    // Method to check if a move is valid
    public boolean isValidMove(Move move){

        if(isGameOver){
            return false;
        }

        if(move.piece.isWhite != isWhiteToMove){
            return false;
        }

        if(sameTeam(move.piece, move.capture)){
            return false;
        }
        if(!move.piece.isValidMovement(move.newCol, move.newRow)){
            return false;
        }
        if(move.piece.moveCollideswithPiece(move.newCol, move.newRow)){
            return false;
        }
        if(checkScanner.isKingChecked(move)){
            return false;
        }

        if(move.newCol < 0 || move.newCol >= cols || move.newRow < 0 || move.newRow >= rows) {
            return false;
        }


        return true;
    }

    // Method to check if two pieces are on the same team
    public boolean sameTeam(Piece p1, Piece p2){
        if(p1 == null || p2 == null){
            return false;
        }
        return p1.isWhite == p2.isWhite;
    }

    // Method to find the king of a specified color
    Piece findKing(boolean isWhite){
        for(Piece piece: pieceList){
            if(isWhite == piece.isWhite && piece.name.equals("King")){
                return piece;
            }
        }
        return null;
    }

    // Method to add pieces to the board
    public void addPiece(){

//          //Checkmate
//
//        pieceList.add(new King(this, 4, 0 , false));
//        pieceList.add(new Rook(this, 0, 7 , true));
//        pieceList.add(new Queen(this, 3, 7 , true));
//        pieceList.add(new King(this, 4, 7 , true));

//
//
//
//        //Stalemate
//
//        pieceList.add(new King(this, 5, 2 , false));
//        pieceList.add(new Rook(this, 6, 7 , true));
//        pieceList.add(new Rook(this, 0, 0 , true));
//        pieceList.add(new Queen(this, 3, 1 , true));
//        pieceList.add(new King(this, 5, 4 , true));
//
//
//        //Castle
//
//        pieceList.add(new King(this, 4, 0 , false));
//        pieceList.add(new Rook(this, 0, 7 , true));
//        pieceList.add(new King(this, 4, 7 , true));
//        pieceList.add(new Rook(this, 7, 7 , true));
//
//        //En passant
//
//        pieceList.add(new King(this, 4, 0 , false));
//        pieceList.add(new Pawn(this, 3, 4 , false));
//        pieceList.add(new King(this, 4, 7 , true));
//        pieceList.add(new Pawn(this, 4, 6 , true));
//
//
//        //Default Board

        pieceList.add(new Rook(this, 0, 0 , false));
        pieceList.add(new Knight(this, 1, 0 , false));
        pieceList.add(new Bishop(this, 2, 0 , false));
        pieceList.add(new Queen(this, 3, 0 , false));
        pieceList.add(new King(this, 4, 0 , false));
        pieceList.add(new Bishop(this, 5, 0 , false));
        pieceList.add(new Knight(this, 6, 0 , false));
        pieceList.add(new Rook(this, 7, 0 , false));

        pieceList.add(new Pawn(this, 0, 1 , false));
        pieceList.add(new Pawn(this, 1, 1 , false));
        pieceList.add(new Pawn(this, 2, 1 , false));
        pieceList.add(new Pawn(this, 3, 1 , false));
        pieceList.add(new Pawn(this, 4, 1 , false));
        pieceList.add(new Pawn(this, 5, 1 , false));
        pieceList.add(new Pawn(this, 6, 1 , false));
        pieceList.add(new Pawn(this, 7, 1 , false));

        pieceList.add(new Rook(this, 0, 7 , true));
        pieceList.add(new Knight(this, 1, 7 , true));
        pieceList.add(new Bishop(this, 2,  7, true));
        pieceList.add(new Queen(this, 3, 7 , true));
        pieceList.add(new King(this, 4, 7 , true));
        pieceList.add(new Bishop(this, 5, 7 , true));
        pieceList.add(new Knight(this, 6, 7 , true));
        pieceList.add(new Rook(this, 7, 7 , true));

        pieceList.add(new Pawn(this, 0, 6 , true));
        pieceList.add(new Pawn(this, 1, 6 , true));
        pieceList.add(new Pawn(this, 2, 6 , true));
        pieceList.add(new Pawn(this, 3, 6 , true));
        pieceList.add(new Pawn(this, 4, 6 , true));
        pieceList.add(new Pawn(this, 5, 6 , true));
        pieceList.add(new Pawn(this, 6, 6 , true));
        pieceList.add(new Pawn(this, 7, 6 , true));

          }

    // Private method to update the game state (Encapsulation)
    private void updateGameState(){
        Piece King = findKing(isWhiteToMove);
        if(checkScanner.isGameOver(King)){
            if(checkScanner.isKingChecked(new Move(this, King, King.col, King.row))){
                String winner = isWhiteToMove ? "Black wins!" : "White wins!";
                statusLabel.setText(winner);
                System.out.println(winner);
            }else{
                statusLabel.setText("Stalemate...");
                System.out.println("Stalemate...");

            }
            isGameOver = true;
        }
        else if(insufficientMaterial(true)&& insufficientMaterial(false)){
            statusLabel.setText("Insufficient material");
            System.out.println("Insufficient material");
            isGameOver = true;
        }
    }

    // Private method to check for insufficient material (Encapsulation)
private boolean insufficientMaterial(boolean isWhite){
        ArrayList<String> names = pieceList.stream()
                .filter(p -> p.isWhite == isWhite)
                .map(p -> p.name)
                .collect(Collectors.toCollection(ArrayList::new));
        if (names.contains("Queen") || names.contains("Rook") || names.contains("Pawn")){
            return false;
        }
        return names.size() < 3;
}

    // Private method to update the status label (Encapsulation)
    private void updateStatusLabel() {
        if (isGameOver) {
            return;
        }
        String turnText = isWhiteToMove ? "White's turn" : "Black's turn";
        statusLabel.setText(turnText);
    }

//chess board color
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        //paint board
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++) {
                g2d.setColor((c + r) % 2 == 0 ? new Color(255, 255, 255) :
                        new Color(56, 56, 56));
                g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);
            }

        //paint available move
        if(selectedPiece != null)
            for(int r = 0; r < rows; r++){
            for(int c = 0; c < cols; c++){

                if(isValidMove(new Move(this, selectedPiece, c, r))){

                    g2d.setColor(new Color(68, 180, 57, 190));
                    g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);

                }

            }
        }

        for (Piece piece : pieceList) {
            piece.paint(g2d);
        }

    }
}