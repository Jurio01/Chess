package view.boardViews;

import cotroller.GameController;
import view.actionListeners.BoardClicked;
import javax.swing.*;
import java.awt.event.MouseListener;



public class Board extends JFrame {
    GameController controller;
    JPanel panel;

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



    }
    public JPanel getPanel(){
        return this.panel;
    }
}
