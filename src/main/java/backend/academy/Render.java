package backend.academy;

import java.util.List;

public interface Render {
    String render(Maze maze);

    String render(Maze maze, List<Coordinate> path);
}
