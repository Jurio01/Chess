package view.actionListeners;

import cotroller.GameController;
import view.boardViews.BoardPanel;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BoardClicked implements MouseListener {
    GameController controller;

    public BoardClicked(GameController controller){
        this.controller = controller;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX() - 16;
        int y = e.getY() - 39;
        controller.setColumn(x);
        controller.setRow(y);
        controller.findTile();
        controller.getBoard().remove(controller.getBoard().getPanel());
        JPanel panel = new BoardPanel(controller.getBoard());
        controller.getBoard().add(panel);
        controller.getBoard().setPanel(panel);
        controller.getBoard().validate();
        panel.repaint();
        System.out.println("Done");
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
