package Figures;

import java.util.ArrayList;

public interface Figure {
    /*
    Move is pretty self describing. It assigns new value to the position of the figure based on the possible
    moves the figure can make. Move always calls canMove after it updates the figures position and putInCheck
    after it.
     */
    void move();
    /*
    Take is used after move is called on a position that is occupied by another piece. It calls on another method
    called canBetaken witch returns boolean, see canBeTaken for further description. If canBeTaken return True, take
    removes the other piece standing on the position on which move is being called by setting its position to
    null value.
     */
    void take();
    /*
    canBeTaken is called always when take is called. It returns True if a piece is of the opposite color and if
    it can be taken in agreement with chess rules (can't take a piece if it puts your king in check), returns False
    otherwise.
     */
    boolean canBeTaken();
    /*
    canMove is called always after at the end of the method move or after figure constructor is called. It updates
    the list of possible moves assigned to each figure.
     */
    void canMove();
    /*
    This method is always called every time that the player can play. Checks if the king is in check and if it is
    It looks for all the moves that the player can make that put it out of it from the list of possible moves.
    */
    void isInCheck();
    /*
    Put in check is always called once canMove method is called. If any of the items in the list of possible moves
    are equal to the position of the king it returns True and returns False otherwise.
     */
    boolean putInCheck();
}
