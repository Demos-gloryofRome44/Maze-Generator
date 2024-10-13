package backend.academy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class BFSSolver implements Solver {

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate newStart, Coordinate newEnd) {
        if (maze == null || newStart == null || newEnd == null) {
            throw new IllegalArgumentException("Maze, start, and end cannot be null.");
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
