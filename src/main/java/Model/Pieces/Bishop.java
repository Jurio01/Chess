package Model.Pieces;


import Model.Game.Tile;
import View.Figures.BishopGUI;

import java.util.ArrayList;

public class Bishop extends Piece {
    BishopGUI bishopGUI;

    public Bishop(Tile tile, int color) {
        super(tile, color);
        bishopGUI = new BishopGUI();
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
        boolean firstBlock = false; //first direction is blocked
        boolean secondBlock = false; //second direction is blocked
        boolean thirdBlock = false; //third direction is blocked
        boolean fourthBlock = false; //fourth direction is blocked
        //basically go through all the possible increments/decrements of its current tile column and row and check if
        //such a tile exists. If it does, it goes to the possible moves list and if it is occupied everything behind
        //it is blocked.
        for (int i = 1; i < 8; i++){
            for (Tile tile: tiles){
                if (tile.getRow() == row + i && tile.getColumn() == column + i && !firstBlock){
                    possibleMoves.add(tile);
                    if (tile.isOccupied()){
                        firstBlock = true;
                    }
                }
                if (tile.getRow() == row + i && tile.getColumn() == column - i && !secondBlock){
                    possibleMoves.add(tile);
                    if (tile.isOccupied()){
                        secondBlock = true;
                    }
                }
                if (tile.getRow() == row - i && tile.getColumn() == column + i && !thirdBlock){
                    possibleMoves.add(tile);
                    if (tile.isOccupied()){
                        thirdBlock = true;
                    }
                }
                if (tile.getRow() == row - i && tile.getColumn() == column - i && !fourthBlock){
                    possibleMoves.add(tile);
                    if (tile.isOccupied()){
                        fourthBlock = true;
                    }
                }
            }
        }
    }



    @Override
    public boolean putInCheck() {
        return false;
    }
}
