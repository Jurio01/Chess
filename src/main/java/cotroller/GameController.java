package cotroller;

import model.game.Chess960;
import model.game.Classic;
import model.game.Tile;
import model.pieces.Pawn;
import view.boardViews.Board;
import view.boardViews.EndScreen;
import view.boardViews.PromotionScreen;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private final Classic classic;
    private final Board board;
    private int columnSelected;
    private int rowSelected;

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
            this.columnSelected = 1;
        }
        if (64< column && column <=128){
            this.columnSelected = 2;
        }
        if (128< column && column <=192){
            this.columnSelected = 3;
        }
        if (192< column && column <=256){
            this.columnSelected = 4;
        }
        if (256< column && column <=320){
            this.columnSelected = 5;
        }
        if (320< column && column <=384){
            this.columnSelected = 6;
        }
        if (384< column && column <= 448){
            this.columnSelected = 7;
        }
        if (448< column && column <= 512){
            this.columnSelected = 8;
        }
        //System.out.println(this.x);
    }
    //again I have a certain system in my tiles, and I'm too lazy to change it so 0 is actually 8

    public void setRowSelected(int rowSelected){
        if (rowSelected <=64){
            this.rowSelected = 8;
        }
        if (64< rowSelected && rowSelected <=128){
            this.rowSelected = 7;
        }
        if (128< rowSelected && rowSelected <=192){
            this.rowSelected = 6;
        }
        if (192< rowSelected && rowSelected <=256){
            this.rowSelected = 5;
        }
        if (256< rowSelected && rowSelected <=320){
            this.rowSelected = 4;
        }
        if (320< rowSelected && rowSelected <=384){
            this.rowSelected = 3;
        }
        if (384< rowSelected && rowSelected <= 448){
            this.rowSelected = 2;
        }
        if (448< rowSelected && rowSelected <= 512){
            this.rowSelected = 1;
        }
        //System.out.println(this.y);
    }

    public void findTile(){
        List<Tile> tiles = classic.getTiles();
        for (Tile tile: tiles){
            if (tile.getColumn() == columnSelected && tile.getRow() == rowSelected){
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

    public void promotionHappened(){
        getGame().check();
        getGame().end();
    }

    public void end(String message){
        EndScreen screen = new EndScreen(this,message);
        screen.init();
    }

    public void clockEnded(){
        getGame().getClock().interrupt();
        boolean side = getGame().getClock().getWhiteTime() <= 0;
        if (side){
            end("White wins!");
        } else {
            end("Black wins!");
        }
    }
}
