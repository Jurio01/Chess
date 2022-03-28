package Figures;

import java.util.ArrayList;

public class Pawn implements Figure {
    private int line;
    private String row;
    private ArrayList<String> possibleMoves;
    private boolean movedLast; //important for implementation of En passant
    private boolean firstMove; //for implementation of the initial 2 squares move
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


    /*
    Gets called once the pawn moves to line 8. It calls one of three constructors of the possible figures,
    that can be chosen by the player which is then given the
    initial position of the square of the pawn that was promoted and assigns null values to
    both line and row values.
     */
    public void promote(){

    }
}
