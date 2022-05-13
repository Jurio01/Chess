package model.pieces;

import model.game.Classic;
import model.game.Tile;

import java.util.ArrayList;

public class Rook extends Piece {

    private boolean firstMove; //for implementation of castling
    private Tile castlingTile;
    private ArrayList<Tile> firsDirection;
    private ArrayList<Tile> secondDirection;
    private ArrayList<Tile> thirdDirection;
    private ArrayList<Tile> fourthDirection;

    public Rook(Tile tile, int color, Classic game) {
        super(tile, color, game, (color == 1) ? "rw.png" : "rb.png");
        firstMove = true;
        this.firsDirection = new ArrayList<Tile>();
        this.secondDirection = new ArrayList<Tile>();
        this.thirdDirection = new ArrayList<Tile>();
        this.fourthDirection = new ArrayList<Tile>();
    }

    @Override
    public boolean move(){
        this.possibleMoves.clear();
        this.canMove();
        for (Tile tile: possibleMoves){
            if (tile.isSelected() && tile != this.tile){
                System.out.println("Tile was found");
                if (tile.getPiece() == null){
                    this.firstMove =false;
                    this.tile.setPiece(null);
                    this.tile = tile;
                    this.tile.setPiece(this);
                    this.possibleMoves.clear();
                    this.canMove();
                    System.out.println("Moved");
                    for (Piece piece: (color == 1) ? game.getBlackFigures() : game.getWhiteFigures()){
                        if (piece instanceof Pawn){
                            ((Pawn) piece).setEnPassantPossible(false);
                        }
                    }
                    return true;
                }
                if (tile.isSelected() && tile != this.tile && tile.isOccupied()){
                    if (tile.getPiece().canBeTaken(color)){
                        take(tile);
                        this.firstMove =false;
                        this.tile.setPiece(null);
                        this.tile = tile;
                        this.tile.setPiece(this);
                        this.possibleMoves.clear();
                        this.canMove();
                        System.out.println("Taken piece");
                        for (Piece piece: (color == 1) ? game.getBlackFigures() : game.getWhiteFigures()){
                            if (piece instanceof Pawn){
                                ((Pawn) piece).setEnPassantPossible(false);
                            }
                        }
                        return true;
                    }
                }
            }
        }
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
        for (int i = 1; i < 8; i++) {
            for (Tile tile : tiles) {
                if (tile.getRow() == row + i && tile.getColumn() == column && !firstBlock) {
                    possibleMoves.add(tile);
                    firsDirection.add(tile);
                    if (tile.isOccupied()) {
                        firstBlock = true;
                        if (tile.getPiece() instanceof King){
                            dangerMoves = firsDirection;
                        }
                    }
                }
                if (tile.getRow() == row - i && tile.getColumn() == column && !secondBlock) {
                    possibleMoves.add(tile);
                    secondDirection.add(tile);
                    if (tile.isOccupied()) {
                        secondBlock = true;
                        if (tile.getPiece() instanceof King){
                            dangerMoves = secondDirection;
                        }
                    }
                }
                if (tile.getRow() == row && tile.getColumn() == column + i && !thirdBlock) {
                    possibleMoves.add(tile);
                    thirdDirection.add(tile);
                    if (tile.isOccupied()) {
                        thirdBlock = true;
                        if (tile.getPiece() instanceof King){
                            dangerMoves = thirdDirection;
                        }
                    }
                }
                if (tile.getRow() == row && tile.getColumn() == column - i && !fourthBlock) {
                    possibleMoves.add(tile);
                    fourthDirection.add(tile);
                    if (tile.isOccupied()) {
                        fourthBlock = true;
                        if (tile.getPiece() instanceof King){
                            dangerMoves = fourthDirection;
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

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }

    public boolean isFirstMove() {
        return firstMove;
    }

    public void setCastlingTile(Tile castlingTile) {
        this.castlingTile = castlingTile;
    }

    public Tile getCastlingTile() {
        return castlingTile;
    }
}
