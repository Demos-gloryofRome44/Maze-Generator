package backend.academy.console;

import backend.academy.substance.Coordinate;
import backend.academy.substance.Maze;
import java.util.List;

public interface Render {
    String render(Maze maze);

    String render(Maze maze, List<Coordinate> path);
}
