package Model.Figures;

import Model.Game.Classic;
import Model.Game.Tile;
import View.Figures.KingGUI;

import java.util.ArrayList;

public class King extends Figure {
    KingGUI kingGUI;
    private boolean firstMove; //for implementation of castling

    public King(Tile tile, int color) {
        super(tile, color);
    }


    @Override
    public void move() {
        for (Tile tile: game.getTiles()){
            if (tile.isSelected() && tile != this.tile){
                if (tile.getFigure() == null){
                    this.tile = tile;
                }
                else {
                    if (tile.getFigure().canBeTaken(color)){
                        take(tile);
                        this.tile = tile;
                    }
                }
            }
        }
    }

    @Override
    public void take(Tile tile) {
        Figure figure = tile.getFigure();
        figure = null;
    }

    @Override
    public boolean canBeTaken(int color) {
        if (this.color == color){
            return false;
        }
        return true;
    }

    @Override
    public void canMove() {
        int row = tile.getRow();
        int colum = tile.getColum();
        ArrayList<Tile> tiles = game.getTiles();
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
