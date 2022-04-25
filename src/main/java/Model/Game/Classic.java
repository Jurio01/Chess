package Model.Game;

import java.util.ArrayList;

public class Classic {
    private static ArrayList<Tile> tiles;
    private Clock clock;

    public void start(){
        clock.start();
    }

    public void end(){

    }
    public void select(){

    }

    public static ArrayList<Tile> getTiles() {
        return tiles;
    }
}
