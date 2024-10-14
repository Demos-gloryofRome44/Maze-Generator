package backend.academy;

import java.util.ArrayList;
import java.util.List;

public class UtiliteSolver {
    private UtiliteSolver() {
        throw new UnsupportedOperationException("Утилитарный класс не может быть инстанцирован");
    }

    /**
     * Получает список соседних клеток для заданной координаты в лабиринте.
     *
     * @param maze  Лабиринт, в котором нужно найти соседние клетки.
     * @param coord Координаты текущей клетки.
     * @return      Список координат соседних клеток, которые являются проходами.
     */
    public static List<Coordinate> getNeighbors(Maze maze, Coordinate coord) {
        List<Coordinate> neighbors = new ArrayList<>();
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

        for (int[] dir : directions) {
            int newRow = coord.row() + dir[0];
            int newCol = coord.col() + dir[1];

            if (newRow >= 0 && newRow < maze.getHeight() && newCol >= 0 && newCol < maze.getWidth()
                && maze.getCell(newRow, newCol).getType() == Cell.Type.PASSAGE) {
                neighbors.add(new Coordinate(newRow, newCol));
            }
        }
        return neighbors;
    }
}
