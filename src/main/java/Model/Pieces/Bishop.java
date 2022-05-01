package Model.Pieces;


import Model.Game.Tile;
import View.Figures.BishopGUI;

public class Bishop extends Piece {
    BishopGUI bishopGUI;

    public Bishop(Tile tile, int color) {
        super(tile, color);
    }

    @Override
    public void move() {

    }

    @Override
    public void take(Tile tile) {

    }

    @Override
    public boolean canBeTaken(int color) {
        return false;
    }

    @Override
    public void canMove() {

    }

    @Override
    public void isInCheck() {

    }

    @Override
    public boolean putInCheck() {
        return false;
    }
}
