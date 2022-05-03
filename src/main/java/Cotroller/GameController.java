package Cotroller;

import Model.Game.Chess960;
import Model.Game.Classic;
import View.Board;

public class GameController {
    Classic classic;
    Chess960 chess960;
    Board board;
    public GameController(Classic classic){
        this.classic = classic;
    }
    public GameController(Chess960 chess960){
        this.chess960 = chess960;
    }

}
