package view.boardViews;

import model.pieces.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BoardPanel extends JPanel {
    private final Board board;
    public BoardPanel(Board board){
        this.board = board;
    }

    @Override
    public void paint(Graphics g){
        boolean white = true;
        for (int y = 0; y < 8; y++){
            for (int x = 0; x< 8; x++){
                if (white){
                    g.setColor(new Color(229,208,203));
                }
                else {
                    g.setColor(new Color(192,172,181));
                }
                g.fillRect(x *64,y*64,64,64);
                white = !white;
            }
            white = !white;
        }
        for (Piece piece: board.getController().getGame().getWhiteFigures()){
            String imageName = piece.getImageName();
            try {
                Image image = ImageIO.read(getClass().getResource("/png/" + imageName));
                g.drawImage(image.getScaledInstance(64,64,Image.SCALE_SMOOTH),(piece.getTile().getColumn() - 1) * 64, (piece.getTile().getRow() - 8) * (-1) * 64,null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (Piece piece: board.getController().getGame().getBlackFigures()){
            String imageName = piece.getImageName();
            try {
                Image image = ImageIO.read(getClass().getResource("/png/" + imageName));
                g.drawImage(image.getScaledInstance(64,64,Image.SCALE_SMOOTH),(piece.getTile().getColumn() - 1) * 64, (piece.getTile().getRow() - 8) * (-1) * 64,null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
