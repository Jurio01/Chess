package PieceTests;

import model.game.Classic;
import model.game.Tile;
import model.pieces.King;
import model.pieces.Piece;
import model.pieces.PieceColor;
import model.pieces.Rook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
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
        king = new King(this.tile, PieceColor.White,game);
        king.getTile().setPiece(king);
    }

    @Test
    public void kingConstructor_kingIsNotInCheckAndOnTile11(){
        Assertions.assertFalse(king.isCheck());
        Assertions.assertEquals(1,king.getTile().getColumn());
        Assertions.assertEquals(1,king.getTile().getRow());
    }

    @Test
    public void kingCanMove_KingCanMoveTo3Tiles_listSizeIs3_passed(){
        king.canMove();
        Assertions.assertEquals(3,king.getPossibleMoves().size());
    }

    @Test
    public void kingCanMove_kingIsBlockedByMockedRook_kingCanOnlyMoveUp_passed(){
        Rook rook = Mockito.mock(Rook.class);
        game.getBlackFigures().add(rook);
        ArrayList<Tile> tiles = new ArrayList<>();
        for (int i = 8; i<16; i++){
            tiles.add(this.tiles.get(i));
        }
        Mockito.when(rook.getPossibleMoves()).thenReturn(tiles);
        king.canMove();
        Assertions.assertEquals(1,king.getPossibleMoves().size());
        Assertions.assertEquals(1,king.getTile().getRow());
        Assertions.assertEquals(1,king.getTile().getColumn());
        Assertions.assertEquals(2,king.getPossibleMoves().get(0).getRow());
        Assertions.assertEquals(1,king.getPossibleMoves().get(0).getColumn());
    }

}
