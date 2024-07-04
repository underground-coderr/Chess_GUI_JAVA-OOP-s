package pieces;
import java.awt.*;
import java.awt.image.BufferedImage;

import main.Board;
public class Rook extends Piece {
    public Rook(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;

        this.isWhite = isWhite;
        this.name = "Rook";

        // Set the sprite image for the Rook
        this.sprite = sheet.getSubimage( 4 * sheetScale, isWhite ? 0 :sheetScale, sheetScale, sheetScale).getScaledInstance(board.tileSize, board.tileSize, BufferedImage.SCALE_SMOOTH);

    }

    // A Rook moves any number of squares along a row or column
    // This is valid if the target position is in the same column or the same row
    public boolean isValidMovement(int col, int row) {
        return this.col == col || this.row == row;
    }

    public boolean moveCollideswithPiece(int col, int row) {
        //left
        if(this.col > col)
            for(int c = this.col - 1; c > col; c--)
                if(board.getPiece(c, this.row) != null)
                    return true;

        //right
        if(this.col < col)
            for(int c = this.col + 1; c < col; c++)
                if(board.getPiece(c, this.row) != null)
                    return true;

        //up
        if(this.row > row)
            for(int r = this.row - 1; r > row; r--)
                if(board.getPiece(this.col, r) != null)
                    return true;

        //down
        if(this.row < row)
            for(int r = this.row + 1; r < row; r++)
                if(board.getPiece(this.col, r) != null)
                    return true;

        return false;
    }
}
