package Model.Game;

import Model.Pieces.Piece;

public class Tile {
    private final String color; //important for chess 960 bishop setUp
    private final int row;
    private final int colum;
    private boolean selected = false;
    private Piece piece;

    public Tile(String color, int row, int colum) {
        this.color = color;
        this.row = row;
        this.colum = colum;
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

    public int getColum() {
        return colum;
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
