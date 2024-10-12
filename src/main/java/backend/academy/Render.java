package backend.academy;

public interface Render {
    String render(Maze maze);
    String render(Maze maze, List<Coordinate> path);
}
