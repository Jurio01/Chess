package view.actionListeners;

import cotroller.GameController;
import view.boardViews.BoardPanel;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BoardClicked implements MouseListener {
    GameController controller;

    public BoardClicked(GameController controller){
        this.controller = controller;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        controller.setColumn(x);
        controller.setRowSelected(y);
        controller.findTile();
        controller.getBoard().remove(controller.getBoard().getBoardPanel());
        JPanel panel = new BoardPanel(controller.getBoard());
        controller.getBoard().add(panel);
        controller.getBoard().setBoardPanel(panel);
        controller.getBoard().validate();
        panel.repaint();
        Logger.getAnonymousLogger().log(Level.INFO,"Clicked");
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
