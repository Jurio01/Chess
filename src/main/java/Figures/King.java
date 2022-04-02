package Figures;

import java.util.ArrayList;

public class King extends Figure {

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

    /**
     * Takes
     * @param line &
     * @param row
     * and looks if this position is occupied by a rook that was not yet moved.
     */
    public void castle(int line, String row){}
}
