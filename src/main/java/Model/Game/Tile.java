package Model.Game;

import Model.Figures.Figure;

public class Tile {
    private final String color; //important for chess 960 bishop setUp
    private final int row;
    private final int colum;
    private boolean selected = false;
    private Figure figure;

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
        return figure != null;
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

    public Figure getFigure() {
        return figure;
    }

    public void setFigure(Figure figure){
        this.figure = figure;
    }



}
