package Model.Pieces;


import Model.Game.Tile;
import View.Figures.KingGUI;

public class Knight extends Piece {
    KingGUI kingGUI;

    public Knight(Tile tile, int color) {
        super(tile, color);
    }


    @Override
    public void move() {

    }

    @Override
    void take(Tile tile) {

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
