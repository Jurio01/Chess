package Figures;

import java.util.ArrayList;

public class Bishop implements Figure{
    private int line;
    private String row;
    private ArrayList<String> possibleMoves;
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
