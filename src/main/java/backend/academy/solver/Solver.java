package backend.academy.solver;

import backend.academy.substance.Coordinate;
import backend.academy.substance.Maze;
import java.util.List;

public interface Solver {
    List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end);
}
