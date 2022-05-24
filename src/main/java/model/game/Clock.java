package model.game;

import cotroller.GameController;
import view.boardViews.BoardPanel;
import view.boardViews.ClockPanel;

import javax.swing.*;

public class Clock extends Thread {
    private final GameController controller;
    private final Classic game;
    long start;
    long whiteTime = 500000;
    long blackTime = 500000;

    public Clock(GameController controller, Classic game){
        this.controller = controller;
        this.game = game;
    }

    @Override
    public void run() {
        while (!interrupted()){
            doCycle();
        }
    }

    private void doCycle(){
        start = System.currentTimeMillis();
        if (game.whiteTurn){
            try {
                sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long timeElapsed = System.currentTimeMillis() - start;
            whiteTime -= timeElapsed;
            controller.getBoard().remove(controller.getBoard().getClockPanel());
            JPanel panel = new ClockPanel(controller);
            controller.getBoard().add(panel);
            controller.getBoard().setClockPanel(panel);
            controller.getBoard().validate();
            controller.getBoard().repaint();
            if (whiteTime <= 0){
                controller.clockEnded();
            }
        } else {
            try {
                sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long timeElapsed = System.currentTimeMillis() - start;
            blackTime -= timeElapsed;
            controller.getBoard().remove(controller.getBoard().getClockPanel());
            JPanel panel = new ClockPanel(controller);
            controller.getBoard().add(panel);
            controller.getBoard().setClockPanel(panel);
            controller.getBoard().validate();
            controller.getBoard().repaint();
            if (blackTime <= 0){
                controller.clockEnded();
            }
        }
    }

    public long getWhiteTime() {
        return whiteTime;
    }

    public long getBlackTime() {
        return blackTime;
    }

    public String parseTime(long time){
        time /= 1000;
        int minutes = Math.toIntExact(time / 60);
        int secs = Math.toIntExact(time % 60);
        return String.format("%02d:%02d", minutes, secs);
    }
}
