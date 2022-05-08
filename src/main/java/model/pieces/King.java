package model.pieces;

import model.game.Classic;
import model.game.Tile;

import java.util.ArrayList;

public class King extends Piece {
    private boolean firstMove; //for implementation of castling

    public King(Tile tile, int color, Classic game) {
        super(tile, color, game);
        firstMove = true;
    }


    @Override
    public void move() {
        for (Tile tile : game.getTiles()) {
            if (tile.isSelected() && tile != this.tile) {
                if (tile.getFigure() == null) {
                    this.tile = tile;
                    firstMove = false;
                } else {
                    if (tile.getFigure().canBeTaken(color)) {
                        take(tile);
                        this.tile = tile;
                        tile.setPiece(this);
                    }
                }
            }
        }
    }

    @Override
    public boolean canBeTaken(int color) {
        return this.color != color;
    }

    @Override
    public void canMove() {
        int row = tile.getRow();
        int column = tile.getColumn();
        ArrayList<Tile> tiles = game.getTiles();
        for (Tile tile : tiles) {
            if (tile.getRow() == row) {
                if (tile.getColumn() == column + 1 || tile.getColumn() == column - 1) {
                    possibleMoves.add(tile);
                }
            }
            if (tile.getRow() == row - 1 || tile.getRow() == row + 1) {
                if (tile.getColumn() == column || tile.getColumn() == column + 1 || tile.getColumn() == column - 1) {
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
     *
     * @param rook and looks if this position is occupied by a rook that was not yet moved.
     */
    public void castle(Rook rook) {
        if (firstMove && rook.isFirstMove()) {
            if (rook.tile.getColumn() == 8) {
                if (this.color == 1) {
                    for (Tile tile : game.getTiles()) {
                        if (tile.getColumn() == 7 && tile.getRow() == 1) {
                            possibleMoves.add(tile);
                            break;
                        }
                    }
                }
                if (this.color == 0) {
                    for (Tile tile : game.getTiles()) {
                        if (tile.getColumn() == 7 && tile.getRow() == 8) {
                            possibleMoves.add(tile);
                            break;
                        }
                    }
                }
            }
            if (rook.tile.getColumn() == 1) {
                if (this.color == 1) {
                    for (Tile tile : game.getTiles()) {
                        if (tile.getColumn() == 2 && tile.getRow() == 1) {
                            possibleMoves.add(tile);
                            break;
                        }
                    }
                }
                if (this.color == 0) {
                    for (Tile tile : game.getTiles()) {
                        if (tile.getColumn() == 2 && tile.getRow() == 8) {
                            possibleMoves.add(tile);
                            break;
                        }
                    }
                }
            }
        }
    }
}
