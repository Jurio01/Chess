package view.boardViews;

import cotroller.GameController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Board extends JFrame {
    private final GameController controller;
    private JPanel boardPanel;
    private JPanel clockPanel;

    public Board(GameController controller){
        this.controller = controller;
    }
    public void init(){
        this.setLayout(new GridBagLayout());
        boardPanel = new BoardPanel(this);
        this.add(boardPanel);
        clockPanel = new ClockPanel(controller);
        this.add(clockPanel);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        Image image = null;
        this.setTitle("AnarchyChessApp");
        try {
             image = ImageIO.read(getClass().getResource("/png/kw.png")); //checkout resources/png/README for information behind the look of the pieces.
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setIconImage(image);
        this.setResizable(false);
        this.pack();

    }
    public JPanel getBoardPanel(){
        return this.boardPanel;
    }

    public GameController getController() {
        return controller;
    }

    public void setBoardPanel(JPanel boardPanel) {
        this.boardPanel = boardPanel;
    }

    public JPanel getClockPanel() {
        return clockPanel;
    }

    public void setClockPanel(JPanel clockPanel) {
        this.clockPanel = clockPanel;
    }
}
