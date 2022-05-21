package view.actionListeners;

import model.game.Tile;
import model.pieces.Bishop;
import model.pieces.Pawn;
import model.pieces.Piece;
import view.boardViews.PromotionScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BishopButtonClicked implements ActionListener {
    private final Pawn pawn;
    private final PromotionScreen screen;

    public BishopButtonClicked(Pawn pawn, PromotionScreen screen) {
        this.pawn = pawn;
        this.screen = screen;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Tile tile = pawn.getTile();
        pawn.kill();
        Bishop bishop = new Bishop(tile, pawn.getColor(), pawn.getGame());
        List<Piece> pieces = (pawn.getColor().getValue() == 1) ? pawn.getGame().getWhiteFigures() : pawn.getGame().getBlackFigures();
        for (Piece piece: pieces){
            if (piece == pawn){
                pieces.remove(pawn);
                break;
            }
        }
        pieces.add(bishop);
        screen.dispose();
    }
}
