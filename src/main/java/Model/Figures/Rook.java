package Model.Figures;

import Model.Game.Tile;
import View.Figures.RookGUI;

public class Rook extends Figure{
    RookGUI rookGUI;
    private boolean firstMove; //for implementation of castling

    public Rook(Tile tile, int color) {
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
