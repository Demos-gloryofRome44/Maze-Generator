package backend.academy;

import java.util.List;

public class ConsoleRenderer {
    public String render(Maze maze) {
        StringBuilder sb = new StringBuilder();
        for (int row = maze.getHeight() - 1; row >= 0; row--) {
            for (int col = 0; col < maze.getWidth(); col++) {
                sb.append(maze.getGrid()[row][col].getType() == Cell.Type.WALL ? "⬛" : "⬜");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String renderWithPath(Maze maze, List<Coordinate> path, Coordinate start, Coordinate end) {
        StringBuilder sb = new StringBuilder();
        String[][] visualMaze = new String[maze.getHeight()][maze.getWidth()];

        for (int row = 0; row < maze.getHeight(); row++) {
            for (int col = 0; col < maze.getWidth(); col++) {
                visualMaze[row][col] = maze.getGrid()[row][col].getType() == Cell.Type.WALL ? "⬛" : "⬜";
            }
        }

        visualMaze[start.row()][start.col()] = "🅰️";
        visualMaze[end.row()][end.col()] = "\uD83C\uDD71\uFE0F";

        for (Coordinate coordinate : path) {
            if (!(coordinate.equals(start) || coordinate.equals(end))) {
                visualMaze[coordinate.row()][coordinate.col()] = "🟩";
            }
        }

        for (int row = maze.getHeight() - 1; row >= 0; row--) {
            sb.append(String.join("", visualMaze[row])).append("\n");
        }

        return sb.toString();
    }
}
