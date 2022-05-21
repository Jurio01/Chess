package PieceTests;


import model.game.Classic;
import model.game.Tile;
import model.pieces.King;
import model.pieces.Piece;
import model.pieces.Queen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class QueenTest {
    Tile tile;
    Piece queen;
    List<Tile> tiles;
    Classic game;

    @BeforeEach
    public void createNewTilesAndQueen() {
        game = new Classic(null);
        game.boardSetUp();
        this.tiles = game.getTiles();
        this.tile = tiles.get(0);
        queen = new Queen(this.tile, 1, game);
        queen.getTile().setPiece(queen);
    }
    @Test
    public void queenMoveTest_QueenMovesToTile_passed(){
        queen.canMove();
        tiles.get(63).select();
        King king = Mockito.mock(King.class);
        game.setWhiteKing(king);
        game.setBlackKing(king);
        Mockito.when(king.isCheck()).thenReturn(false);
        queen.move();
        Assertions.assertEquals(tiles.get(63),queen.getTile());
    }
}
