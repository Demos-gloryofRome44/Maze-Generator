package backend.academy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IdealGenerator implements Generator {

    /**
     * Генерирует идеальный лабиринт заданных размеров.
     *
     * @param height Высота лабиринта.
     * @param width  Ширина лабиринта.
     * @return Сгенерированный лабиринт.
     */
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

        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                if (maze.getGrid()[r][c].getType() == Cell.Type.PASSAGE) {
                    if (random.nextDouble() < 0.1) {
                        maze.setCell(r, c, Cell.Type.COIN);
                    } else if (random.nextDouble() < 0.1) {
                        maze.setCell(r, c, Cell.Type.SAND);
                    }
                }
            }
        }

        return maze;
    }

    /*
     * Добавляет все возможные стены вокруг заданной клетки в список стен
     */
    private void addWalls(Maze maze, List<Cell> walls, int row, int col) {
        if (row > 0) {
            walls.add(new Cell(row - 1, col, Cell.Type.WALL));
        }
        if (row < maze.getHeight() - 1) {
            walls.add(new Cell(row + 1, col, Cell.Type.WALL));
        }
        if (col > 0) {
            walls.add(new Cell(row, col - 1, Cell.Type.WALL));
        }
        if (col < maze.getWidth() - 1) {
            walls.add(new Cell(row, col + 1, Cell.Type.WALL));
        }
    }

    /*
     * Проверяет проверяет является ли данная стена валидной для удаления
     * из лабиринта путем проверки соседних клеток на наличие проходов .
     */
    private boolean isWallValid(Maze maze, Cell wall) {
        int passageCount = 0;

        if (wall.getRow() > 0 && maze.getGrid()[wall.getRow() - 1][wall.getCol()].getType() == Cell.Type.PASSAGE) {
            passageCount++;
        }
        if (wall.getRow() < maze.getHeight() - 1 && maze.getGrid()[wall.getRow() + 1][wall.getCol()]
            .getType() == Cell.Type.PASSAGE) {
            passageCount++;
        }
        if (wall.getCol() > 0 && maze.getGrid()[wall.getRow()][wall.getCol() - 1].getType() == Cell.Type.PASSAGE) {
            passageCount++;
        }
        if (wall.getCol() < maze.getWidth() - 1 && maze.getGrid()[wall.getRow()][wall.getCol() + 1]
            .getType() == Cell.Type.PASSAGE) {
            passageCount++;
        }

        return passageCount == 1;
    }
}
