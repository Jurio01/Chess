package cotroller;

import model.game.Chess960;
import model.game.Classic;
import model.game.Tile;
import view.Board;


import java.util.ArrayList;

public class GameController {
    Classic classic;
    Chess960 chess960;
    Board board;
    int x;
    int y;

    public GameController(){
        this.classic = new Classic(this);
        classic.start();
        this.board = new Board(this);
        board.init();

    }
    public GameController(ArrayList<Integer> params){
        this.chess960 = new Chess960(this,params);
        this.board = new Board(this);
        board.init();
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void findTile(){
        ArrayList<Tile> tiles = classic.getTiles();
        for (Tile tile: tiles){
            if (tile.getColumn() == x - 1 && tile.getRow() == y - 1){
                tile.select();
                break;
            }
        }
    }






}
