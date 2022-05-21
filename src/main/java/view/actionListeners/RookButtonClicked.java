package view.actionListeners;

import model.game.Tile;
import model.pieces.Pawn;
import model.pieces.Piece;
import model.pieces.Rook;
import view.boardViews.PromotionScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RookButtonClicked implements ActionListener {
    private final Pawn pawn;
    private final PromotionScreen screen;

    public RookButtonClicked(Pawn pawn, PromotionScreen screen) {
        this.pawn = pawn;
        this.screen = screen;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Tile tile = pawn.getTile();
        pawn.kill();
        Rook rook = new Rook(tile, pawn.getColor(), pawn.getGame());
        ArrayList<Piece> pieces = (pawn.getColor() == 1) ? pawn.getGame().getWhiteFigures() : pawn.getGame().getBlackFigures();
        for (Piece piece: pieces){
            if (piece == pawn){
                pieces.remove(pawn);
                break;
            }
        }
        rook.setFirstMove(false);
        pieces.add(rook);
        screen.dispose();
    }
}
