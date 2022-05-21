package model.pieces;

import model.game.Classic;
import model.game.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Rook extends Piece {

    private boolean firstMove; //for implementation of castling
    private Tile castlingTile;

    public Rook(Tile tile, PieceColor color, Classic game) {
        super(tile, color, game, (color == PieceColor.White) ? "rw.png" : "rb.png");
        firstMove = true;
    }

    @Override
    public boolean move(){
        Logger.getAnonymousLogger().log(Level.INFO,"Moving...");
        this.possibleMoves.clear();
        this.canMove();
        for (Tile tile: possibleMoves){
            if (tile.isSelected() && tile != this.tile){
                if (tile.getPiece() == null){
                    if (checkInvalidMove(tile)){
                        return false;
                    }
                    this.firstMove =false;
                    this.tile.setPiece(null);
                    this.tile = tile;
                    this.tile.setPiece(this);
                    this.possibleMoves.clear();
//                    System.out.println("Moved");
                    for (Piece piece: (color == PieceColor.White) ? game.getBlackFigures() : game.getWhiteFigures()){
                        if (piece instanceof Pawn){
                            ((Pawn) piece).setEnPassantPossible(false);
                        }
                    }
                    return true;
                }
                if (tile.isSelected() && tile != this.tile && tile.isOccupied()){
                    if (canBeTaken(tile.getPiece())){
                        if (checkInvalidMove(tile)){
                            return false;
                        }
                        take(tile);
                        this.firstMove =false;
                        this.tile.setPiece(null);
                        this.tile = tile;
                        this.tile.setPiece(this);
                        this.possibleMoves.clear();
//                        System.out.println("Taken piece");
                        for (Piece piece: (color == PieceColor.White) ? game.getBlackFigures() : game.getWhiteFigures()){
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
        List<Tile> tiles = game.getTiles();
        possibleMoves.clear();
        boolean firstBlock = false; //first direction is blocked
        boolean secondBlock = false; //second direction is blocked
        boolean thirdBlock = false; //third direction is blocked
        boolean fourthBlock = false; //fourth direction is blocked
        //basically go through all the possible increments/decrements of its current tile column and row and check if
        //such a tile exists. If it does, it goes to the possible moves list and if it is occupied everything behind
        //it is blocked.
        for (int i = 1; i < 8; i++) {
            for (Tile tile : tiles) {
                if (tile.getRow() == row + i && tile.getColumn() == column && !firstBlock){
                    possibleMoves.add(tile);
                    if (tile.isOccupied()) {
                        firstBlock = true;
                    }
                }
                if (tile.getRow() == row - i && tile.getColumn() == column && !secondBlock){
                    possibleMoves.add(tile);
                    if (tile.isOccupied()) {
                        secondBlock = true;
                    }
                }
                if (tile.getRow() == row && tile.getColumn() == column + i && !thirdBlock){
                    possibleMoves.add(tile);
                    if (tile.isOccupied()) {
                        thirdBlock = true;
                    }
                }
                if (tile.getRow() == row && tile.getColumn() == column - i && !fourthBlock){
                    possibleMoves.add(tile);
                    if (tile.isOccupied()) {
                        fourthBlock = true;
                    }
                }
            }
        }
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
