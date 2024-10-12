package backend.academy;

public class Maze {
    private final int height;
    private final int width;
    private final Cell[][] grid;

    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
        this.grid = new Cell[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                grid[row][col] = new Cell(row, col, Cell.Type.WALL);
            }
        }
    }

    public Maze(Cell[][] grid) throws IllegalArgumentException {
        if (grid == null || grid.length == 0) {
            throw new IllegalArgumentException("Grid cannot be null or empty.");
        }
        for (Cell[] row : grid) {
            if (row == null || row.length != grid[0].length) {
                throw new IllegalArgumentException("All rows in the grid must be non-null and the same length.");
            }
            for (Cell cell : row) {
                if (cell == null) {
                    throw new IllegalArgumentException("Cells in the grid cannot be null.");
                }
            }
        }
        this.height = grid.length;
        this.width = grid[0].length;
        this.grid = grid;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Cell[][] getGrid() {
        return grid;
    }
}
