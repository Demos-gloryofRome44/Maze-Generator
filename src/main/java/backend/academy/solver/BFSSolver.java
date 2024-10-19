package backend.academy.solver;

import backend.academy.substance.Cell;
import backend.academy.substance.Coordinate;
import backend.academy.substance.Maze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class BFSSolver implements Solver {
    private static final int WEIGHT_COIN = 0;
    private static final int WEIGHT_PASSAGE = 2;
    private static final int WEIGHT_SAND = 4;

    /**
     * Находит путь в лабиринте от начальной до конечной точки.
     *
     * @param maze Лабиринт, в котором нужно найти путь.
     * @param newStart Начальная точка.
     * @param newEnd Конечная точка.
     * @return Список координат, представляющий найденный путь, или пустой список, если путь не найден.
     * @throws IllegalArgumentException Если лабиринт равен null.
     */
    @Override
    public List<Coordinate> solve(Maze maze, Coordinate newStart, Coordinate newEnd) {
        if (maze == null) {
            throw new IllegalArgumentException("Maze cannot be null.");
        }

        // Приоритетная очередь для хранения клеток с их весами
        PriorityQueue<WeightedCoordinate> queue = new PriorityQueue<>(Comparator.comparingInt(wc -> wc.weight));
        Map<Coordinate, Coordinate> prev = new HashMap<>();
        boolean[][] visited = new boolean[maze.getHeight()][maze.getWidth()];

        queue.offer(new WeightedCoordinate(newStart, 0));
        visited[newStart.row()][newStart.col()] = true;

        while (!queue.isEmpty()) {
            WeightedCoordinate current = queue.poll(); // Извлечение клетки с наименьшим весом

            if (current.coordinate.equals(newEnd)) {
                return buildPath(prev, newEnd);
            }

            List<Coordinate> neighbors = UtiliteSolver.getNeighbors(maze, current.coordinate);
            for (Coordinate neighbor : neighbors) {
                if (!visited[neighbor.row()][neighbor.col()]) {
                    visited[neighbor.row()][neighbor.col()] = true;
                    prev.put(neighbor, current.coordinate);

                    int weight = getWeight(maze.getCell(neighbor.row(), neighbor.col()));
                    queue.offer(new WeightedCoordinate(neighbor, weight));
                }
            }
        }

        return Collections.emptyList();
    }

   /**
    * Получает вес для данной клетки в зависимости от её типа.
    *
    * @param cell Клетка, для которой нужно получить вес.
    * @return Вес клетки.
    */
    private int getWeight(Cell cell) {
        switch (cell.getType()) {
            case COIN:
                return WEIGHT_COIN;
            case PASSAGE:
                return WEIGHT_PASSAGE;
            case SAND:
                return WEIGHT_SAND;
            default:
                return Integer.MAX_VALUE;
        }
    }

    /**
     * Восстанавливает путь от конечной точки к начальной на основе предшествующих клеток.
     *
     * @param prev Словарь предшествующих клеток.
     * @param end Конечная точка.
     * @return Список координат, представляющий путь от начальной до конечной точки.
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

    /**
     * Хранение координаты клетки и её вес.
     */
    private static class WeightedCoordinate {
        Coordinate coordinate;
        int weight;

        WeightedCoordinate(Coordinate coordinate, int weight) {
            this.coordinate = coordinate;
            this.weight = weight;
        }
    }
}
