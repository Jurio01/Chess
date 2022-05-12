package model.game;

import cotroller.GameController;
import model.pieces.*;

import java.util.ArrayList;

public class Classic {
    protected ArrayList<Tile> tiles;
    protected Clock clock;
    protected ArrayList<Piece> whitePieces;
    protected ArrayList<Piece> blackPieces;
    protected King whiteKing;
    protected King blackKing;
    protected GameController controller;
    protected Tile tileWithPiece;
    protected Tile tileToMove;
    protected boolean whiteTurn;

    public void start() {
        boardSetUp();
        figureSetUp();
        clock.start();
        whiteTurn = true;
    }

    public void end() {
        if (whiteTurn){

        }
    }

    public Classic(GameController controller) {
        this.tiles = new ArrayList<Tile>();
        this.clock = new Clock();
        this.whitePieces = new ArrayList<Piece>();
        this.blackPieces = new ArrayList<Piece>();
        this.controller = controller;
    }

    public void select(Tile tile) {
        if (tile.getPiece() == null){
            if (tileToMove != null){
                tileToMove.unselect();
            }
            tileToMove = tile;
            tileToMove.select();
            System.out.println("Selected tile to move");
        }
        if (tile.getPiece() != null){
            if (whiteTurn && tile.getPiece().getColor() == 1){
                if (tileWithPiece != null){
                    tileWithPiece.unselect();
                }
                tileWithPiece = tile;
                tileWithPiece.select();
                System.out.println("Selected tile with piece");
            }
            if (!whiteTurn && tile.getPiece().getColor() == 0){
                if (tileWithPiece != null){
                    tileWithPiece.unselect();
                }
                tileWithPiece = tile;
                tileWithPiece.select();
                System.out.println("Selected tile with piece");
            }
            if (whiteTurn && tile.getPiece().getColor() == 0){
                if (tileToMove != null){
                    tileToMove.unselect();
                }
                tileToMove = tile;
                tileToMove.select();
                System.out.println("Selected tile to move");
            }
            if (!whiteTurn && tile.getPiece().getColor() == 1){
                if (tileToMove != null){
                    tileToMove.unselect();
                }
                tileToMove = tile;
                tileToMove.select();
                System.out.println("Selected tile to move");
            }
        }
        if (tileWithPiece != null && tileToMove != null){
            System.out.println("Tried to move");
            Piece piece = tileWithPiece.getPiece();
            if (piece.move()){
                whiteTurn =! whiteTurn;
            }
            piece.getPossibleMoves().clear();
            piece.canMove();
            check();
            tileWithPiece.unselect();
            tileToMove.unselect();
            tileWithPiece = null;
            tileToMove = null;
            System.out.println(whiteTurn);
        }
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void boardSetUp() {
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                Tile tile;
                if ((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1)) {
                    tile = new Tile("black", j, i);
                    tiles.add(tile);
                }
                if ((i % 2 == 1 && j % 2 == 0) || (i % 2 == 0 && j % 2 == 1)){
                    tile = new Tile("white", j, i);
                    tiles.add(tile);
                }

            }
        }
    }

    public void figureSetUp() {
        for (int i = 0; i < 32; i++) {
            Piece piece;
            int color;
            if (i % 2 == 0) {
                color = 1;
            } else {
                color = 0;
            }
            // I couldn't think of a way to make this stuff readable, but it works
            // set up pawns
            if (i < 16) {
                for (Tile tile : this.tiles) {
                    if ((tile.getRow() == 2 && color == 1 && !tile.isOccupied()) || tile.getRow() == 7 && color == 0 && !tile.isOccupied()) {
                        piece = new Pawn(tile, color, this);
                        if (color == 1) {
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
                    if ((tile.getRow() == 1 && (tile.getColumn() == 1 || tile.getColumn() == 8) && color == 1 && !tile.isOccupied()) || tile.getRow() == 8 && (tile.getColumn() == 1 || tile.getColumn() == 8) && color == 0 && !tile.isOccupied()) {
                        piece = new Rook(tile, color, this);
                        if (color == 1) {
                            whitePieces.add(piece);
                        } else {
                            blackPieces.add(piece);
                        }
                        break;
                    }
                }
            }
            // set up knights
            if (i < 24 && i > 19) {
                for (Tile tile : this.tiles) {
                    if ((tile.getRow() == 1 && (tile.getColumn() == 2 || tile.getColumn() == 7) && color == 1 && !tile.isOccupied()) || tile.getRow() == 8 && (tile.getColumn() == 2 || tile.getColumn() == 7) && color == 0 && !tile.isOccupied()) {
                        piece = new Knight(tile, color,this);
                        if (color == 1){
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
                    if ((tile.getRow() == 1 && (tile.getColumn() == 3 || tile.getColumn() == 6) && color == 1 && !tile.isOccupied()) || tile.getRow() == 8 && (tile.getColumn() == 3 || tile.getColumn() == 6) && color == 0 && !tile.isOccupied()) {
                        piece = new Bishop(tile, color,this);
                        if (color == 1){ whitePieces.add(piece);}
                        else { blackPieces.add(piece);}
                        break;
                    }
                }
            }
            // set up queens
            if (i < 30 && i > 27){
                for (Tile tile : this.tiles) {
                    if ((tile.getRow() == 1 &&  tile.getColumn() == 4 && color == 1 && !tile.isOccupied()) || tile.getRow() == 8 && tile.getColumn() == 4 && color == 0 && !tile.isOccupied()) {
                        piece = new Queen(tile, color,this);
                        if (color == 1){ whitePieces.add(piece);}
                        else { blackPieces.add(piece);}
                        break;
                    }
                }
            }
            // set up kings
            if (i > 29){
                for (Tile tile : this.tiles) {
                    if ((tile.getRow() == 1 && tile.getColumn() == 5 && color == 1 && !tile.isOccupied()) || tile.getRow() == 8 && tile.getColumn() == 5 && color == 0 && !tile.isOccupied()) {
                        piece = new King(tile, color,this);
                        if (color == 1){ whitePieces.add(piece); whiteKing = (King) piece;}
                        else { blackPieces.add(piece); blackKing = (King) piece;}
                        break;
                    }
                }
            }
        }
    }
    public ArrayList<Piece> getWhiteFigures() {
        return whitePieces;
    }

    public ArrayList<Piece> getBlackFigures() {
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
        if (whiteTurn){
            for (Piece piece: blackPieces){
                for (Tile tile: piece.getPossibleMoves()){
                    if (tile.isOccupied()){
                        if (tile.getPiece() == whiteKing){
                            whiteKing.putInCheck();
                        }
                    }
                }
            }
        } else {
            for (Piece piece: whitePieces){
                for (Tile tile: piece.getPossibleMoves()){
                    if (tile.isOccupied()){
                        if (tile.getPiece() == blackKing){
                            blackKing.putInCheck();
                        }
                    }
                }
            }
        }
    }
}