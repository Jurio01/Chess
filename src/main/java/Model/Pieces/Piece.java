package Model.Pieces;

import Model.Game.Classic;
import Model.Game.Tile;

import java.util.ArrayList;

public abstract class Piece {
    Tile tile;
    ArrayList<Tile> possibleMoves;
    boolean check;
    int color;
    Classic game;
    boolean threat;

    public Piece(Tile tile, int color) {
        this.tile = tile;
        this.color = color;
    }

    /**
    Move assigns new value to the position of the figure based on the possible
    moves the figure can make. Move always calls canMove after it updates the figures position and putInCheck
    after it.
     **/
    public void move(){
        for (Tile tile: game.getTiles()){
            if (tile.isSelected() && tile != this.tile){
                if (tile.getFigure() == null){
                    this.tile = tile;
                }
                else {
                    if (tile.getFigure().canBeTaken(color)){
                        take(tile);
                        this.tile = tile;
                        tile.setPiece(this);
                    }
                }
            }
        }
    }
    /**
    Take is used after move is called on a position that is occupied by another piece. It calls on another method
    called canBeTaken witch returns boolean, see canBeTaken for further description. If canBeTaken returns True, take
    removes the other piece standing on the position on which move is being called by setting its position to
    null value.
     **/
    public void take(Tile tile) {
        if (this.color == 1){
            game.getBlackFigures().remove(tile.getFigure());
        }
        else {
            game.getWhiteFigures().remove(tile.getFigure());
        }
        tile.setPiece(null);
    }
    /**
    * canBeTaken is called always when take is called. It returns True if a piece is of the opposite color and if
    * it can be taken in agreement with chess rules (can't take a piece if it puts your king in check), returns False
    * otherwise.
     *
     * @param color*/
    abstract boolean canBeTaken(int color);
    /**
    Can move is always called once the player selected a tile of his colour with a piece on it. It looks for all
     possible moves that the piece can make
     **/
    abstract void canMove();
    /**
    This method is always called every time that the player can play. Checks if the king is in check and if it is
    It looks for all the moves that the player can make that put it out of it from the list of possible moves.
    **/
     void isInCheck() {
        if (this.color == 1){
            for (Piece piece: game.getBlackFigures()){
                if (game.getWhiteKing().tile == piece.tile){
                    for (Piece blackPiece: game.getWhiteFigures()){
                        blackPiece.check = true;
                    }
                    piece.threat = true;
                }
            }
        }
        if (this.color == 0){
            for (Piece piece: game.getWhiteFigures()){
                if (game.getBlackKing().tile == piece.tile){
                    for (Piece whitePiece: game.getWhiteFigures()){
                        whitePiece.check = true;
                    }
                    piece.threat = true;
                }
            }
        }
    }
    /**
    Put in check is always called once canMove method is called. If any of the items in the list of possible moves
    are equal to the position of the king it returns True, returns False otherwise.
     **/
    abstract boolean putInCheck();
}
