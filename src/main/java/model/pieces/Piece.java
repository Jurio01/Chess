package model.pieces;

import model.game.Classic;
import model.game.Tile;

import java.util.ArrayList;

public abstract class Piece {
    Tile tile;
    ArrayList<Tile> possibleMoves;
    boolean check;
    int color;
    String imageName;
    Classic game;
    boolean threat; //to make it easier to find in an array of figures and to make it clear witch piece is threat
                    //to the king so the other player may react accordingly
    ArrayList<Tile> dangerMoves;
    ArrayList<Tile> checkMoves;
    Piece protection;
    public Piece(Tile tile, int color, Classic game, String imageName) {
        this.tile = tile;
        this.color = color;
        this.game = game;
        this.imageName = imageName;
        tile.setPiece(this);
        this.possibleMoves = new ArrayList<Tile>();
        this.dangerMoves = new ArrayList<Tile>();
//        this.checkMoves = new ArrayList<Tile>();
        this.protection = null;
        this.threat = false;
    }

    /**
    Move assigns new value to the position of the figure based on the possible
    moves the figure can make. Move always calls canMove after it updates the figures position and putInCheck
    after it.
     **/
    public boolean move(){
        this.canMove();
        King king = (this.color == 1) ? game.getWhiteKing() : game.getBlackKing();
//        if (king.isCheck()){
//            checkingMoves();
//            System.out.println("Threat is " + game.getThreats().get(0).threat);
//        }
        for (Tile tile: getPossibleMoves()){
//            if (king.isCheck()){
//                System.out.println("Check moves is empty: " + checkMoves.isEmpty());
//            }
            if (tile.isSelected() && tile != this.tile){
//                System.out.println("Tile was found");
                if (tile.getPiece() == null){
                    if (!checkValidMove(tile)){
                        return false;
                    }
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
                    this.protect(null);
                    for (Piece piece: (color == 1) ? game.getWhiteFigures() : getGame().getBlackFigures()){
                        piece.canMove();
                    }
                    return true;
                }
                if (tile.isSelected() && tile != this.tile && tile.isOccupied()){
                    if (canBeTaken(tile.getPiece())){
                        if (!checkValidMove(tile)){
                            return false;
                        }
                        take(tile);
                        this.tile.setPiece(null);
                        this.tile = tile;
                        this.tile.setPiece(this);
                        this.possibleMoves.clear();
//                        System.out.println("Taken piece");
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
        if (tile.getPiece() instanceof Rook){
            game.getRooks().remove((Rook) tile.getPiece());
        }
        game.getThreats().remove(tile.getPiece());
        tile.setPiece(null);
    }
    /**
    * canBeTaken is called always when take is called. It returns True if a piece is of the opposite color and if
    * it can be taken in agreement with chess rules (can't take a piece if it puts your king in check), returns False
    * otherwise.
     *
     * @param piece piece ti be taken*/
    public boolean canBeTaken(Piece piece){
        return piece.color != this.color;
    }
    /**
    Can move is always called once the player selected a tile of his colour with a piece on it. It looks for all
     possible moves that the piece can make
     **/
    public abstract void canMove();

    protected boolean checkValidMove(Tile tile){
        Tile prevTile = this.tile;
        prevTile.setPiece(null);
        this.tile = tile;
        this.tile.setPiece(this);
        if ((color == 1) ? game.getWhiteKing().isCheck() : game.getBlackKing().isCheck()){
            this.tile = prevTile;
            game.check();
            return false;
        }
        this.tile = prevTile;
        this.tile.setPiece(this);
        game.check();
        return true;
    }

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

    public String getImageName() {
        return imageName;
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

//    public void checkingMoves(){
//        checkMoves.clear();
//        ArrayList<Piece> threats = game.getThreats();
//        if (threats.size() == 1){
//            canMove();
//            ArrayList<Tile> moves = possibleMoves;
//            for (Piece threat: threats){
//                for (Tile threatMove : threat.getDangerMoves()){
//                    if (moves.contains(threatMove)){
//                        checkMoves.add(threatMove);
//                    }
//                }
//            }
//            for (Tile tile : moves){
//                if (tile.isOccupied()){
//                    if (tile.getPiece().isThreat()){
//                        checkMoves.add(tile);
//                    }
//                }
//            }
//        }
//    }

    protected void protect(Piece piece) {
        this.protection = piece;
    }

    protected boolean isProtected(){
        return protection != null;
    }

    public ArrayList<Tile> getCheckMoves() {
        return checkMoves;
    }

    public void setThreat(boolean threat) {
        this.threat = threat;
    }

}
