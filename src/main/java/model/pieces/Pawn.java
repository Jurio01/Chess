package model.pieces;
import model.game.Classic;
import model.game.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Pawn extends Piece {
    private boolean enPassantPossible; //important for implementation of En passant
    private boolean firstMove; //for implementation of the initial 2 squares move
    private final List<Tile> kingDangerTiles;

    public Pawn(Tile tile, PieceColor color, Classic game) {
        super(tile, color, game, (color.getValue() == 1) ? "pw.png" : "pb.png");
        kingDangerTiles = new ArrayList<>();
        enPassantPossible = false;
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
                    if (tile.getRow() == this.getTile().getRow() + 2 || tile.getRow() == this.getTile().getRow() - 2){
                        enPassantPossible = true;
                    }
                    if (tile.getRow() == this.getTile().getRow() + 1 || tile.getRow() == this.getTile().getRow() - 1){
                        enPassantPossible = false;
                    }
                    if (tile.isEnPassantMove()){
                        for (Piece pawn: (color == PieceColor.White) ? game.getBlackFigures() : game.getWhiteFigures()){
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
                    if (isOnEightRank()){
                        promote();
                    }
                    kingDangerTiles.clear();
                    for (Piece piece: (color == PieceColor.White) ? game.getBlackFigures() : game.getWhiteFigures()){
                        if (piece != this){
                            if (piece instanceof Pawn){
                                ((Pawn) piece).setEnPassantPossible(false);
                            }
                        }
                    }
                    return true;
                }
                if (tile.isSelected() && tile != this.tile && tile.isOccupied()){
                    if (canBeTaken(tile.getPiece())){
                        if (checkInvalidMove(tile)){
                            return false;
                        }
                        Logger.getAnonymousLogger().log(Level.INFO,"Taking...");
                        take(tile);
                        this.firstMove = false;
                        this.tile.setPiece(null);
                        this.tile = tile;
                        this.tile.setPiece(this);
                        this.possibleMoves.clear();
                        if (isOnEightRank()){
                            promote();
                        }
                        for (Piece piece: (color == PieceColor.White) ? game.getBlackFigures() : game.getWhiteFigures()){
                            if (piece != this){
                                if (piece instanceof Pawn){
                                    ((Pawn) piece).setEnPassantPossible(false);
                                }
                            }
                        }
                        kingDangerTiles.clear();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void canMove() {
        possibleMoves.clear();
        kingDangerTiles.clear();
        int row = this.tile.getRow();
        int column = this.tile.getColumn();
        List<Tile> tiles = this.game.getTiles();
        boolean block = false;
        if (this.color == PieceColor.White){
            for (Tile tile: tiles){
                if (tile.getRow() == row + 1 && tile.getColumn() == column && tile.isOccupied()){
                    block = true;
                }
                if (tile.getRow() == row + 1 && tile.getColumn() == column && !tile.isOccupied()){
                    this.possibleMoves.add(tile);
                }
                if (tile.getRow() == row + 1 && (tile.getColumn() == column + 1 || tile.getColumn() == column - 1)){
                    if (tile.isOccupied()){
                        possibleMoves.add(tile);
                    }
                    kingDangerTiles.add(tile);
                }
                if (tile.getRow() == row && (tile.getColumn() == column + 1 || tile.getColumn() == column - 1) && tile.getPiece() instanceof Pawn piece){
                    if (piece.isEnPassantPossible()){
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
            if (firstMove && !block){
                for (Tile tile: tiles){
                    if (tile.getRow()== row + 2 && tile.getColumn() == column && !tile.isOccupied()){
                        possibleMoves.add(tile);
                    }
                }
            }
        } else {
            for (Tile tile: tiles){
                if (tile.getRow() == row - 1 && tile.getColumn() == column && tile.isOccupied()){
                    block = true;
                }
                if (tile.getRow() == row - 1 && tile.getColumn() == column && !tile.isOccupied()){
                    possibleMoves.add(tile);
                }
                if (tile.getRow() == row - 1 && (tile.getColumn() == column + 1 || tile.getColumn() == column -1)){
                    if (tile.isOccupied()){
                        possibleMoves.add(tile);
                    }
                    kingDangerTiles.add(tile);
                }
                if (tile.getRow() == row && (tile.getColumn() == column + 1 || tile.getColumn() == column - 1) && tile.getPiece() instanceof Pawn piece){
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
            if (firstMove && !block){
                for (Tile tile: tiles){
                    if (tile.getRow() == row - 2 && tile.getColumn() == column && !tile.isOccupied()){
                        possibleMoves.add(tile);
                    }
                }
            }
        }
    }


    /**
    Gets called once the pawn moves to line 8. It calls one of four constructors of the possible figures,
    that can be chosen by the player which is then given the
    initial position of the square of the pawn that was promoted and kills the pawn
     **/
    public void promote(){
        game.getController().promote(this);
    }

    public boolean isOnEightRank(){
        return this.tile.getRow() == 8 || this.tile.getRow() == 1;
    }

    public boolean isEnPassantPossible() {
        return enPassantPossible;
    }

    public void setEnPassantPossible(boolean enPassantPossible) {
        this.enPassantPossible = enPassantPossible;
    }

    public List<Tile> getKingDangerTiles() {
        return kingDangerTiles;
    }
}

