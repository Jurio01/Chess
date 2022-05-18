package model.pieces;

import model.game.Classic;
import model.game.Tile;

import java.util.ArrayList;

public class King extends Piece {
    private boolean firstMove; //for implementation of castling
    private Rook leftRook; //also for castling reasons
    private Rook rightRook;

    public King(Tile tile, int color, Classic game) {
        super(tile, color, game, (color == 1) ? "kw.png" : "kb.png");
        firstMove = true;
    }


    @Override
    public boolean move() {
        this.possibleMoves.clear();
        this.canMove();
        for (Tile tile: possibleMoves){
            if (tile.isSelected() && tile != this.tile){
//                System.out.println("Tile was found");
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
//                    System.out.println("Moved");
                    for (Piece piece: (color == 1) ? game.getBlackFigures() : game.getWhiteFigures()){
                        if (piece instanceof Pawn){
                            ((Pawn) piece).setEnPassantPossible(false);
                        }
                    }
                    game.getThreats().clear();
                    for (Piece piece: (this.color == 1) ? game.getWhiteFigures() : game.getBlackFigures()){
                        piece.unPin();
                    }
                    game.getThreats().clear();
                    this.check = false;
                    for (Piece piece: (color == 1) ? game.getWhiteFigures() : getGame().getBlackFigures()){
                        piece.canMove();
                    }
                    return true;
                }
                if (tile.isSelected() && tile != this.tile && tile.isOccupied()){
                    if (canBeTaken(tile.getPiece()) && !tile.getPiece().isProtected()){
                        this.firstMove = false;
                        take(tile);
                        this.tile.setPiece(null);
                        this.tile = tile;
                        this.tile.setPiece(this);
//                        System.out.println("Taken piece");
                        game.getThreats().clear();
                        for (Piece piece: (this.color == 1) ? game.getWhiteFigures() : game.getBlackFigures()){
                            piece.unPin();
                        }
                        this.check = false;
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
        possibleMoves.clear();
        int row = tile.getRow();
        int column = tile.getColumn();
        ArrayList<Tile> tiles = game.getTiles();
        ArrayList<Piece> enemyPieces = (this.color == 1) ? game.getBlackFigures() : game.getWhiteFigures();
        for (Tile tile : tiles) {
            if (tile.getRow() == row) {
                if (tile.getColumn() == column + 1 || tile.getColumn() == column - 1) {
                    possibleMoves.add(tile);
                    if (tile.isOccupied()){
                        if (tile.getPiece().getColor() == color){
                            tile.getPiece().protect(this);
                        }
                    }
                }
            }
            if (tile.getRow() == row - 1 || tile.getRow() == row + 1) {
                if (tile.getColumn() == column || tile.getColumn() == column + 1 || tile.getColumn() == column - 1) {
                    possibleMoves.add(tile);
                    if (tile.isOccupied()){
                        if (tile.getPiece().getColor() == color){
                            tile.getPiece().protect(this);
                        }
                    }
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
//        System.out.println("trying castling");
        ArrayList<Piece> enemyPieces = (this.color == 1) ? game.getBlackFigures() : game.getWhiteFigures();
        if (firstMove && rook.isFirstMove()){
            if (rook.getTile().getColumn() == 8){
                rightRook = rook;
                for (Piece piece: enemyPieces){
                    for (Tile tile: game.getTiles()){
                        for (Tile enemyMove : piece.getPossibleMoves()){
                            if (tile.getRow() == row && tile.getColumn() == 6){
                                rightRook.setCastlingTile(tile);
                            }
                            if (tile.getRow() == row && tile.getColumn() == 6 && (tile.isOccupied() || tile == enemyMove)){
//                                System.out.println("path is blocked");
                                break;
                            }
                            if (tile.getRow() == row && tile.getColumn() == 7 && (tile.isOccupied() || tile == enemyMove)){
                                break;
                            }
                            if (tile.getRow() == row && tile.getColumn() == 7 && !tile.isOccupied() && tile != enemyMove){
//                                System.out.println("castle short found");
                                possibleMoves.add(tile);
                                tile.setCastleMove(true);
                            }
                            possibleMoves.remove(enemyMove);
                        }
                    }
                }
            }
            if (rook.getTile().getColumn() == 1){
                leftRook = rook;
                for (Tile tile: game.getTiles()){
                    for (Piece piece: enemyPieces){
                        for (Tile enemyMove: piece.getPossibleMoves()){
                            if (tile.getRow() == row && tile.getColumn() == 4){
                                leftRook.setCastlingTile(tile);
                            }
                            if (tile.getRow() == row && (tile.getColumn() == 2 || tile.getColumn() == 4) && (tile.isOccupied() || tile == enemyMove)){
//                                System.out.println("path is blocked");
                                break;
                            }
                            if (tile.getRow() == row && tile.getColumn() == 3 && (tile.isOccupied() || tile == enemyMove)){
                                break;
                            }
                            if (tile.getRow() == row && tile.getColumn() == 3 && !tile.isOccupied() && tile != enemyMove){
//                                System.out.println("castle long found");
                                possibleMoves.add(tile);
                                tile.setCastleMove(true);
                            }
                            possibleMoves.remove(enemyMove);
                        }
                    }
                }
            }
        }
    }
}
