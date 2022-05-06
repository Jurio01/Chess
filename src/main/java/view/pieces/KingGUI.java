package view.pieces;

import model.pieces.King;
import view.Board;


import java.awt.*;


public class KingGUI extends Component{
    King king;
    Image image;

    public KingGUI(King king) {
        this.king = king;
    }

    public void projectImage(){
        image = Toolkit.getDefaultToolkit().getImage("C:\\Users\\jurio\\OneDrive\\Počítač\\2.semester\\PJV\\Semestrálka\\Tile set\\png");
        Board board = king.getGame().getController().getBoard();
    }
}
