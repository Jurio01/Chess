package Model.Pieces;
import Model.Game.Tile;
import View.Figures.KingGUI;
import java.util.ArrayList;

public class King extends Piece {
    KingGUI kingGUI;
    private boolean firstMove; //for implementation of castling

    public King(Tile tile, int color) {
        super(tile, color);
        firstMove = true;
        kingGUI = new KingGUI();
    }


    @Override
    public void move() {
        for (Tile tile: game.getTiles()){
            if (tile.isSelected() && tile != this.tile){
                if (tile.getFigure() == null){
                    this.tile = tile;
                    firstMove = false;
                }
                else {
                    if (tile.getFigure().canBeTaken(color)){
                        take(tile);
                        this.tile = tile;
                        tile.setPiece(this);
                    }
                }
            }
        }
    }

    @Override
    public void take(Tile tile) {
        if (this.color == 1){
            game.getBlackFigures().remove(tile.getFigure());
        }
        else {
            game.getWhiteFigures().remove(tile.getFigure());
        }
        tile.setPiece(null);

    }

    @Override
    public boolean canBeTaken(int color) {
        if (this.color == color){
            return false;
        }
        return true;
    }

    @Override
    public void canMove() {
        int row = tile.getRow();
        int colum = tile.getColum();
        ArrayList<Tile> tiles = game.getTiles();
        for (Tile tile: tiles){
            if (tile.getRow() == row){
                if (tile.getColum() == colum + 1 || tile.getColum() == colum - 1){
                    possibleMoves.add(tile);
                }
            }
            if (tile.getRow() == row - 1 || tile.getRow() == row + 1){
                if (tile.getColum() == colum || tile.getColum() == colum + 1 || tile.getColum() == colum - 1){
                    possibleMoves.add(tile);
                }
            }
        }
    }

    @Override
    public void isInCheck() {

    }

    @Override
    public boolean putInCheck() {
        this.check = true;
        return false;
    }

    /**
     * Takes
     * @param rook
     * and looks if this position is occupied by a rook that was not yet moved.
     */
    public void castle(Rook rook){
        if (firstMove && rook.isFirstMove()){
            if (rook.tile.getColum() == 8){
                if (this.color == 1){
                    for (Tile tile: game.getTiles()) {
                        if (tile.getColum() == 7 && tile.getRow() == 1) {
                            possibleMoves.add(tile);
                            break;
                        }
                    }
                }
                if (this.color == 0){
                    for (Tile tile : game.getTiles()){
                        if (tile.getColum() == 7 && tile.getRow() == 8){
                            possibleMoves.add(tile);
                            break;
                        }
                    }
                }
            }
            if (rook.tile.getColum() == 1){
                if (this.color == 1){
                    for (Tile tile: game.getTiles()){
                        if (tile.getColum() == 2 && tile.getRow() == 1){
                            possibleMoves.add(tile);
                            break;
                        }
                    }
                }
                if (this.color == 0){
                    for (Tile tile: game.getTiles()){
                        if (tile.getColum() == 2 && tile.getRow() == 8){
                            possibleMoves.add(tile);
                            break;
                        }
                    }
                }
            }
        }
    }
}
