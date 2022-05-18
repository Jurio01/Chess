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
//    boolean pin;
    Piece yourThreat;
    Piece pining;
    public Piece(Tile tile, int color, Classic game, String imageName) {
        this.tile = tile;
        this.color = color;
        this.game = game;
        this.imageName = imageName;
        tile.setPiece(this);
        this.possibleMoves = new ArrayList<Tile>();
        this.dangerMoves = new ArrayList<Tile>();
        this.checkMoves = new ArrayList<Tile>();
//        this.pin = false;
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
        checkPin();
//        System.out.println("Pin is " + pin);
//        if (pin){
//            checkingMoves();
//        }
        if (king.isCheck()){
            checkingMoves();
            System.out.println("Threat is " + game.getThreats().get(0).threat);
        }
        for (Tile tile: (king.isCheck()) ? checkMoves :possibleMoves){
            if (king.isCheck()){
                System.out.println("Check moves is empty: " + checkMoves.isEmpty());
            }
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
                    if (king.isCheck()){
//                        pin = true;
                        if (yourThreat == null){
                            yourThreat = game.getThreats().get(0);
                            yourThreat.setPining(this);
                        }
                    }
                    this.protect(null);
                    for (Piece piece: (color == 1) ? game.getWhiteFigures() : getGame().getBlackFigures()){
                        piece.canMove();
                    }
//                    if (!pin){
//                        yourThreat = null;
//                    }
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
//                        pin = false;
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
        if (tile.getPiece().getPining() != null){
            tile.getPiece().getPining().setPin(false);
            tile.getPiece().getPining().setYourThreat(null);
            tile.getPiece().setPining(null);
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
        if (piece.getColor() != this .color){
            King king = (color == 1) ? game.getWhiteKing() : game.getBlackKing();
            if (king.isCheck()){
                if (piece == yourThreat){
                    return true;
                }
            }
            if (!king.isCheck()){
                return true;
            }
        }
        return false;
    }
    /**
    Can move is always called once the player selected a tile of his colour with a piece on it. It looks for all
     possible moves that the piece can make
     **/
    public abstract void canMove();

    protected boolean checkValidMove(Tile tile){
        Tile prevTile = this.tile;
        this.tile = tile;
        for (Piece piece: (color == 1) ? getGame().getBlackFigures() : getGame().getWhiteFigures()){
            piece.canMove();
            if ((color == 1) ? game.getWhiteKing().isCheck() : game.getBlackKing().isCheck()){
                this.tile = prevTile;
                return false;
            }
        }
        this.tile = prevTile;
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

    public void checkingMoves(){
        checkMoves.clear();
        ArrayList<Piece> threats = game.getThreats();
        if (threats.size() == 1){
            canMove();
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
        if (yourThreat == null){
           yourThreat = threats.get(0);
        }
        if (threats.isEmpty()){
            if (yourThreat != null){
                canMove();
                ArrayList<Tile> moves = possibleMoves;
                for (Tile threatMove : yourThreat.getDangerMoves()){
                    if (moves.contains(threatMove)){
                        checkMoves.add(threatMove);
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
        }
    }

    public void unPin() {
//        this.pin = false;
    }

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

    public void setPining(Piece pining) {
        this.pining = pining;
    }

    public Piece getPining() {
        return pining;
    }

    public void setPin(boolean pin) {
//        this.pin = pin;
    }

    public void checkPin(){
        if (yourThreat == null){
//            pin = false;
        } else {
//            pin = true;
        }
    }

    public void setYourThreat(Piece yourThreat) {
        this.yourThreat = yourThreat;
    }
}
