package model.pieces;


import model.game.Classic;
import model.game.Tile;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {

    public Bishop(Tile tile, PieceColor color, Classic game) {
        super(tile, color, game, (color.getValue() == 1) ? "bw.png" : "bb.png");
    }


    @Override
    public void canMove() {
        possibleMoves.clear();
        int row = tile.getRow();
        int column = tile.getColumn();
        List<Tile> tiles = game.getTiles();
        boolean firstBlock = false; //first direction is blocked
        boolean secondBlock = false; //second direction is blocked
        boolean thirdBlock = false; //third direction is blocked
        boolean fourthBlock = false; //fourth direction is blocked
        //basically go through all the possible increments/decrements of its current tile column and row and check if
        //such a tile exists. If it does, it goes to the possible moves list and if it is occupied everything behind
        //it is blocked.
        for (int i = 1; i < 8; i++) {
            for (Tile tile : tiles) {
                if (tile.getRow() == row + i && tile.getColumn() == column + i && !firstBlock) {
                    possibleMoves.add(tile);
                    if (tile.isOccupied()) {
                        firstBlock = true;
                    }
                }
                if (tile.getRow() == row + i && tile.getColumn() == column - i && !secondBlock) {
                    possibleMoves.add(tile);
                    if (tile.isOccupied()) {
                        secondBlock = true;
                    }
                }
                if (tile.getRow() == row - i && tile.getColumn() == column + i && !thirdBlock) {
                    possibleMoves.add(tile);
                    if (tile.isOccupied()) {
                        thirdBlock = true;
                    }
                }
                if (tile.getRow() == row - i && tile.getColumn() == column - i && !fourthBlock) {
                    possibleMoves.add(tile);
                    if (tile.isOccupied()) {
                        fourthBlock = true;
                    }
                }
            }
        }
    }

}
