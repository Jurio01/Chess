package view.boardViews;

import cotroller.GameController;
import view.actionListeners.EndButtonClicked;

import javax.swing.*;
import java.awt.event.ActionListener;

public class EndScreen extends JFrame {
    private final GameController controller;
    private final String message;
    public EndScreen(GameController controller, String message){
        this.controller = controller;
        this.message = message;
    }

    public void init(){
        controller.getBoard().setEnabled(false);
        this.setAlwaysOnTop(true);
        this.setBounds(8,8,200,200);
        this.setVisible(true);

        JButton button = new JButton("End");
        JPanel panel = new JPanel();
        panel.add(button);
        JLabel label = new JLabel();
        label.setText(message);
        panel.add(label);
        button.setBounds(0,0,20,10);
        ActionListener listener = new EndButtonClicked();
        button.addActionListener(listener);
        this.add(panel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
