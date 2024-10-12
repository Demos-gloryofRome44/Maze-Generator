package backend.academy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IdealGenerator {
    public Maze generate(int height, int width) {
        Maze maze = new Maze(height, width);

        // Инициализация начальной точки
        Random random = new Random();
        int startRow = random.nextInt(height);
        int startCol = random.nextInt(width);

        maze.setCell(startRow, startCol, Cell.Type.PASSAGE);

        List<Cell> walls = new ArrayList<>();
        addWalls(maze, walls, startRow, startCol);

        while (!walls.isEmpty()) {
            int index = random.nextInt(walls.size());
            Cell wall = walls.remove(index);
            if (isWallValid(maze, wall)) {
                maze.setCell(wall.getRow(), wall.getCol(), Cell.Type.PASSAGE);
                addWalls(maze, walls, wall.getRow(), wall.getCol());
            }
        }

        return maze;
    }
}
