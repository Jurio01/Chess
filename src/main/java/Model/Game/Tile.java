package Model.Game;

import Model.Figures.Figure;

public class Tile {
    private String color; //important for chess 960 bishop setUp
    private int line;
    private String row;
    private Figure figure;
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

}
