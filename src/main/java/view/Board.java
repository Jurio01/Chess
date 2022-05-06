package view;

import cotroller.GameController;
import view.actionListeners.BoardClicked;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;


public class Board extends JFrame {
    GameController controller;
    JPanel panel;

    public Board(GameController controller){
        this.controller = controller;
    }
    public void init(){
        this.setBounds(8,8,528,551);
        final JPanel panel = new JPanel(){
            @Override
            public void paint(Graphics g){
                boolean white = true;
                for (int y = 0; y < 8; y++){
                    for (int x = 0; x< 8; x++){
                        if (white){
                            g.setColor(new Color(238,238,210));
                        }
                        else {
                            g.setColor(new Color(118,150,86));
                        }
                        g.fillRect(x *64,y*64,64,64);
                        white = !white;
                    }
                    white = !white;
                }
            }
        };
        MouseListener listener = new BoardClicked(controller);
        this.add(panel);
        this.panel = panel;
        controller.getGame().getWhiteKing().getKingGUI().projectImage();

        this.addMouseListener(listener);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);



    }
    public JPanel getPanel(){
        return this.panel;
    }
}
