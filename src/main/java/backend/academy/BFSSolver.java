package backend.academy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class BFSSolver implements Solver {

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        if (maze == null || start == null || end == null) {
            throw new IllegalArgumentException("Maze, start, and end cannot be null.");
        }

        WallHandler wallHandler = new WallHandler(maze);
        start = wallHandler.checkAndModifyCellType(start, "A");
        end = wallHandler.checkAndModifyCellType(end, "B");

        Queue<Coordinate> queue = new LinkedList<>();
        Map<Coordinate, Coordinate> prev = new HashMap<>();
        boolean[][] visited = new boolean[maze.getHeight()][maze.getWidth()];

        queue.offer(start);
        visited[start.row()][start.col()] = true;

        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();

            if (current.equals(end)) {
                return buildPath(prev, end);
            }

            List<Coordinate> neighbors = getNeighbors(maze, current);
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

    private List<Coordinate> getNeighbors(Maze maze, Coordinate coord) {
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
