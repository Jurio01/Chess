package Model.Figures;

import Model.Game.Classic;
import Model.Game.Tile;
import View.Figures.KingGUI;

import java.util.ArrayList;

public class King extends Figure {
    KingGUI kingGUI;
    private boolean firstMove; //for implementation of castling
    private Tile tile;


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
        int row = tile.getRow();
        int colum = tile.getColum();
        ArrayList<Tile> tiles = Classic.getTiles();
        for (Tile tile: tiles){
            if (tile.getRow() == row){
                if (tile.getColum() == colum + 1 || tile.getColum() == colum - 1){
                    possibleMoves.add(tile);
                }
            }
            if (tile.getRow() == row - 1 || tile.getRow() == row + 1){
                if (tile.getColum() == colum || tile.getColum() == colum + 1 || tile.getColum() == colum - 1){
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
     * Takes
     * @param rook
     * and looks if this position is occupied by a rook that was not yet moved.
     */
    public void castle(Rook rook){}
}
