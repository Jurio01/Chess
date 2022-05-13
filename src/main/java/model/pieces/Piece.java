package model.pieces;

import model.game.Classic;
import model.game.Tile;

import java.util.ArrayList;

public abstract class Piece {
    Tile tile;
    ArrayList<Tile> possibleMoves;
    boolean check;
    int color;
    Classic game;
    boolean threat; //to make it easier to find in an array of figures and to make it clear witch piece is threat
                    //to the king so the other player may react accordingly

    public Piece(Tile tile, int color, Classic game) {
        this.tile = tile;
        this.color = color;
        this.game = game;
    }

    /**
    Move assigns new value to the position of the figure based on the possible
    moves the figure can make. Move always calls canMove after it updates the figures position and putInCheck
    after it.
     **/
    public boolean move(){
        this.possibleMoves.clear();
        this.canMove();
        King king = (this.color == 1) ? game.getWhiteKing() : game.getBlackKing();
        if (pin){
            return false;
        }
        for (Tile tile: (king.isCheck()) ? checkMoves :possibleMoves){
            if (tile.isSelected() && tile != this.tile){
                System.out.println("Tile was found");
                if (tile.getPiece() == null){
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
                    if (king.isCheck()){
                        pin = true;
                    }
                    return true;
                }
                if (tile.isSelected() && tile != this.tile && tile.isOccupied()){
                    if (tile.getPiece().canBeTaken(color)){
                        take(tile);
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
    /**
    Take is used after move is called on a position that is occupied by another piece. It calls on another method
    called canBeTaken witch returns boolean, see canBeTaken for further description. If canBeTaken returns True, take
    removes the other piece standing on the position on which move is being called by setting its position to
    null value.
     **/
    public void take(Tile tile) {
        if (this.color == 1){
            game.getBlackFigures().remove(tile.getPiece());
        }
        else {
            game.getWhiteFigures().remove(tile.getPiece());
        }
        tile.setPiece(null);
    }
    /**
    * canBeTaken is called always when take is called. It returns True if a piece is of the opposite color and if
    * it can be taken in agreement with chess rules (can't take a piece if it puts your king in check), returns False
    * otherwise.
     *
     * @param color*/
    public boolean canBeTaken(int color){
        return color != this.color;
    }
    /**
    Can move is always called once the player selected a tile of his colour with a piece on it. It looks for all
     possible moves that the piece can make
     **/
    public abstract void canMove();

    /**
    Put in check is always called once canMove method is called. If any of the items in the list of possible moves
    are equal to the position of the king it returns True, returns False otherwise.
     **/
    abstract boolean putInCheck();

    public Tile getTile() {
        return tile;
    }

    public ArrayList<Tile> getPossibleMoves() {
        return possibleMoves;
    }

    public boolean isCheck() {
        return check;
    }

    public int getColor() {
        return color;
    }

    public Classic getGame() {
        return game;
    }

    public boolean isThreat() {
        return threat;
    }

    public void setTile(Tile tile) {
        this.tile.setPiece(null);
        this.tile = tile;
        this.tile.setPiece(this);
    }

    public void kill(){
        this.tile = null;
    }

    public ArrayList<Tile> getDangerMoves() {
        return dangerMoves;
    }

    public void checkingMoves(){
        canMove();
        ArrayList<Piece> threats = game.getThreats();
        if (threats.size() >= 2){
            return;
        }
        ArrayList<Tile> moves = possibleMoves;
        for (Piece threat: threats){
            for (Tile threatMove : threat.getDangerMoves()){
                if (moves.contains(threatMove)){
                    checkMoves.add(threatMove);
                }
            }
        }
        for (Tile tile : moves){
            if (tile.isOccupied()){
                if (tile.getPiece().isThreat()){
                    checkMoves.add(tile);
                }
            }
        }
    }

    public void unPin() {
        this.pin = false;
    }
}
