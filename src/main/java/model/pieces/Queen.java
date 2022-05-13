package model.pieces;

import model.game.Classic;
import model.game.Tile;

import java.util.ArrayList;

public class Queen extends Piece {
    private ArrayList<Tile> firsDirection;
    private ArrayList<Tile> secondDirection;
    private ArrayList<Tile> thirdDirection;
    private ArrayList<Tile> fourthDirection;
    private ArrayList<Tile> fifthDirection;
    private ArrayList<Tile> sixthDirection;
    private ArrayList<Tile> seventhDirection;
    private ArrayList<Tile> eighthDirection;

    public Queen(Tile tile, int color, Classic game) {
        super(tile, color, game, (color == 1) ? "qw.png" : "qb.png");
        this.firsDirection = new ArrayList<Tile>();
        this.secondDirection = new ArrayList<Tile>();
        this.thirdDirection = new ArrayList<Tile>();
        this.fourthDirection = new ArrayList<Tile>();
        this.fifthDirection = new ArrayList<Tile>();
        this.sixthDirection = new ArrayList<Tile>();
        this.seventhDirection = new ArrayList<Tile>();
        this.eighthDirection = new ArrayList<Tile>();
    }

    @Override
    public void canMove() {
        int row = tile.getRow();
        int column = tile.getColumn();
        ArrayList<Tile> tiles = game.getTiles();
        //queen can move in eight directions, so we need to check if any of them is blocked
        boolean firstBlock = false; //first direction is blocked
        boolean secondBlock = false; //second direction is blocked
        boolean thirdBlock = false; //third direction is blocked
        boolean fourthBlock = false; //fourth direction is blocked
        boolean fifthBlock = false; //fifth direction is blocked
        boolean sixthBlock = false; //sixth direction is blocked
        boolean seventhBlock = false; //seventh direction is blocked
        boolean eightBlock = false; //eight direction is blocked
        //basically go through all the possible increments/decrements of its current tile column and row and check if
        //such a tile exists. If it does, it goes to the possible moves list and if it is occupied everything behind
        //it is blocked.
        for (int i = 1; i < 8; i++){
            for (Tile tile: tiles){
                if (tile.getRow() == row + i && tile.getColumn() == column && !firstBlock){
                    possibleMoves.add(tile);
                    firsDirection.add(tile);
                    if (tile.isOccupied()) {
                        firstBlock = true;
                        if (tile.getPiece() instanceof King){
                            dangerMoves = firsDirection;
                        }
                    }
                }
                if (tile.getRow() == row - i && tile.getColumn() == column && !secondBlock){
                    possibleMoves.add(tile);
                    secondDirection.add(tile);
                    if (tile.isOccupied()) {
                        secondBlock = true;
                        if (tile.getPiece() instanceof King){
                            dangerMoves = secondDirection;
                        }
                    }
                }
                if (tile.getRow() == row && tile.getColumn() == column + i && !thirdBlock){
                    possibleMoves.add(tile);
                    thirdDirection.add(tile);
                    if (tile.isOccupied()) {
                        thirdBlock = true;
                        if (tile.getPiece() instanceof King){
                            dangerMoves = thirdDirection;
                        }
                    }
                }
                if (tile.getRow() == row && tile.getColumn() == column - i && !fourthBlock){
                    possibleMoves.add(tile);
                    fourthDirection.add(tile);
                    if (tile.isOccupied()) {
                        fourthBlock = true;
                        if (tile.getPiece() instanceof King){
                            dangerMoves = fourthDirection;
                        }
                    }
                }
                if (tile.getRow() == row + i && tile.getColumn() == column + i && !fifthBlock){
                    possibleMoves.add(tile);
                    fifthDirection.add(tile);
                    if (tile.isOccupied()) {
                        fifthBlock = true;
                        if (tile.getPiece() instanceof King){
                            dangerMoves = fifthDirection;
                        }
                    }
                }
                if (tile.getRow() == row + i && tile.getColumn() == column - i && !sixthBlock){
                    possibleMoves.add(tile);
                    sixthDirection.add(tile);
                    if (tile.isOccupied()){
                        sixthBlock = true;
                        if (tile.getPiece() instanceof King){
                            dangerMoves = sixthDirection;
                        }
                    }
                }
                if (tile.getRow() == row - i && tile.getColumn() == column + i && !seventhBlock){
                    possibleMoves.add(tile);
                    seventhDirection.add(tile);
                    if (tile.isOccupied()){
                        seventhBlock = true;
                        if (tile.getPiece() instanceof King){
                            dangerMoves = seventhDirection;
                        }
                    }
                }
                if (tile.getRow() == row - i && tile.getColumn() == column - i && !eightBlock){
                    possibleMoves.add(tile);
                    eighthDirection.add(tile);
                    if (tile.isOccupied()){
                        eightBlock = true;
                        if (tile.getPiece() instanceof King){
                            dangerMoves = eighthDirection;
                        }
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
