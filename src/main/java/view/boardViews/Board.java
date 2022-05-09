package view.boardViews;

import cotroller.GameController;
import view.actionListeners.BoardClicked;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.io.IOException;


public class Board extends JFrame {
    private GameController controller;
    private JPanel panel;

    public Board(GameController controller){
        this.controller = controller;
    }
    public void init(){
        this.setBounds(8,8,528,551);
        MouseListener listener = new BoardClicked(controller);
        panel = new BoardPanel(this);
        this.add(panel);
        this.panel = new BoardPanel(this);

        this.addMouseListener(listener);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        Image image = null;
        this.setTitle("AnarchyChessApp");
        try {
             image = ImageIO.read(getClass().getResource("/png/kw.png")); //checkout resources/png/README for information behind the look of the pieces.
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setIconImage(image);



    }
    public JPanel getPanel(){
        return this.panel;
    }

    public GameController getController() {
        return controller;
    }
}
