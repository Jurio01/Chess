package view.boardViews;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ErrorScreen extends JFrame {
    public ErrorScreen(){
        JPanel panel = new JPanel();
        JLabel label = new JLabel("An error has occurred");
        JButton button = new JButton("Exit");
        button.addActionListener(e -> System.exit(0));

        panel.add(label);
        panel.add(button);
        this.add(panel);
    }
}
