package model.pieces;


import model.game.Classic;
import model.game.Tile;

import java.util.ArrayList;

public class Knight extends Piece {
    KingGUI kingGUI;

    public Knight(Tile tile, int color, Classic game) {
        super(tile, color, game);
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
        for (Tile tile: tiles){
            if (tile.getColumn() == column + 2 || tile.getColumn() == column - 2){
                if (tile.getRow() == row + 1 || tile.getRow() == row - 1){
                    possibleMoves.add(tile);
                }
            }
            if (tile.getColumn() == column + 1 || tile.getColumn() == column - 1){
                if (tile.getRow() == row + 2 || tile.getRow() == row - 2){
                    possibleMoves.add(tile);
                }
            }
        }
    }


    @Override
    public boolean putInCheck() {
        return false;
    }

}
