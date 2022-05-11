package cotroller;

import model.game.Chess960;
import model.game.Classic;
import model.game.Tile;
import view.boardViews.Board;

import java.util.ArrayList;

public class GameController {
    Classic classic;
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
        this.classic = new Chess960(this,params);
        this.board = new Board(this);
        board.init();
    }
    //this is the best solution I could find to get the coords of clicked tiles, please don't even ask why I am starting
    //from 1 and not 0. It's just the way it is, and I'm too lazy to change it.
    public void setX(int x){
        if (x<=64){
            this.x = 1;
        }
        if (64<x && x<=128){
            this.x = 2;
        }
        if (128<x && x<=192){
            this.x = 3;
        }
        if (192<x && x<=256){
            this.x = 4;
        }
        if (256<x && x<=320){
            this.x = 5;
        }
        if (320<x && x<=384){
            this.x = 6;
        }
        if (384<x && x<= 448){
            this.x = 7;
        }
        if (448<x && x<= 512){
            this.x = 8;
        }
        //System.out.println(this.x);
    }
    //again I have a certain system in my tiles, and I'm too lazy to change it so 0 is actually 8
    public void setY(int y){
        if (y<=64){
            this.y = 8;
        }
        if (64<y && y<=128){
            this.y = 7;
        }
        if (128<y && y<=192){
            this.y = 6;
        }
        if (192<y && y<=256){
            this.y = 5;
        }
        if (256<y && y<=320){
            this.y = 4;
        }
        if (320<y && y<=384){
            this.y = 3;
        }
        if (384<y && y<= 448){
            this.y = 2;
        }
        if (448<y && y<= 512){
            this.y = 1;
        }
        //System.out.println(this.y);
    }

    public void findTile(){
        ArrayList<Tile> tiles = classic.getTiles();
        for (Tile tile: tiles){
            if (tile.getColumn() == x && tile.getRow() == y){
                classic.select(tile);
                break;
            }
        }
    }

    public void moved(){

    }

    public Board getBoard() {
        return board;
    }

    public Classic getGame(){
        return this.classic;
    }

}
