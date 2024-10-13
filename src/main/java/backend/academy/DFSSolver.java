package backend.academy;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class DFSSolver implements Solver {

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate newStart, Coordinate newEnd) {
        if (maze == null || newStart == null || newEnd == null) {
            throw new IllegalArgumentException("Maze, start, and end cannot be null.");
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

            List<Coordinate> neighbors = getNeighbors(maze, current);

            for (Coordinate neighbor : neighbors) {
                if (!visited[neighbor.row()][neighbor.col()]) {
                    stack.push(neighbor);
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
}
