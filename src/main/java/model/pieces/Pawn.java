package model.pieces;


import model.game.Classic;
import model.game.Tile;
import view.pieces.PawnGUI;

import java.util.ArrayList;

public class Pawn extends Piece {
    PawnGUI pawnGUI;
    private boolean movedLast; //important for implementation of En passant
    private boolean firstMove; //for implementation of the initial 2 squares move

    public Pawn(Tile tile, int color, Classic game) {
        super(tile, color, game);
        movedLast = false;
        firstMove = true;
        pawnGUI = new PawnGUI();
    }


    @Override
    public void move() {

    }

    @Override
    public boolean canBeTaken(int color) {
        return false;
    }
    @Override
    public void canMove() {
        int row = tile.getRow();
        int column = tile.getColumn();
        ArrayList<Tile> tiles = game.getTiles();
        if (color == 1){
            for (Tile tile: tiles){
                if (tile.getRow() == row + 1 && tile.getColumn() == column){
                    possibleMoves.add(tile);
                }
                if (firstMove){
                    if (tile.getRow()== row + 2 && tile.getColumn() == column){
                        possibleMoves.add(tile);
                    }
                }
                if (tile.getRow() == row + 1 && (tile.getColumn() == column + 1 || tile.getColumn() == column - 1) && tile.isOccupied()){
                    possibleMoves.add(tile);
                }
            }
        }
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
