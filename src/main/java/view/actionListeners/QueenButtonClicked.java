package view.actionListeners;

import model.game.Tile;
import model.pieces.Pawn;
import model.pieces.Piece;
import model.pieces.PieceColor;
import model.pieces.Queen;
import view.boardViews.BoardPanel;
import view.boardViews.PromotionScreen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class QueenButtonClicked implements ActionListener {
    private final Pawn pawn;
    private final PromotionScreen screen;

    public QueenButtonClicked(Pawn pawn, PromotionScreen screen) {
        this.pawn = pawn;
        this.screen = screen;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Tile tile = pawn.getTile();
        pawn.kill();
        Queen queen = new Queen(tile, pawn.getColor(), pawn.getGame());
        List<Piece> pieces = (pawn.getColor() == PieceColor.White) ? pawn.getGame().getWhiteFigures() : pawn.getGame().getBlackFigures();
        for (Piece piece: pieces){
            if (piece == pawn){
                pieces.remove(pawn);
                break;
            }
        }
        pieces.add(queen);
        screen.getController().promotionHappened();
        screen.getController().getBoard().remove(screen.getController().getBoard().getBoardPanel());
        JPanel panel = new BoardPanel(screen.getController().getBoard());
        screen.getController().getBoard().add(panel);
        screen.getController().getBoard().setBoardPanel(panel);
        screen.getController().getBoard().validate();
        panel.repaint();
        screen.dispose();
    }
}
