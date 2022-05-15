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
        King king = (color == 1) ? game.getWhiteKing() : game.getBlackKing();
        if (pin){
            checkingMoves();
        }
        for (Tile tile: (pin) ? checkMoves : possibleMoves){
            if (tile.isSelected() && tile != this.tile){
                System.out.println("Tile was found");
                if (tile.getPiece() == null){
                    this.firstMove =false;
                    this.tile.setPiece(null);
                    this.tile = tile;
                    this.tile.setPiece(this);
                    this.possibleMoves.clear();
                    System.out.println("Moved");
                    for (Piece piece: (color == 1) ? game.getBlackFigures() : game.getWhiteFigures()){
                        if (piece instanceof Pawn){
                            ((Pawn) piece).setEnPassantPossible(false);
                        }
                    }
                    if (king.isCheck()){
                        pin = true;
                    }
                    this.protect(null);
                    for (Piece piece: (color == 1) ? game.getWhiteFigures() : getGame().getBlackFigures()){
                        piece.canMove();
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
                        System.out.println("Taken piece");
                        for (Piece piece: (color == 1) ? game.getBlackFigures() : game.getWhiteFigures()){
                            if (piece instanceof Pawn){
                                ((Pawn) piece).setEnPassantPossible(false);
                            }
                        }
                        this.protect(null);
                        for (Piece piece: (color == 1) ? game.getWhiteFigures() : getGame().getBlackFigures()){
                            piece.canMove();
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
                if (tile.getRow() == row + i && tile.getColumn() == column){
                    if (!firstBlock){
                        possibleMoves.add(tile);
                    }
                    firsDirection.add(tile);
                    if (tile.isOccupied()) {
                        if (tile.getPiece().getColor() == color && !firstBlock){
                            tile.getPiece().protect(this);
                        }
                        firstBlock = true;
                        if (tile.getPiece() instanceof King && tile.getPiece().getColor() != color){
                            dangerMoves = firsDirection;
                        }
                    }
                }
                if (tile.getRow() == row - i && tile.getColumn() == column){
                    if (!secondBlock){
                        possibleMoves.add(tile);
                    }
                    secondDirection.add(tile);
                    if (tile.isOccupied()) {
                        if (tile.getPiece().getColor() == color && !secondBlock){
                            tile.getPiece().protect(this);
                        }
                        secondBlock = true;
                        if (tile.getPiece() instanceof King && tile.getPiece().getColor() != color){
                            dangerMoves = secondDirection;
                        }
                    }
                }
                if (tile.getRow() == row && tile.getColumn() == column + i){
                    if (!thirdBlock){
                        possibleMoves.add(tile);
                    }
                    thirdDirection.add(tile);
                    if (tile.isOccupied()) {
                        if (tile.getPiece().getColor() == color && !thirdBlock){
                            tile.getPiece().protect(this);
                        }
                        thirdBlock = true;
                        if (tile.getPiece() instanceof King && tile.getPiece().getColor() != color){
                            dangerMoves = thirdDirection;
                        }
                    }
                }
                if (tile.getRow() == row && tile.getColumn() == column - i){
                    if (!fourthBlock){
                        possibleMoves.add(tile);
                    }
                    fourthDirection.add(tile);
                    if (tile.isOccupied()) {
                        if (tile.getPiece().getColor() == color && !fourthBlock){
                            tile.getPiece().protect(this);
                        }
                        fourthBlock = true;
                        if (tile.getPiece() instanceof King && tile.getPiece().getColor() != color){
                            dangerMoves = fourthDirection;
                        }
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
