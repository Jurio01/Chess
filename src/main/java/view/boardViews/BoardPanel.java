package view.boardViews;

import model.pieces.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BoardPanel extends JPanel {
    private Board board;
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
        int y;
        int x = 0;
        boolean firstInstance = true;
        for (Piece piece: board.controller.getGame().getWhiteFigures()){
            if (piece instanceof Pawn){
                y = 6 * 64;
            } else {
              y = 7 * 64;
            }
            if (x > 512){
                x = 0;
            }
            if (piece instanceof Rook) {
                if (firstInstance) {
                    x = 0;
                } else {
                    x = 7 * 64;
                }
                firstInstance = !firstInstance;
            }
            if (piece instanceof Knight){
                if (firstInstance){
                    x = 64;
                } else {
                    x = 6 * 64;
                }
                firstInstance =! firstInstance;
            }
            if (piece instanceof Bishop){
                if (firstInstance){
                    x = 2 * 64;
                } else {
                    x = 5 * 64;
                }
                firstInstance =! firstInstance;
            }
            if (piece instanceof Queen){
                x = 3 * 64;
            }
            if (piece instanceof King){
                x = 4 * 64;
            }
            String imageName = piece.getImageName();
            try {
                Image image = ImageIO.read(getClass().getResource("/png/" + imageName));
                g.drawImage(image.getScaledInstance(64,64,Image.SCALE_SMOOTH),x,y,null);
                x += 64;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        firstInstance = true;
        x = 0;
        for (Piece piece: board.controller.getGame().getBlackFigures()){
            if (piece instanceof Pawn){
                y = 64;
            } else {
                y = 0;
            }
            if (x > 512){
                x = 0;
            }
            if (piece instanceof Rook) {
                if (firstInstance) {
                    x = 0;
                } else {
                    x = 7 * 64;
                }
                firstInstance = !firstInstance;
            }
            if (piece instanceof Knight){
                if (firstInstance){
                    x = 64;
                } else {
                    x = 6 * 64;
                }
                firstInstance =! firstInstance;
            }
            if (piece instanceof Bishop){
                if (firstInstance){
                    x = 2 * 64;
                } else {
                    x = 5 * 64;
                }
                firstInstance =! firstInstance;
            }
            if (piece instanceof Queen){
                x = 3 * 64;
            }
            if (piece instanceof King){
                x = 4 * 64;
            }
            String imageName = piece.getImageName();
            try {
                Image image = ImageIO.read(getClass().getResource("/png/" + imageName));
                g.drawImage(image.getScaledInstance(64,64,Image.SCALE_SMOOTH),x,y,null);
                x += 64;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
