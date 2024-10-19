package backend.academy.console;

import backend.academy.substance.Cell;
import backend.academy.substance.Coordinate;
import backend.academy.substance.Maze;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsoleRenderer {
    private static final Map<Cell.Type, String> CELLS = new HashMap<>();

    static {
        CELLS.put(Cell.Type.WALL, "⬛");
        CELLS.put(Cell.Type.PASSAGE, "⬜");
        CELLS.put(Cell.Type.COIN, "🪙");
        CELLS.put(Cell.Type.SAND, "🟧");
    }

    public String render(Maze maze) {
        StringBuilder sb = new StringBuilder();

        for (int row = maze.getHeight() - 1; row >= 0; row--) {
            sb.append(row).append(' ');
            for (int col = 0; col < maze.getWidth(); col++) {
                sb.append(renderCell(maze.getGrid()[row][col]));
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public String renderWithPath(Maze maze, List<Coordinate> path, Coordinate start, Coordinate end) {
        StringBuilder sb = new StringBuilder();
        String[][] visualMaze = new String[maze.getHeight()][maze.getWidth()];

        for (int row = 0; row < maze.getHeight(); row++) {
            for (int col = 0; col < maze.getWidth(); col++) {
                visualMaze[row][col] = renderCell(maze.getGrid()[row][col]);
            }
        }

        visualMaze[start.row()][start.col()] = "🅰️";
        visualMaze[end.row()][end.col()] = "\uD83C\uDD71\uFE0F";

        for (Coordinate coordinate : path) {
            if (!(coordinate.equals(start) || coordinate.equals(end))) {
                if (maze.getCell(coordinate.row(), coordinate.col()).getType() == Cell.Type.COIN) {
                    visualMaze[coordinate.row()][coordinate.col()] = "🟢";
                } else if (maze.getCell(coordinate.row(), coordinate.col()).getType() == Cell.Type.SAND) {
                    visualMaze[coordinate.row()][coordinate.col()] = "🔶";
                } else {
                    visualMaze[coordinate.row()][coordinate.col()] = "🟩";
                }
            }
        }

        for (int row = maze.getHeight() - 1; row >= 0; row--) {
            sb.append(row).append(' ');
            sb.append(String.join("", visualMaze[row])).append('\n');
        }

        return sb.toString();
    }

    private String renderCell(Cell cell) {
        return CELLS.getOrDefault(cell.getType(), "⬜");
    }
}
