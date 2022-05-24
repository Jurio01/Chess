package model.pieces;

import model.game.Classic;
import model.game.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class King extends Piece {
    private boolean firstMove; //for implementation of castling
    private Rook leftRook; //also for castling reasons
    private Rook rightRook;

    public King(Tile tile, PieceColor color, Classic game) {
        super(tile, color, game, (color == PieceColor.White) ? "kw.png" : "kb.png");
        firstMove = true;
    }


    @Override
    public boolean move() {
        Logger.getAnonymousLogger().log(Level.INFO,"Moving...");
        this.possibleMoves.clear();
        this.canMove();
        for (Tile tile: possibleMoves){
            if (tile.isSelected() && tile != this.tile){
                if (tile.getPiece() == null){
                    if (checkInvalidMove(tile)){
                        return false;
                    }
                    if (tile.isCastleMove()){
                        if (tile.getColumn() == 7){
                            rightRook.setTile(rightRook.getCastlingTile());
                        }
                        if (tile.getColumn() == 3){
                            leftRook.setTile(leftRook.getCastlingTile());
                        }
                    }
                    this.firstMove = false;
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
                    this.check = false;
                    return true;
                }
                if (tile.isSelected() && tile != this.tile && tile.isOccupied()){
                    if (canBeTaken(tile.getPiece())){
                        if (checkInvalidMove(tile)){
                            return false;
                        }
                        this.firstMove = false;
                        take(tile);
                        this.tile.setPiece(null);
                        this.tile = tile;
                        this.tile.setPiece(this);
                        this.check = false;
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
        int row = tile.getRow();
        int column = tile.getColumn();
        List<Tile> tiles = game.getTiles();
        List<Piece> enemyPieces = (this.color == PieceColor.White) ? game.getBlackFigures() : game.getWhiteFigures();
        for (Tile tile : tiles) {
            if (tile.getRow() == row) {
                if (tile.getColumn() == column + 1 || tile.getColumn() == column - 1) {
                    possibleMoves.add(tile);
                }
            }
            if (tile.getRow() == row - 1 || tile.getRow() == row + 1) {
                if (tile.getColumn() == column || tile.getColumn() == column + 1 || tile.getColumn() == column - 1) {
                    possibleMoves.add(tile);
                }
            }
        }
        if (!check){
            for (Rook rook : game.getRooks()){
                if (rook.getColor() == this.color){
                    castle(rook);
                }
            }
        }
        for (Piece piece: enemyPieces){
            if (piece instanceof Pawn && piece.getColor() != color){
                for (Tile tile: ((Pawn) piece).getKingDangerTiles()){
                    this.possibleMoves.remove(tile);
                }
            } else {
                for (Tile tile : piece.getPossibleMoves()){
                    this.possibleMoves.remove(tile);
                }
            }
        }

    }


    public void putInCheck() {
        this.check = true;
    }

    public void unCheck(){
        this.check = false;
    }


    /**
     *Takes
     * @param rook and looks if this position is occupied by a rook that was not yet moved, then checks if all the tiles between king and rook are
     * not occupied.
     */
    // It's ugly but it works
    public void castle(Rook rook) {
        int row = this.tile.getRow();
        List<Piece> enemyPieces = (color == PieceColor.White) ? game.getBlackFigures() :game.getWhiteFigures();
        if (firstMove && rook.isFirstMove()){
            if (rook.getTile().getColumn() == 8){
                rightRook = rook;
                for (Piece piece: enemyPieces){
                    piece.canMove();
                    for (Tile tile: game.getTiles()){
                        if (tile.getRow() == row && (tile.getColumn() == 6 || tile.getColumn() == 7) &&
                                (tile.isOccupied() || piece.getPossibleMoves().contains(tile))){
                            return;
                        }
                    }
                }
                for (Tile tile: game.getTiles()){
                    if (tile.getRow() == row && tile.getColumn() == 6){
                        rightRook.setCastlingTile(tile);
                    }
                    if (tile.getRow() == row && tile.getColumn() == 7 && !tile.isOccupied()){
                        possibleMoves.add(tile);
                        tile.setCastleMove(true);
                    }
                }
            }
        }
        if (rook.getTile().getColumn() == 1){
            leftRook = rook;
            for (Piece piece: enemyPieces){
                for (Tile tile: game.getTiles()){
                    if (tile.getRow() == row && (tile.getColumn() == 3 || tile.getColumn() == 4) &&
                            (tile.isOccupied() || piece.getPossibleMoves().contains(tile))){
                        return;
                    }
                    if (tile.getRow() == row && tile.getColumn() == 2 && tile.isOccupied()){
                        return;
                    }
                }
            }
            for (Tile tile: game.getTiles()){
                if (tile.getRow() == row && tile.getColumn() == 4){
                    leftRook.setCastlingTile(tile);
                }
                if (tile.getRow() == row && tile.getColumn() == 3 && !tile.isOccupied()){
                    if (checkInvalidMove(tile)){
                        return;
                    }
                    possibleMoves.add(tile);
                    tile.setCastleMove(true);
                }
            }
        }
    }
}
