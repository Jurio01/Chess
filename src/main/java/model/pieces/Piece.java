package model.pieces;

import model.game.Classic;
import model.game.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Piece {
    protected Tile tile;
    protected List<Tile> possibleMoves;
    protected boolean check;
    protected PieceColor color;
    protected String imageName;
    protected Classic game;

    public Piece(Tile tile, PieceColor color, Classic game, String imageName) {
        this.tile = tile;
        this.color = color;
        this.game = game;
        this.imageName = imageName;
        tile.setPiece(this);
        this.possibleMoves = new ArrayList<>();
    }

    /**
    Move assigns new value to the position of the figure based on the possible
    moves the figure can make. Move always calls canMove after it updates the figures position and putInCheck
    after it.
     **/
    public boolean move(){
        Logger.getAnonymousLogger().log(Level.INFO,"Moving...");
        this.canMove();
        for (Tile tile: getPossibleMoves()){
            if (tile.isSelected() && tile != this.tile){
                if (tile.getPiece() == null){
                    if (checkInvalidMove(tile)){
                        return false;
                    }
                    this.tile.setPiece(null);
                    this.tile = tile;
                    this.tile.setPiece(this);
                    this.possibleMoves.clear();
                    for (Piece piece: (color.getValue() == 1) ? game.getBlackFigures() : game.getWhiteFigures()){
                        if (piece instanceof Pawn){
                            ((Pawn) piece).setEnPassantPossible(false);
                        }
                    }
                    for (Piece piece: (color.getValue() == 1) ? game.getWhiteFigures() : getGame().getBlackFigures()){
                        piece.canMove();
                    }
                    return true;
                }
                if (tile.isSelected() && tile != this.tile && tile.isOccupied()){
                    Logger.getAnonymousLogger().log(Level.INFO,"Taking...");
                    if (canBeTaken(tile.getPiece())){
                        if (checkInvalidMove(tile)){
                            return false;
                        }
                        take(tile);
                        this.tile.setPiece(null);
                        this.tile = tile;
                        this.tile.setPiece(this);
                        this.possibleMoves.clear();
                        for (Piece piece: (color.getValue() == 1) ? game.getBlackFigures() : game.getWhiteFigures()){
                            if (piece instanceof Pawn){
                                ((Pawn) piece).setEnPassantPossible(false);
                            }
                        }
                        for (Piece piece: (color.getValue() == 1) ? game.getWhiteFigures() : getGame().getBlackFigures()){
                            piece.canMove();
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
    protected void take(Tile tile) {
        if (this.color.getValue() == 1){
            game.getBlackFigures().remove(tile.getPiece());
        }
        else {
            game.getWhiteFigures().remove(tile.getPiece());
        }
        if (tile.getPiece() instanceof Rook){
            game.getRooks().remove((Rook) tile.getPiece());
        }
        tile.setPiece(null);
    }
    /**
    * canBeTaken is called always when take is called. It returns True if a piece is of the opposite color and if
    * it can be taken in agreement with chess rules (can't take a piece if it puts your king in check), returns False
    * otherwise.
     *
     * @param piece piece ti be taken*/
    boolean canBeTaken(Piece piece){
        return piece.color != this.color;
    }
    /**
    Can move is always called once the player selected a tile of his colour with a piece on it. It looks for all
     possible moves that the piece can make
     **/
    public abstract void canMove();

    public boolean checkInvalidMove(Tile tile){
        Tile prevTile = this.tile;
        Piece prevPiece = tile.getPiece();
        List<Piece> piecesWhite = new ArrayList<>(game.getWhiteFigures());
        List<Piece> piecesBlack = new ArrayList<>(game.getBlackFigures());
        if (prevPiece != null){
            if (canBeTaken(prevPiece)){
                take(prevPiece.getTile());
            }
        }
        prevTile.setPiece(null);
        if (tile.isOccupied()){
            if (tile.getPiece().getColor() == this.color){
                prevTile.setPiece(this);
                return true;
            }
        }
        this.tile = tile;
        this.tile.setPiece(this);
        game.check();
        if ((color.getValue() == 1) ? game.getWhiteKing().isCheck() : game.getBlackKing().isCheck()){
            this.tile.setPiece(prevPiece);
            this.tile = prevTile;
            if (prevPiece != null){
                if (canBeTaken(prevPiece)){
                    prevPiece.setTile(tile);
                    game.setWhitePieces(piecesWhite);
                    game.setBlackPieces(piecesBlack);
                }
            }
            this.tile.setPiece(this);
            game.check();
            return true;
        }
        this.tile.setPiece(prevPiece);
        this.tile = prevTile;
        this.tile.setPiece(this);
        if (prevPiece != null){
            if (canBeTaken(prevPiece)){
                prevPiece.setTile(tile);
                game.setWhitePieces(piecesWhite);
                game.setBlackPieces(piecesBlack);
            }
        }
        game.check();
        return false;
    }

    public Tile getTile() {
        return tile;
    }

    public List<Tile> getPossibleMoves() {
        return possibleMoves;
    }

    public boolean isCheck() {
        return check;
    }

    public PieceColor getColor() {
        return color;
    }

    public String getImageName() {
        return imageName;
    }

    public Classic getGame() {
        return game;
    }

    public void setTile(Tile tile) {
        this.tile.setPiece(null);
        this.tile = tile;
        this.tile.setPiece(this);
    }

    public void kill(){
        this.tile = null;
    }
}
