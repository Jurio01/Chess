package PieceTests;

import model.game.Classic;
import model.game.Tile;
import model.pieces.King;
import model.pieces.Piece;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class KingTest {
    Tile tile;
    Piece king;
    List<Tile> tiles;
    Classic game;
    @BeforeEach
    public void createNewTilesAndKing(){
        game = new Classic(null);
        game.boardSetUp();
        this.tiles = game.getTiles();
        this.tile = tiles.get(0);
        king = new King(this.tile,1,game);
    }

    @Test
    public void kingConstructor_kingIsNotInCheckAndOnTile11(){
        Assertions.assertEquals(false,king.isCheck());
        Assertions.assertEquals(1,king.getTile().getColumn());
        Assertions.assertEquals(1,king.getTile().getRow());
    }

    @Test
    public void kingCanMove_KingCanMoveTo3Tiles_listSizeIs3_passed(){
        king.canMove();
        Assertions.assertEquals(3,king.getPossibleMoves().size());
    }
}
