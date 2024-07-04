package pieces;
import java.awt.*;
import java.awt.image.BufferedImage;
import main.Board;
import main.Board;
import main.Move;

public class King extends Piece {
    public King(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;

        this.isWhite = isWhite;
        this.name = "King";

        this.sprite = sheet.getSubimage( 0 * sheetScale, isWhite ? 0 :sheetScale, sheetScale, sheetScale).getScaledInstance(board.tileSize, board.tileSize, BufferedImage.SCALE_SMOOTH);

    }

    // A King moves one square in any direction or can castle
    // Check for normal one-square movement
    public boolean isValidMovement(int col, int row) {
        return Math.abs((col - this.col) * (row - this.row)) == 1 || Math.abs(col - this.col) + Math.abs(row -this.row) == 1 || canCastle(col, row);
    }

    private boolean canCastle(int col, int row) {

        if (this.row == row) {

            // Castling kingside
            if(col == 6){
            Piece rook = board.getPiece(7, row); // get the rook in the corder
            if (rook != null && rook.isfirstmove && isfirstmove){
                // ensure path between king and rook is clear
                return  board.getPiece(5, row) == null &&
                        board.getPiece(6, row) == null &&
                        !board.checkScanner.isKingChecked(new Move(board, this, 5, row));

                }

            // Castling queenside
            } else if(col == 2){
                Piece rook = board.getPiece(0, row);
                if (rook != null && rook.isfirstmove && isfirstmove){
                    // Ensure the path between King and Rook is clear and King is not in check
                    return  board.getPiece(3, row) == null &&
                            board.getPiece(2, row) == null &&
                            board.getPiece(1, row) == null &&
                            !board.checkScanner.isKingChecked(new Move(board, this, 3, row));

                }
            }
        }
        return false; // castling not possible

    }

}

