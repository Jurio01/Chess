package Model.Game;

import Model.Figures.Figure;

public class Tile {
    private String color; //important for chess 960 bishop setUp
    private int row;
    private int colum;
    private boolean selected = false;

    public void select(){
        this.selected = true;
    }
    public void unselect(){
        this.selected = false;
    }

    public boolean isOccupied(){
        return true;
    }

    public int getColum() {
        return colum;
    }

    public int getRow() {
        return row;
    }

}
