package Model.Figures;


import Model.Game.Tile;
import View.Figures.PawnGUI;

public class Pawn extends Figure {
    PawnGUI pawnGUI;
    private boolean movedLast; //important for implementation of En passant
    private boolean firstMove; //for implementation of the initial 2 squares move

    public Pawn(Tile tile, int color) {
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


    /**
    Gets called once the pawn moves to line 8. It calls one of three constructors of the possible figures,
    that can be chosen by the player which is then given the
    initial position of the square of the pawn that was promoted and assigns null values to
    both line and row values.
     **/
    public void promote(int switcher){

    }
}
