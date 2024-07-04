package pieces;
import java.awt.*;
import java.awt.image.BufferedImage;

import main.Board;
public class Bishop extends Piece {
    public Bishop(Board     board, int col, int row, boolean isWhite) {
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;

        this.isWhite = isWhite;
        this.name = "Bishop";

        this.sprite = sheet.getSubimage( 2 * sheetScale, isWhite ? 0 :sheetScale, sheetScale, sheetScale).getScaledInstance(board.tileSize, board.tileSize, BufferedImage.SCALE_SMOOTH);

    }

    // A Bishop moves diagonally, so the absolute difference between the current and target columns
    // must be equal to the absolute difference between the current and target rowss
    public boolean isValidMovement(int col, int row) {
         return Math.abs(this.col - col) == Math.abs(this.row - row);
    }

    public boolean moveCollideswithPiece(int col, int row) {

        //returning true means that the piece is colliding with other piece
        // returning false means that the piece can move there

        //up left
        if(this.col > col && this.row > row)
            for(int i = 1; i < Math.abs(this.col - col); i++)
                if(board.getPiece(this.col - i, this. row - i) != null)
                    return true;

        //up right
        if(this.col < col && this.row > row)
            for(int i = 1; i < Math.abs(this.col - col); i++)
                if(board.getPiece(this.col + i, this. row - i) != null)
                    return true;

        //down left
        if(this.col > col && this.row < row)
            for(int i = 1; i < Math.abs(this.col - col); i++)
                if(board.getPiece(this.col - i, this. row + i) != null)
                    return true;

        //down right
        if(this.col < col && this.row < row)
            for(int i = 1; i < Math.abs(this.col - col); i++)
                if(board.getPiece(this.col + i, this. row + i) != null)
                    return true;

        return false;
    }
}
