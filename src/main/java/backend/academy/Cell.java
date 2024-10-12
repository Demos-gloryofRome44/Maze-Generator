package backend.academy;

public class Cell {
    private final int row;
    private final int col;
    private Type type;

    public enum Type { WALL, PASSAGE }

    public Cell(int row, int col, Type type) {
        this.row = row;
        this.col = col;
        this.type = type;
    }


}
