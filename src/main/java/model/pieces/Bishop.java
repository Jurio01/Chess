package model.pieces;


import model.game.Classic;
import model.game.Tile;
import view.pieces.BishopGUI;

import java.util.ArrayList;

public class Bishop extends Piece {
    BishopGUI bishopGUI;

    public Bishop(Tile tile, int color, Classic game) {
        super(tile, color, game);
        bishopGUI = new BishopGUI();
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
        for (int i = 1; i < 8; i++) {
            for (Tile tile : tiles) {
                if (tile.getRow() == row + i && tile.getColumn() == column + i) {
                    if (!firstBlock){
                        possibleMoves.add(tile);
                    }
                    firsDirection.add(tile);
                    if (tile.isOccupied()) {
                        firstBlock = true;
                        if (tile.getPiece() instanceof King && tile.getPiece().getColor() != color){
                            dangerMoves = firsDirection;
                        }
                        if (tile.getPiece().getColor() == color){
                            tile.getPiece().protect(this);
                        }
                    }
                }
                if (tile.getRow() == row + i && tile.getColumn() == column - i) {
                    if (!secondBlock){
                        possibleMoves.add(tile);
                    }
                    secondDirection.add(tile);
                    if (tile.isOccupied()) {
                        secondBlock = true;
                        if (tile.getPiece() instanceof King && tile.getPiece().getColor() != color){
                            dangerMoves = secondDirection;
                        }
                        if (tile.getPiece().getColor() == color){
                            tile.getPiece().protect(this);
                        }
                    }
                }
                if (tile.getRow() == row - i && tile.getColumn() == column + i) {
                    if (!thirdBlock){
                        possibleMoves.add(tile);
                    }
                    thirdDirection.add(tile);
                    if (tile.isOccupied()) {
                        thirdBlock = true;
                        if (tile.getPiece() instanceof King && tile.getPiece().getColor() != color){
                            dangerMoves = thirdDirection;
                        }
                        if (tile.getPiece().getColor() == color){
                            tile.getPiece().protect(this);
                        }
                    }
                }
                if (tile.getRow() == row - i && tile.getColumn() == column - i) {
                    if (!fourthBlock){
                        possibleMoves.add(tile);
                    }
                    fourthDirection.add(tile);
                    if (tile.isOccupied()) {
                        fourthBlock = true;
                        if (tile.getPiece() instanceof King && tile.getPiece().getColor() != color){
                            dangerMoves = fourthDirection;
                        }
                        if (tile.getPiece().getColor() == color){
                            tile.getPiece().protect(this);
                        }
                    }
                }
            }
        }
    }

}
