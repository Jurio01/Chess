package Model.Figures;

import View.Figures.RookGUI;

public class Rook extends Figure{
    RookGUI rookGUI;
    private boolean firstMove; //for implementation of castling

    @Override
    public void move() {

    }

    @Override
    public void take() {

    }

    @Override
    public boolean canBeTaken() {
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
