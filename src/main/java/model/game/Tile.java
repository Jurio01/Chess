package model.game;

import model.pieces.Piece;

public class Tile {
    private final String color; //important for chess 960 bishop setUp
    private final int row;
    private final int column;
    private boolean selected = false;
    private Piece piece;
    private int screenX;
    private int ScreenY;

    public Tile(String color, int row, int column) {
        this.color = color;
        this.row = row;
        this.column = column;
    }


    public void select(){
        this.selected = true;
    }
    public void unselect(){
        this.selected = false;
    }

    public boolean isOccupied(){
        return piece != null;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public boolean isSelected() {
        return selected;
    }

    public Piece getFigure() {
        return piece;
    }

    public void setPiece(Piece piece){
        this.piece = piece;
    }



}
