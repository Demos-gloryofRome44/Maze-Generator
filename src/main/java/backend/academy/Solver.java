package backend.academy;

public interface Solver {
    List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end);
}
