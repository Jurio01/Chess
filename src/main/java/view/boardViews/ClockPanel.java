package view.boardViews;

import cotroller.GameController;

import javax.swing.*;
import java.awt.*;

public class ClockPanel extends JPanel {
    private final GameController controller;
    private String whiteTime;
    private String blackTime;

    public ClockPanel(GameController controller){
        this.controller = controller;
        this.setPreferredSize(new Dimension(300,512));
        this.whiteTime = controller.getGame().getClock().parseTime(controller.getGame().getClock().getWhiteTime());
        this.blackTime = controller.getGame().getClock().parseTime(controller.getGame().getClock().getBlackTime());
    }
    @Override
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,300,512);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(new Font("Time script", Font.BOLD, 40));
        g2d.setPaint(Color.WHITE);
        g2d.drawString(whiteTime,150,450);
        g2d.drawString(blackTime,150,50);
    }
}
