package model.pieces;

import model.game.Classic;
import model.game.Tile;

import java.util.ArrayList;

public class King extends Piece {
    private boolean firstMove; //for implementation of castling
    private Rook leftRook; //also for castling reasons
    private Rook rightRook;

    public King(Tile tile, int color, Classic game) {
        super(tile, color, game);
        firstMove = true;
    }


    @Override
    public boolean move() {
        this.possibleMoves.clear();
        this.canMove();
        for (Tile tile: possibleMoves){
            if (tile.isSelected() && tile != this.tile){
                System.out.println("Tile was found");
                if (tile.getPiece() == null){
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
                        this.firstMove = false;
                        take(tile);
                        this.tile.setPiece(null);
                        this.tile = tile;
                        this.tile.setPiece(this);
                        System.out.println("Taken piece");
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
        ArrayList<Piece> pieces = (this.color == 1) ? game.getWhiteFigures() : game.getBlackFigures();
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
        for (Piece piece: pieces){
            if (piece instanceof Rook){
                castle((Rook) piece);
            }
        }
    }

    @Override
    public void isInCheck() {

    }

    @Override
    public boolean putInCheck() {
        this.check = true;
        return false;
    }

    /**
     * Takes
     *
     * @param rook and looks if this position is occupied by a rook that was not yet moved.
     */
    // It's ugly but it works
    public void castle(Rook rook) {
        int row = this.tile.getRow();
        System.out.println("trying castling");
        if (firstMove && rook.isFirstMove()){
            if (rook.getTile().getColumn() == 8){
                rightRook = rook;
                for (Tile tile: game.getTiles()){
                    if (tile.getRow() == row && tile.getColumn() == 6){
                        rightRook.setCastlingTile(tile);
                    }
                    if (tile.getRow() == row && tile.getColumn() == 6 && tile.isOccupied()){
                        System.out.println("path is blocked");
                        break;
                    }
                    if (tile.getRow() == row && tile.getColumn() == 7 && !tile.isOccupied()){
                        System.out.println("castle short found");
                        possibleMoves.add(tile);
                        tile.setCastleMove(true);
                    }
                }
            }
            if (rook.getTile().getColumn() == 1){
                leftRook = rook;
                for (Tile tile: game.getTiles()){
                    if (tile.getRow() == row && tile.getColumn() == 4){
                        leftRook.setCastlingTile(tile);
                    }
                    if (tile.getRow() == row && (tile.getColumn() == 2 || tile.getColumn() == 4) && tile.isOccupied()){
                        System.out.println("path is blocked");
                        break;
                    }
                    if (tile.getRow() == row && tile.getColumn() == 3 && !tile.isOccupied()){
                        System.out.println("castle long found");
                        possibleMoves.add(tile);
                        tile.setCastleMove(true);
                    }
                }
            }
        }
    }
}
