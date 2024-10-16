package backend.academy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class BFSSolverNormal implements Solver {

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

        Queue<Coordinate> queue = new LinkedList<>();
        Map<Coordinate, Coordinate> prev = new HashMap<>();
        boolean[][] visited = new boolean[maze.getHeight()][maze.getWidth()];

        queue.offer(newStart);
        visited[newStart.row()][newStart.col()] = true;

        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();

            if (current.equals(newEnd)) {
                return buildPath(prev, newEnd);
            }

            List<Coordinate> neighbors = UtiliteSolver.getNeighbors(maze, current);
            for (Coordinate neighbor : neighbors) {
                if (!visited[neighbor.row()][neighbor.col()]) {
                    queue.offer(neighbor);
                    visited[neighbor.row()][neighbor.col()] = true;
                    prev.put(neighbor, current);
                }
            }
        }

        return Collections.emptyList();
    }

    /**
     * Восстанавливает путь от конечной точки до начальной на основе карты предшествующих координат.
     *
     * @param prev Карта предшествующих координат.
     * @param end  Конечная точка.
     * @return Список координат, представляющих путь от начальной до конечной точки.
     */
    private List<Coordinate> buildPath(Map<Coordinate, Coordinate> prev, Coordinate end) {
        List<Coordinate> path = new ArrayList<>();
        Coordinate at = end;

        while (at != null) {
            path.add(at);
            at = prev.get(at);
        }

        Collections.reverse(path);
        return path;
    }
}
