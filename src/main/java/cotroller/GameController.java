package cotroller;

import model.game.Chess960;
import model.game.Classic;
import model.game.Tile;
import model.pieces.Pawn;
import view.boardViews.Board;
import view.boardViews.PromotionScreen;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private final Classic classic;
    private final Board board;
    private int column;
    private int row;

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
    public void setColumn(int column){
        if (column <=64){
            this.column = 1;
        }
        if (64< column && column <=128){
            this.column = 2;
        }
        if (128< column && column <=192){
            this.column = 3;
        }
        if (192< column && column <=256){
            this.column = 4;
        }
        if (256< column && column <=320){
            this.column = 5;
        }
        if (320< column && column <=384){
            this.column = 6;
        }
        if (384< column && column <= 448){
            this.column = 7;
        }
        if (448< column && column <= 512){
            this.column = 8;
        }
        //System.out.println(this.x);
    }
    //again I have a certain system in my tiles, and I'm too lazy to change it so 0 is actually 8

    public void setRow(int row){
        if (row <=64){
            this.row = 8;
        }
        if (64< row && row <=128){
            this.row = 7;
        }
        if (128< row && row <=192){
            this.row = 6;
        }
        if (192< row && row <=256){
            this.row = 5;
        }
        if (256< row && row <=320){
            this.row = 4;
        }
        if (320< row && row <=384){
            this.row = 3;
        }
        if (384< row && row <= 448){
            this.row = 2;
        }
        if (448< row && row <= 512){
            this.row = 1;
        }
        //System.out.println(this.y);
    }

    public void findTile(){
        List<Tile> tiles = classic.getTiles();
        for (Tile tile: tiles){
            if (tile.getColumn() == column && tile.getRow() == row){
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

    public void promote(Pawn pawn){
        PromotionScreen screen = new PromotionScreen(this, pawn);
        screen.init();
    }

}
