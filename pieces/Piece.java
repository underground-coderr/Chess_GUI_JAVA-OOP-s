package pieces;
import main.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Piece {

    public int col, row;
    public int xPos, yPos;

    public boolean isWhite;
    public String name;
    public int value;

    public boolean isfirstmove = true;


    BufferedImage sheet;

    // Static block to load the sprite sheet
    {
        try {
            // Load the sprite sheet image from resources
            sheet = ImageIO.read(ClassLoader.getSystemResourceAsStream("pieces.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Scale of each sprite on the sprite sheet
    protected int sheetScale = sheet.getWidth()  / 6;

    Image sprite;

    Board board;

    public Piece(Board board) {
        this.board = board;

    }

    public boolean isValidMovement(int col, int row){
        return true;}
    public boolean moveCollideswithPiece(int col, int row){return false;}

    public void paint(Graphics g) {
        g.drawImage(sprite, xPos, yPos, null );
    }

}

