package pieces;
import java.awt.*;
import java.awt.image.BufferedImage;

import main.Board;
public class Knight extends Piece {
    public Knight(Board board, int col, int row, boolean isWhite) {
    super(board);
    this.col = col;
    this.row = row;
    this.xPos = col * board.tileSize;
    this.yPos = row * board.tileSize;

    this.isWhite = isWhite;
    this.name = "Knight";

    // Set the sprite image for the Knight
    this.sprite = sheet.getSubimage( 3 * sheetScale, isWhite ? 0 :sheetScale, sheetScale, sheetScale).getScaledInstance(board.tileSize, board.tileSize, BufferedImage.SCALE_SMOOTH);

    }

    // A Knight moves in an L-shape: two squares in one direction and one square perpendicular
    // This is represented by the product of the differences between the current and target positions being 2
    public boolean isValidMovement(int col, int row) {
        return Math.abs(col - this.col) * Math.abs(row - this.row) == 2;
    }

}
