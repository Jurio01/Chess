package GameTest;

import model.game.Classic;
import model.game.Tile;
import model.pieces.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class ClassicTest {
    Classic game = new Classic(null);;
    List<Tile> tiles;
    List<Piece> pieces;
    public void createNewGame(){
        game.boardSetUp();
        tiles = game.getTiles();
        game.figureSetUp();
        pieces = game.getWhiteFigures();
        game.setWhiteTurn(true);
    }

    @Test
    public void classicGame_tileConstruction_passed(){
        createNewGame();
        int count = 0;
        while (tiles.size() > count){
            for (int i = 1; i<9;i++){
                for (int j = 1; j<9;j++){
                    Assertions.assertEquals(j,tiles.get(count).getRow());
                    Assertions.assertEquals(i,tiles.get(count).getColumn());
                    count += 1;
                }
            }
        }
    }

    @Test
    public void classicGame_figureConstruction_passed(){
        createNewGame();
        for (Piece piece: pieces){
            if (piece instanceof Pawn){
                Assertions.assertEquals(2,piece.getTile().getRow());
            } else {
                Assertions.assertEquals(1,piece.getTile().getRow());
            }
        }
    }

    @Test
    public void classicGameCheck_whiteKingIsInCheckByMockedPiece_passed(){
        Piece piece = Mockito.mock(Piece.class);
        ArrayList<Tile> tiles = new ArrayList<>();
        Tile tile = Mockito.mock(Tile.class);
        tiles.add(tile);
        King king = new King(tile,1,game);
        game.getWhiteFigures().add(king);
        game.setWhiteKing(king);
        Mockito.when(tile.getPiece()).thenReturn(king);
        Mockito.when(tile.isOccupied()).thenReturn(true);
        Mockito.when(piece.getColor()).thenReturn(0);
        Mockito.when(piece.getPossibleMoves()).thenReturn(tiles);
        game.getBlackFigures().add(piece);
        game.setWhiteTurn(true);
        game.check();
        Assertions.assertTrue(king.isCheck());
    }
    @Test
    public void ProcessTest_4pawnMoves_5thOneTakesEnemyPawn_passed(){
        Piece pawn = null;
        createNewGame();
        Assertions.assertEquals(16,game.getWhiteFigures().size());
        for (Tile tile: tiles){
            if (tile.getRow() == 2 && tile.getColumn() == 5){
                game.select(tile);
            }
            if (tile.getRow() == 4 && tile.getColumn() == 5){
                game.select(tile);
            }
        }

        Assertions.assertEquals(16,game.getBlackFigures().size());
        for (Tile tile: tiles){
            if (tile.getRow() == 7 && tile.getColumn() == 5){
                game.select(tile);
            }
            if (tile.getRow() == 5 && tile.getColumn() == 5){
                game.select(tile);
            }
        }

        Assertions.assertEquals(16,game.getWhiteFigures().size());
        for (Tile tile: tiles){
            if (tile.getRow() == 2 && tile.getColumn() == 4){
                game.select(tile);
            }
            if (tile.getRow() == 3 && tile.getColumn() == 4){
                game.select(tile);
            }
        }

        Assertions.assertEquals(16,game.getBlackFigures().size());
        for (Tile tile: tiles){
            if (tile.getRow() == 7 && tile.getColumn() == 6){
                game.select(tile);
            }
            if (tile.getRow() == 5 && tile.getColumn() == 6){
                game.select(tile);
            }
        }

        Assertions.assertEquals(16,game.getWhiteFigures().size());
        for (Tile tile: tiles){
            if (tile.getRow() == 4 && tile.getColumn() == 5){
                game.select(tile);
                Assertions.assertTrue(tile.getPiece() instanceof Pawn);
                Assertions.assertEquals(1,tile.getPiece().getColor());
                pawn = tile.getPiece();
            }
            if (tile.getRow() == 5 && tile.getColumn() == 6){
                game.select(tile);
                Assertions.assertTrue(tile.getPiece() instanceof Pawn);
                Assertions.assertEquals(1,tile.getPiece().getColor());
                Assertions.assertEquals(pawn,tile.getPiece());
            }
        }
        Assertions.assertEquals(15,game.getBlackFigures().size());
    }

    @Test
    public void ProcessTest_KingIsInCheckAfterBishopMove_passed(){
        createNewGame();
        Tile bishopTile = null;
        for (Tile tile: tiles){
            if (tile.getRow() == 2 && tile.getColumn() == 5){
                Assertions.assertTrue(tile.getPiece() instanceof Pawn);
                game.select(tile);
            }
            if (tile.getRow() == 4 && tile.getColumn() == 5){
                game.select(tile);
            }
        }
        for (Tile tile: tiles){
            if (tile.getRow() == 7 && tile.getColumn() == 5){
                Assertions.assertTrue(tile.getPiece() instanceof Pawn);
                game.select(tile);
            }
            if (tile.getRow() == 5 && tile.getColumn() == 5){
                game.select(tile);
            }
        }
        for (Tile tile: tiles){
            if (tile.getRow() == 2 && tile.getColumn() == 4){
                Assertions.assertTrue(tile.getPiece() instanceof Pawn);
                game.select(tile);
            }
            if (tile.getRow() == 3 && tile.getColumn() == 4){
                game.select(tile);
            }
        }
        for (Tile tile: tiles){
            if (tile.getRow() == 8 && tile.getColumn() == 6){
                Assertions.assertTrue(tile.getPiece() instanceof Bishop);
                game.select(tile);
            }
            if (tile.getRow() == 4 && tile.getColumn() == 2){
                game.select(tile);
                bishopTile = tile;
            }
        }
        Assertions.assertTrue(game.getWhiteKing().isCheck());
        Assertions.assertTrue(bishopTile.getPiece() instanceof Bishop);
    }

    @Test
    public void processTest_pawnTakesOtherPawnByEnPassant_passed(){
        createNewGame();
        Tile enPassantTile = null;
        Piece pawn = null;
        for (Tile tile: tiles){
            if (tile.getRow() == 2 && tile.getColumn() == 5){
                game.select(tile);
            }
            if (tile.getRow() == 4 && tile.getColumn() == 5){
                game.select(tile);
            }
        }
        for (Tile tile: tiles){
            if (tile.getRow() == 7 && tile.getColumn() == 1){
                game.select(tile);
            }
            if (tile.getRow() == 5 && tile.getColumn() == 1){
                game.select(tile);
            }
        }
        for (Tile tile: tiles){
            if (tile.getRow() == 4 && tile.getColumn() == 5){
                game.select(tile);
            }
            if (tile.getRow() == 5 && tile.getColumn() == 5){
                game.select(tile);
            }
        }
        for (Tile tile: tiles){
            if (tile.getRow() == 7 && tile.getColumn() == 4){
                game.select(tile);
            }
            if (tile.getRow() == 5 && tile.getColumn() == 4){
                game.select(tile);
            }
        }
        Assertions.assertEquals(16,game.getBlackFigures().size());
        for (Tile tile: tiles) {
            if (tile.getRow() == 5 && tile.getColumn() == 5) {
                game.select(tile);
                pawn = tile.getPiece();
            }
            if (tile.isEnPassantMove()) {
                enPassantTile = tile;
            }
        }
        Assertions.assertTrue(enPassantTile.isEnPassantMove());
        game.select(enPassantTile);
        Assertions.assertEquals(enPassantTile,pawn.getTile());
        Assertions.assertTrue(pawn instanceof Pawn);
        Assertions.assertEquals(15,game.getBlackFigures().size());
    }

    @Test
    public void ProcessTest_KingCastlesWhenHeCan_passed(){
        Tile kingTile = null;
        createNewGame();
        game.setWhiteTurn(true);
        for (Tile tile: tiles){
            if (tile.getRow() == 2 && tile.getColumn() == 5){
                Assertions.assertTrue(tile.getPiece() instanceof Pawn);
                game.select(tile);
            }
            if (tile.getRow() == 4 && tile.getColumn() == 5){
                game.select(tile);
            }
        }
        Assertions.assertFalse(game.isWhiteTurn());
        game.setWhiteTurn(true);
        for (Tile tile: tiles){
            if (tile.getRow() == 1 && tile.getColumn() == 6){
                Assertions.assertTrue(tile.getPiece() instanceof Bishop);
                game.select(tile);
            }
            if (tile.getRow() == 2 && tile.getColumn() == 5){
                game.select(tile);
            }
        }
        Assertions.assertFalse(game.isWhiteTurn());
        game.setWhiteTurn(true);
        for (Tile tile: tiles){
            if (tile.getRow() == 1 && tile.getColumn() == 7){
                Assertions.assertTrue(tile.getPiece() instanceof Knight);
                game.select(tile);
            }
            if (tile.getRow() == 3 && tile.getColumn() == 6){
                game.select(tile);
            }
        }
        Assertions.assertFalse(game.isWhiteTurn());
        game.setWhiteTurn(true);
        for (Tile tile: tiles){
            if (tile.getRow() == 1 && tile.getColumn() == 5){
                Assertions.assertTrue(tile.getPiece() instanceof King);
                game.select(tile);
            }
            if (tile.getRow() == 1 && tile.getColumn() == 7){
                game.select(tile);
                kingTile = tile;
            }
        }
        Assertions.assertEquals(kingTile,game.getWhiteKing().getTile());
        for (Tile tile: tiles){
            if (tile.getRow() == 1 && tile.getColumn() == 6){
                Assertions.assertTrue(tile.getPiece() instanceof Rook);
                break;
            }
        }
    }
}
