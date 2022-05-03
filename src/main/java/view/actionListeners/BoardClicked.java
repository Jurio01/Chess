package view.actionListeners;

import cotroller.GameController;
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
        controller.setX(x);
        controller.setY(y);
        controller.findTile();
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
