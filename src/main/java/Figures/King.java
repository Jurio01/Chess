package Figures;

import java.util.ArrayList;

public class King implements Figure {
    private int line;
    private String row;
    private ArrayList<String> possibleMoves;
    private boolean firstMove; //for implementation of castling
    private boolean check;

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
