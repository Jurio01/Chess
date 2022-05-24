package view.boardViews;

import cotroller.GameController;
import model.pieces.Pawn;
import view.actionListeners.BishopButtonClicked;
import view.actionListeners.KnightButtonClicked;
import view.actionListeners.QueenButtonClicked;
import view.actionListeners.RookButtonClicked;

import javax.swing.*;
import java.awt.*;

public class PromotionScreen extends JFrame {
    private final GameController controller;
    private final Pawn pawn;

    public void init(){
        this.setUndecorated(true);
        JRadioButton queenButton = new JRadioButton("Queen");
        JRadioButton rookButton = new JRadioButton("Rook");
        JRadioButton knightButton = new JRadioButton("knight");
        JRadioButton bishopButton = new JRadioButton("Bishop");
        ButtonGroup group = new ButtonGroup();
        JPanel panel = new JPanel(new GridLayout(4,1));

        group.add(queenButton);
        group.add(rookButton);
        group.add(knightButton);
        group.add(bishopButton);

        queenButton.addActionListener(new QueenButtonClicked(pawn, this));
        rookButton.addActionListener(new RookButtonClicked(pawn, this));
        knightButton.addActionListener(new KnightButtonClicked(pawn, this));
        bishopButton.addActionListener(new BishopButtonClicked(pawn, this));

        panel.add(queenButton);
        panel.add(rookButton);
        panel.add(knightButton);
        panel.add(bishopButton);

        this.add(panel);

        this.setBounds(10,10,100,200);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public PromotionScreen(GameController controller, Pawn pawn) throws HeadlessException {
        this.controller = controller;
        this.pawn = pawn;
    }

    public GameController getController() {
        return controller;
    }
}
