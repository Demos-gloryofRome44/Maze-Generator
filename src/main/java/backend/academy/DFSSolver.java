package backend.academy;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class DFSSolver implements Solver {

    /**
        * Решает задачу нахождения пути в лабиринте от заданной начальной точки до конечной.
        *
        * @param maze      Лабиринт, в котором необходимо найти путь.
        * @param newStart  Координаты начальной точки.
        * @param newEnd    Координаты конечной точки.
        * @return Список координат, представляющих путь от начальной точки до конечной,
     *         или пустой список, если путь не найден.
        * @throws IllegalArgumentException Если лабиринт, начальная или конечная точка равны null.
        */
    @Override
    public List<Coordinate> solve(Maze maze, Coordinate newStart, Coordinate newEnd) {
        if (maze == null) {
            throw new IllegalArgumentException("Maze cannot be null.");
        }

        Deque<Coordinate> stack = new ArrayDeque<>();
        List<Coordinate> path = new ArrayList<>();
        boolean[][] visited = new boolean[maze.getHeight()][maze.getWidth()];

        stack.push(newStart);

        while (!stack.isEmpty()) {
            Coordinate current = stack.pop();

            if (current.equals(newEnd)) {
                path.add(current);
                return path;
            }

            visited[current.row()][current.col()] = true;
            path.add(current);

            List<Coordinate> neighbors = UtiliteSolver.getNeighbors(maze, current);

            for (Coordinate neighbor : neighbors) {
                if (!visited[neighbor.row()][neighbor.col()]) {
                    stack.push(neighbor);
                }
            }
        }
        return Collections.emptyList();
    }
}
