package main;

import pieces.Piece;

public class CheckScanner {

    Board board; // board object
    //constructor
    public CheckScanner(Board board) {
        this.board = board;
    }

    // Method to check if a move results in the king being in check
    public boolean isKingChecked(Move move){

        Piece king = board.findKing(move.piece.isWhite);
        assert king != null;

        int KingCol = king.col;
        int KingRow = king.row;

        // If the selected piece is the king, update the king's position to the new move position
        if(board.selectedPiece != null && board.selectedPiece.name.equals("King")){
            KingCol = move.newCol;
            KingRow = move.newRow;
        }

        //check for threats from different pieces
        return  hitByRook  (move.newCol, move.newRow, king, KingCol, KingRow ,0, 1) || //up
                hitByRook  (move.newCol, move.newRow, king, KingCol, KingRow, 1, 0) || //down
                hitByRook  (move.newCol, move.newRow, king, KingCol, KingRow, 0, -1) || //left
                hitByRook  (move.newCol, move.newRow, king, KingCol, KingRow, -1, 0) || //right

                hitByBishop(move.newCol, move.newRow, king, KingCol, KingRow, -1, -1) || //up left
                hitByBishop(move.newCol, move.newRow, king, KingCol, KingRow,1 ,-1) || // up down
                hitByBishop(move.newCol, move.newRow, king, KingCol, KingRow,1 ,1) || // down right
                hitByBishop(move.newCol, move.newRow, king, KingCol, KingRow,-1, 1) || // down left

                hitByKnight(move.newCol, move.newRow, king, KingCol , KingRow ) ||
                hitByPawn(move.newCol, move.newRow, king, KingCol, KingRow)||
                        hitByKing( king, KingCol, KingRow);
    }

    //check all possible way in which king can be checked
    private boolean hitByRook(int col, int row, Piece King, int kingCol, int kingRow, int colVal, int rowVal){
        for(int i = 1; i < 8; i++){
            if(kingCol + (i * colVal) == col && kingRow + (i * rowVal) == row){
                break;
            }

            Piece piece = board.getPiece(kingCol + (i * colVal), kingRow + (i * rowVal));
            if(piece != null && piece != board.selectedPiece){
                if(!board.sameTeam(piece, King) && (piece.name.equals("Rook") || piece.name.equals("Queen"))){
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private boolean hitByBishop(int col, int row, Piece King, int kingCol, int kingRow, int colVal, int rowVal){
        for(int i = 1; i < 8; i++){
            if(kingCol - (i * colVal) == col && kingRow - (i * rowVal) == row){
                break;
            }

            Piece piece = board.getPiece(kingCol - (i * colVal), kingRow - (i * rowVal));
            if(piece != null && piece != board.selectedPiece){
                if(!board.sameTeam(piece, King) && (piece.name.equals("Bishop") || piece.name.equals("Queen"))){
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private boolean hitByKnight(int col, int row, Piece King, int kingCol, int kingRow){
         return checkKnight(board.getPiece(kingCol - 1, kingRow - 2), King, col, row) ||
                checkKnight(board.getPiece(kingCol + 1, kingRow - 2), King, col, row) ||
                checkKnight(board.getPiece(kingCol + 2, kingRow - 1), King, col, row) ||
                checkKnight(board.getPiece(kingCol + 2, kingRow + 1), King, col, row) ||
                checkKnight(board.getPiece(kingCol + 1, kingRow + 2), King, col, row) ||
                checkKnight(board.getPiece(kingCol - 1, kingRow + 2), King, col, row) ||
                checkKnight(board.getPiece(kingCol - 2, kingRow + 1), King, col, row) ||
                checkKnight(board.getPiece(kingCol - 2, kingRow - 1), King, col, row);
    }

    private boolean checkKnight(Piece p, Piece k, int col, int row){
        return p != null && !board.sameTeam(p, k) && p.name.equals("Knight") && !(p.col == col && p.row == row);
    }

    private boolean hitByKing(Piece King, int KingCol, int KingRow){
         return checkKing(board.getPiece(KingCol - 1, KingRow - 1), King) ||
                checkKing(board.getPiece(KingCol + 1, KingRow - 1), King) ||
                checkKing(board.getPiece(KingCol, KingRow - 1), King) ||
                checkKing(board.getPiece(KingCol - 1, KingRow), King) ||
                checkKing(board.getPiece(KingCol + 1, KingRow), King) ||
                checkKing(board.getPiece(KingCol - 1, KingRow + 1), King) ||
                checkKing(board.getPiece(KingCol + 1, KingRow + 1), King) ||
                checkKing(board.getPiece(KingCol, KingRow + 1), King);
    }

    private boolean checkKing(Piece p, Piece k){
        return p != null && !board.sameTeam(p, k) && p.name.equals("King");
    }

    private boolean hitByPawn(int col, int row, Piece King, int kingCol, int kingRow){
        int colorVal = King.isWhite ? -1 : 1;
        return checkPawn(board.getPiece(kingCol + 1, kingRow + colorVal), King, col, row) ||
                checkPawn(board.getPiece(kingCol - 1, kingRow + colorVal), King, col, row);
    }

    // Helper method to check if a pawn can attack the king
    private boolean checkPawn(Piece p, Piece k, int col, int row){
        return p != null && !board.sameTeam(p, k) && p.name.equals("Pawn") && !(p.col == col && p.row == row);
    }

    // Method to check if the game is over (checkmate or stalemate)
    public boolean isGameOver(Piece king){

        // Iterate through all pieces of the same color as the king
        for(Piece piece : board.pieceList){
            if(board.sameTeam(piece, king)){
                board.selectedPiece = piece == king ? king : null;
                for(int row = 0; row < board.rows; row++){
                    for(int col = 0; col < board.cols; col++){
                        Move move = new Move(board, piece, col, row);
                        if(board.isValidMove(move)){
                            return false; // If a valid move is found, the game is not over
                        }
                    }
                }
            }
        }
        return true; // If no valid moves are found, the game is over
    }

}
