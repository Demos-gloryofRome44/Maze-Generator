package backend.academy;

public class ConsoleRenderer {
    public String render(Maze maze) {
        StringBuilder sb = new StringBuilder();
        for (int row = maze.getHeight() - 1; row >= 0; row--) {
            for (int col = 0; col < maze.getWidth(); col++) {
                sb.append(maze.getGrid()[row][col].getType() == Cell.Type.WALL ? "█" : " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
