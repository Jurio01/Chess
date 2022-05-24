package view.actionListeners;

import cotroller.GameController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndButtonClicked implements ActionListener {

    public EndButtonClicked(){}

    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}
