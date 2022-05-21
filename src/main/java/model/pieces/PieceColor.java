package model.pieces;

public enum PieceColor {
    White(1),
    Black(0);

    private final int value;

    PieceColor(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}
