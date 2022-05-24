package model.game;

import cotroller.GameController;
import model.pieces.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Classic {
    protected List<Tile> tiles;
    protected Clock clock;
    protected List<Piece> whitePieces;
    protected List<Piece> blackPieces;
    protected King whiteKing;
    protected King blackKing;
    protected GameController controller;
    protected Tile tileWithPiece;
    protected Tile tileToMove;
    protected boolean whiteTurn;
    protected List<Rook> rooks;

    public void start() {
        boardSetUp();
        figureSetUp();
        whiteTurn = true;
    }

    public void end() {
        if (whitePieces.size() + blackPieces.size() == 3){
            if (whitePieces.size() > blackPieces.size()){
                endCheckContains(whitePieces);
            } else {
                endCheckContains(blackPieces);
            }
        }
        for (Piece piece: (whiteTurn) ? whitePieces : blackPieces) {
            piece.canMove();
            Logger.getAnonymousLogger().log(Level.INFO,"Piece is: " + piece.getClass().getSimpleName());
            for (Tile tile : piece.getPossibleMoves()) {
                if (!piece.checkInvalidMove(tile)) {
                    return;
                }
            }
        }
        System.out.println("Game ended");
        if ((whiteTurn) ? whiteKing.isCheck() : blackKing.isCheck()){
            System.out.println((whiteTurn)? "Black wins!" : "White wins!");
            controller.end((whiteTurn) ? "Black wins" : "White wins");
            clock.interrupt();
        }else {
            System.out.println("Stale mate");
            controller.end("Stale mate");
            clock.interrupt();
        }
    }

    public Classic(GameController controller) {
        this.whitePieces = new ArrayList<>();
        this.blackPieces = new ArrayList<>();
        this.controller = controller;
        this.clock = new Clock(controller, this);
        this.rooks = new ArrayList<>();
    }

    public void select(Tile tile) {
        //select empty tile
        if (tile.getPiece() == null){
            if (tileToMove != null){
                tileToMove.unselect();
            }
            tileToMove = tile;
            tileToMove.select();
        }
        //select not empty tile
        if (tile.getPiece() != null){
            //if it is white turn and tile holds white piece, it is the tile with a piece that will move
            if (whiteTurn && tile.getPiece().getColor() == PieceColor.White){
                if (tileWithPiece != null){
                    tileWithPiece.unselect();
                }
                tileWithPiece = tile;
                tileWithPiece.select();
            }
            //if it isn't white turn and tile holds black piece, it is the tile with a piece that will move
            if (!whiteTurn && tile.getPiece().getColor() == PieceColor.Black){
                if (tileWithPiece != null){
                    tileWithPiece.unselect();
                }
                tileWithPiece = tile;
                tileWithPiece.select();
            }
            //if it is white turn and tile holds black piece, it is the tile that the piece will move to
            if (whiteTurn && tile.getPiece().getColor() == PieceColor.Black){
                if (tileToMove != null){
                    tileToMove.unselect();
                }
                tileToMove = tile;
                tileToMove.select();
            }
            //if it isn't white turn and tile holds white piece, it is the tile that the piece will move to
            if (!whiteTurn && tile.getPiece().getColor() == PieceColor.White){
                if (tileToMove != null){
                    tileToMove.unselect();
                }
                tileToMove = tile;
                tileToMove.select();
            }
        }
        //once two tiles are selected, piece will make a move
        if (tileWithPiece != null && tileToMove != null){
            Piece piece = tileWithPiece.getPiece();
            //if piece moves successfully, game checks for check, the turn will reverse and game looks if it is the end

            if (piece.move()){
                check();
                whiteTurn =! whiteTurn;
                end();
                if (!clock.isAlive()){
                    if (whiteTurn){ //clock starts once both sides have made their first move
                        clock.start();
                    }
                }
            }
            System.out.println(whiteTurn);
            tileWithPiece.unselect();
            tileToMove.unselect();
            tileWithPiece = null;
            tileToMove = null;
        }
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void boardSetUp() {
        List<Tile> tempList = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                Tile tile;
                if ((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1)) {
                    tile = new Tile("black", j,i);
                    tempList.add(tile);
                }
                if ((i % 2 == 1 && j % 2 == 0) || (i % 2 == 0 && j % 2 == 1)){
                    tile = new Tile("white", j, i);
                    tempList.add(tile);
                }
            }
        }
        tiles = Collections.unmodifiableList(tempList);
    }

    public void figureSetUp() {
        for (int i = 0; i < 32; i++) {
            Piece piece;
            PieceColor color;
            if (i % 2 == 0) {
                color = PieceColor.White;
            } else {
                color = PieceColor.Black;
            }
            // I couldn't think of a way to make this stuff readable, but it works
            // set up pawns
            if (i < 16) {
                for (Tile tile : this.tiles) {
                    if ((tile.getRow() == 2 && color == PieceColor.White && !tile.isOccupied()) || tile.getRow() == 7 && color == PieceColor.Black && !tile.isOccupied()) {
                        piece = new Pawn(tile, color, this);
                        if (color.getValue() == 1) {
                            whitePieces.add(piece);
                        } else {
                            blackPieces.add(piece);
                        }
                        break;
                    }
                }
            }
            // set up rooks
            if (i < 20 && i > 15) {
                for (Tile tile : this.tiles) {
                    if ((tile.getRow() == 1 && (tile.getColumn() == 1 || tile.getColumn() == 8) && color == PieceColor.White && !tile.isOccupied()) ||
                            tile.getRow() == 8 && (tile.getColumn() == 1 || tile.getColumn() == 8) && color == PieceColor.Black && !tile.isOccupied()) {
                        piece = new Rook(tile, color, this);
                        if (color.getValue() == 1) {
                            whitePieces.add(piece);
                        } else {
                            blackPieces.add(piece);
                        }
                        rooks.add((Rook) piece);
                        break;
                    }
                }
            }
            // set up knights
            if (i < 24 && i > 19) {
                for (Tile tile : this.tiles) {
                    if ((tile.getRow() == 1 && (tile.getColumn() == 2 || tile.getColumn() == 7) && color == PieceColor.White && !tile.isOccupied()) ||
                            tile.getRow() == 8 && (tile.getColumn() == 2 || tile.getColumn() == 7) && color == PieceColor.Black && !tile.isOccupied()) {
                        piece = new Knight(tile, color,this);
                        if (color.getValue() == 1){
                            whitePieces.add(piece);
                        } else {
                            blackPieces.add(piece);
                        }
                        break;
                    }
                }
            }
            // set up bishops
            if (i < 28 && i > 23){
                for (Tile tile : this.tiles) {
                    if ((tile.getRow() == 1 && (tile.getColumn() == 3 || tile.getColumn() == 6) && color == PieceColor.White && !tile.isOccupied()) ||
                            tile.getRow() == 8 && (tile.getColumn() == 3 || tile.getColumn() == 6) && color == PieceColor.Black && !tile.isOccupied()) {
                        piece = new Bishop(tile, color,this);
                        if (color.getValue() == 1){ whitePieces.add(piece);}
                        else { blackPieces.add(piece);}
                        break;
                    }
                }
            }
            // set up queens
            if (i < 30 && i > 27){
                for (Tile tile : this.tiles) {
                    if ((tile.getRow() == 1 &&  tile.getColumn() == 4 && color == PieceColor.White && !tile.isOccupied()) ||
                            tile.getRow() == 8 && tile.getColumn() == 4 && color == PieceColor.Black && !tile.isOccupied()) {
                        piece = new Queen(tile, color,this);
                        if (color.getValue() == 1){ whitePieces.add(piece);}
                        else { blackPieces.add(piece);}
                        break;
                    }
                }
            }
            // set up kings
            if (i > 29){
                for (Tile tile : this.tiles) {
                    if ((tile.getRow() == 1 && tile.getColumn() == 5 && color == PieceColor.White && !tile.isOccupied()) ||
                            tile.getRow() == 8 && tile.getColumn() == 5 && color == PieceColor.Black && !tile.isOccupied()) {
                        piece = new King(tile, color,this);
                        if (color.getValue() == 1){ whitePieces.add(piece); whiteKing = (King) piece;}
                        else { blackPieces.add(piece); blackKing = (King) piece;}
                        break;
                    }
                }
            }
        }
    }
    public List<Piece> getWhiteFigures() {
        return whitePieces;
    }

    public List<Piece> getBlackFigures() {
        return blackPieces;
    }

    public King getWhiteKing(){
        return whiteKing;
    }

    public King getBlackKing(){
        return blackKing;
    }

    public GameController getController() {
        return controller;
    }

    public void check(){
        King king = (whiteTurn) ? whiteKing : blackKing;
        king.unCheck();
        for (Piece piece: (whiteTurn) ? blackPieces : whitePieces){
            piece.canMove();
            for (Tile tile: (piece instanceof Pawn) ? ((Pawn) piece).getKingDangerTiles() :piece.getPossibleMoves()){
                if (tile.isOccupied()){
                    if (tile.getPiece() == king){
                        king.putInCheck();
                        Logger.getAnonymousLogger().log(Level.INFO,"King is in check");
                        Logger.getAnonymousLogger().log(Level.INFO,"Piece is: " + piece.getClass().getSimpleName());
                        return;
                    }
                }
            }
        }
    }

    public List<Rook> getRooks() {
        return rooks;
    }

    public void setWhiteKing(King whiteKing) {
        this.whiteKing = whiteKing;
    }

    public void setBlackKing(King blackKing) {
        this.blackKing = blackKing;
    }

    public void setWhiteTurn(boolean whiteTurn) {
        this.whiteTurn = whiteTurn;
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    private void endCheckContains(List<Piece> list){
        for (Piece piece: list){
            if (piece instanceof Bishop || piece instanceof Knight){
                System.out.println("Game ended");
                System.out.println("Stale mate");
                clock.interrupt();
                controller.end("Stale mate");
            }
        }
    }

    public Clock getClock() {
        return clock;
    }

    public void setWhitePieces(List<Piece> whitePieces) {
        this.whitePieces = whitePieces;
    }

    public void setBlackPieces(List<Piece> blackPieces) {
        this.blackPieces = blackPieces;
    }
}
