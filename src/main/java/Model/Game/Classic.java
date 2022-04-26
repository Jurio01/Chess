package Model.Game;

import Model.Figures.*;

import java.util.ArrayList;

public class Classic {
    protected ArrayList<Tile> tiles;
    protected Clock clock;
    protected ArrayList<Figure> whiteFigures;
    protected ArrayList<Figure> blackFigures;

    public void start() {
        boardSetUp();
        clock.start();
    }

    public void end() {

    }

    public void select() {

    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void boardSetUp() {
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                Tile tile;
                if ((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1)) {
                    tile = new Tile("black", j, i);
                } else {
                    tile = new Tile("white", j, i);
                }
                tiles.add(tile);
            }
        }
    }

    public void figureSetUp() {
        for (int i = 0; i < 32; i++) {
            Figure figure;
            int color;
            if (i % 2 == 0) {
                color = 1;
            } else {
                color = 0;
            }
            if (i < 16) {
                for (Tile tile : this.tiles) {
                    if ((tile.getRow() == 2 && color == 1 && !tile.isOccupied()) || tile.getRow() == 7 && color == 0 && !tile.isOccupied()) {
                        figure = new Pawn(tile, color);
                        if (color == 1) {
                            whiteFigures.add(figure);
                        } else {
                            blackFigures.add(figure);
                        }
                    }
                }
            }
            if (i < 20 && i > 15) {
                for (Tile tile : this.tiles) {
                    if ((tile.getRow() == 1 && (tile.getColum() == 1 || tile.getColum() == 8) && color == 1 && !tile.isOccupied()) || tile.getRow() == 8 && (tile.getColum() == 1 || tile.getColum() == 8) && color == 0 && !tile.isOccupied()) {
                        figure = new Rook(tile, color);
                        if (color == 1) {
                            whiteFigures.add(figure);
                        } else {
                            blackFigures.add(figure);
                        }
                    }
                }
            }
            if (i < 24 && i > 19) {
                for (Tile tile : this.tiles) {
                    if ((tile.getRow() == 1 && (tile.getColum() == 2 || tile.getColum() == 7) && color == 1 && !tile.isOccupied()) || tile.getRow() == 8 && (tile.getColum() == 2 || tile.getColum() == 7) && color == 0 && !tile.isOccupied()) {
                        figure = new Knight(tile, color);
                        if (color == 1){ whiteFigures.add(figure);}
                        else { blackFigures.add(figure);}
                    }
                }
            }
            if (i < 28 && i > 23){
                for (Tile tile : this.tiles) {
                    if ((tile.getRow() == 1 && (tile.getColum() == 3 || tile.getColum() == 6) && color == 1 && !tile.isOccupied()) || tile.getRow() == 8 && (tile.getColum() == 3 || tile.getColum() == 6) && color == 0 && !tile.isOccupied()) {
                        figure = new Bishop(tile, color);
                        if (color == 1){ whiteFigures.add(figure);}
                        else { blackFigures.add(figure);}
                    }
                }
            }
        }
    }
}