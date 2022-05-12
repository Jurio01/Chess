package model.pieces;
import model.game.Classic;
import model.game.Tile;
import view.pieces.PawnGUI;

import java.util.ArrayList;

public class Pawn extends Piece {
    PawnGUI pawnGUI;
    private boolean movedLast; //important for implementation of En passant
    private boolean firstMove; //for implementation of the initial 2 squares move

    public Pawn(Tile tile, int color, Classic game) {
        super(tile, color, game);
        movedLast = false;
        firstMove = true;
        pawnGUI = new PawnGUI();
    }

    @Override
    public boolean move(){
        this.possibleMoves.clear();
        this.canMove();
        for (Tile tile: possibleMoves){
            if (tile.isSelected() && tile != this.tile){
                System.out.println("Tile was found");
                if (tile.getPiece() == null){
                    if (tile.getRow() == this.getTile().getRow() + 2 || tile.getRow() == this.getTile().getRow() - 2){
                        enPassantPossible = true;
                        System.out.println("EnPassant possible");
                    }
                    if (tile.getRow() == this.getTile().getRow() + 1 || tile.getRow() == this.getTile().getRow() - 1){
                        enPassantPossible = false;
                    }
                    if (tile.isEnPassantMove()){
                        for (Piece pawn: (color == 1) ? game.getBlackFigures() : game.getWhiteFigures()){
                            if (pawn instanceof Pawn){
                                if (((Pawn) pawn).isEnPassantPossible()){
                                    take(pawn.tile);
                                    break;
                                }
                            }
                        }
                    }
                    this.tile.setPiece(null);
                    this.tile = tile;
                    this.tile.setPiece(this);
                    this.firstMove = false;
                    System.out.println("Moved");
                    if (isOnEightLine()){
                        promote();
                    }
                    return true;
                }
                if (tile.isSelected() && tile != this.tile && tile.isOccupied()){
                    if (tile.getPiece().canBeTaken(color)){
                        take(tile);
                        this.tile.setPiece(null);
                        this.tile = tile;
                        this.tile.setPiece(this);
                        if (isOnEightLine()){
                            promote();
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
        System.out.println("Searching moves");
        int row = this.tile.getRow();
        int column = this.tile.getColumn();
        ArrayList<Tile> tiles = this.game.getTiles();
        if (this.color == 1){
            for (Tile tile: tiles){
                if (tile.getRow() == row + 1 && tile.getColumn() == column){
                    this.possibleMoves.add(tile);
                    System.out.println("Found a tile");
                }
                if (firstMove){
                    if (tile.getRow()== row + 2 && tile.getColumn() == column){
                        possibleMoves.add(tile);
                        System.out.println("Found a tile");
                    }
                }
                if (tile.getRow() == row + 1 && (tile.getColumn() == column + 1 || tile.getColumn() == column - 1) && tile.isOccupied()){
                    possibleMoves.add(tile);
                }
                if (tile.getRow() == row && (tile.getColumn() == column + 1 || tile.getColumn() == column - 1) && tile.getPiece() instanceof Pawn){
                    Pawn piece = (Pawn)tile.getPiece();
                    if (piece.isEnPassantPossible()){
                        System.out.println("Found EnPassant pawn");
                        for (Tile enPassantTile: game.getTiles()){
                            if (enPassantTile.getRow() == piece.getTile().getRow() + 1 && enPassantTile.getColumn() == piece.getTile().getColumn()){
                                possibleMoves.add(enPassantTile);
                                enPassantTile.setEnPassantMove(true);
                                break;
                            }
                        }
                    }
                }
            }
        } else {
            for (Tile tile: tiles){
                if (tile.getRow() == row - 1 && tile.getColumn() == column){
                    possibleMoves.add(tile);
                }
                if (firstMove){
                    if (tile.getRow() == row -2 && tile.getColumn() == column){
                        possibleMoves.add(tile);
                    }
                }
                if (tile.getRow() == row - 1 && (tile.getColumn() == column + 1 || tile.getColumn() == column -1) && tile.isOccupied()){
                    possibleMoves.add(tile);
                }
                if (tile.getRow() == row && (tile.getColumn() == column + 1 || tile.getColumn() == column - 1) && tile.getPiece() instanceof Pawn){
                    Pawn piece = (Pawn)tile.getPiece();
                    if (piece.isEnPassantPossible()){
                        for (Tile tile1: game.getTiles()){
                            if (tile1.getRow() == piece.getTile().getRow() - 1 && tile1.getColumn() == piece.getTile().getColumn()){
                                possibleMoves.add(tile1);
                                tile1.setEnPassantMove(true);
                                break;
                            }
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


    /**
    Gets called once the pawn moves to line 8. It calls one of four constructors of the possible figures,
    that can be chosen by the player which is then given the
    initial position of the square of the pawn that was promoted and assigns null values to
    both line and row values.
     **/
    public void promote(){
        game.getController().promote(this);
    }

    public boolean isOnEightLine(){
        return this.tile.getRow() == 8 || this.tile.getRow() == 1;
    }

    public boolean isEnPassantPossible() {
        return enPassantPossible;
    }

    public void setEnPassantPossible(boolean enPassantPossible) {
        this.enPassantPossible = enPassantPossible;
    }
}

