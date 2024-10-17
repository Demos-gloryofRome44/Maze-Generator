package backend.academy.samples;

import backend.academy.BFSSolver;
import backend.academy.BFSSolverNormal;
import backend.academy.Cell;
import backend.academy.ConsoleRenderer;
import backend.academy.Coordinate;
import backend.academy.DFSSolver;
import backend.academy.Maze;
import backend.academy.Solver;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SolverTest {
    // Тест что алгоритм нашел путь между двумя точками правильно
    @Test
    public void testPathExists() {
        Maze maze = createMazeWithPath();
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(4, 4);

        // проверка алгоритма BFS
        BFSSolverNormal bfsSolver = new BFSSolverNormal();
        List<Coordinate> pathbfs = bfsSolver.solve(maze, start, end);

        assertFalse(pathbfs.isEmpty(), "Path should exist but was not found.");
        assertEquals(end, pathbfs.get(pathbfs.size() - 1), "The last coordinate of the path should be the end coordinate.");

        //проверка алгоритма DFS
        DFSSolver dfsSolver = new DFSSolver();
        List<Coordinate> pathdfs = dfsSolver.solve(maze, start, end);

        assertFalse(pathdfs.isEmpty(), "Path should exist but was not found.");
        assertEquals(end, pathdfs.get(pathbfs.size() - 1), "The last coordinate of the path should be the end coordinate.");
    }

    // Тест на то может ли умный алгоритм BFS находить более выголный путь с учетом поверхности
    @Test
    void testPathWithType() {
        Maze maze = mazeSolveType();
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(4, 4);

        //алгоритм умного поиска пути с учетом поверхности
        BFSSolver solver = new BFSSolver();
        List<Coordinate> path = solver.solve(maze, start, end);

        assertFalse(path.isEmpty(), "Path should exist but was not found.");
        assertEquals(end, path.get(path.size() - 1), "The last coordinate of the path should be the end coordinate.");

        // сравним путь с обычным алгоритмом BFS в данном случае он не может быть больще
        BFSSolverNormal bfsSolver = new BFSSolverNormal();
        List<Coordinate> pathbfs = bfsSolver.solve(maze, start, end);
        assertTrue(path.size() <= pathbfs.size());

        // сравнение веса двух путей учитывая облегчающие и усложняющие поверзности
        int normWeight = SumWeightPath(pathbfs);
        int idealWeight = SumWeightPath(path);

        assertTrue(idealWeight < normWeight);
    }

    // Тест что алгоритм не нашел путь между двумя точками
    @Test
    public void testNoPath() {
        Maze maze = createMazeWithoutPath();
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(3, 1);

        // проверка алгоритма BFS
        BFSSolverNormal bfsSolver = new BFSSolverNormal();
        List<Coordinate> pathbfs = bfsSolver.solve(maze, start, end);

        assertTrue(pathbfs.isEmpty(), "Path should not exist but was found.");

        // проверка алгоритма DFS
        DFSSolver dfsSolver = new DFSSolver();
        List<Coordinate> pathdfs = dfsSolver.solve(maze, start, end);

        assertTrue(pathdfs.isEmpty(), "Path should not exist but was found.");
    }

    private Maze createMazeWithPath() {
        Maze maze = new Maze(5, 5);
        maze.setCell(0, 0, Cell.Type.PASSAGE);
        maze.setCell(0, 1, Cell.Type.PASSAGE);
        maze.setCell(0, 2, Cell.Type.PASSAGE);
        maze.setCell(0, 3, Cell.Type.PASSAGE);
        maze.setCell(0, 4, Cell.Type.PASSAGE);
        maze.setCell(1, 0, Cell.Type.PASSAGE);
        maze.setCell(1, 1, Cell.Type.PASSAGE);
        maze.setCell(1, 2, Cell.Type.PASSAGE);
        maze.setCell(1, 3, Cell.Type.PASSAGE);
        maze.setCell(1, 4, Cell.Type.PASSAGE);
        maze.setCell(2, 0, Cell.Type.PASSAGE);
        maze.setCell(2, 1, Cell.Type.PASSAGE);
        maze.setCell(2, 2, Cell.Type.PASSAGE);
        maze.setCell(2, 3, Cell.Type.PASSAGE);
        maze.setCell(2, 4, Cell.Type.PASSAGE);
        maze.setCell(3, 0, Cell.Type.PASSAGE);
        maze.setCell(3, 1, Cell.Type.PASSAGE);
        maze.setCell(3, 2, Cell.Type.PASSAGE);
        maze.setCell(3, 3, Cell.Type.PASSAGE);
        maze.setCell(3, 4, Cell.Type.PASSAGE);
        maze.setCell(4, 0, Cell.Type.PASSAGE);
        maze.setCell(4, 1, Cell.Type.PASSAGE);
        maze.setCell(4, 2, Cell.Type.PASSAGE);
        maze.setCell(4, 3, Cell.Type.PASSAGE);
        maze.setCell(4, 4, Cell.Type.PASSAGE);

        return maze;
    }

    private Maze createMazeWithoutPath() {
        Maze maze = new Maze(5, 5);

        // Все ячейки - стены
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                maze.setCell(row, col, Cell.Type.WALL);
            }
        }

        maze.setCell(0, 0, Cell.Type.PASSAGE);
        maze.setCell(4, 4, Cell.Type.PASSAGE);

        return maze;
    }

    private Maze mazeSolveType() {
        Maze maze = new Maze(5, 5);

        maze.setCell(0, 0, Cell.Type.PASSAGE);
        maze.setCell(0, 1, Cell.Type.PASSAGE);
        maze.setCell(0, 2, Cell.Type.WALL);
        maze.setCell(0, 3, Cell.Type.WALL);
        maze.setCell(0, 4, Cell.Type.WALL);

        maze.setCell(1, 0, Cell.Type.COIN);
        maze.setCell(1, 1, Cell.Type.SAND);
        maze.setCell(1, 2, Cell.Type.PASSAGE);
        maze.setCell(1, 3, Cell.Type.WALL);
        maze.setCell(1, 4, Cell.Type.WALL);

        maze.setCell(2, 0, Cell.Type.PASSAGE);
        maze.setCell(2, 1, Cell.Type.WALL);
        maze.setCell(2, 2, Cell.Type.PASSAGE);
        maze.setCell(2, 3, Cell.Type.WALL);
        maze.setCell(2, 4, Cell.Type.WALL);

        maze.setCell(3, 0, Cell.Type.PASSAGE);
        maze.setCell(3, 1, Cell.Type.PASSAGE);
        maze.setCell(3, 2, Cell.Type.PASSAGE);
        maze.setCell(3, 3, Cell.Type.PASSAGE);
        maze.setCell(3, 4, Cell.Type.PASSAGE);
        maze.setCell(4, 0, Cell.Type.PASSAGE);
        maze.setCell(4, 1, Cell.Type.PASSAGE);
        maze.setCell(4, 2, Cell.Type.PASSAGE);
        maze.setCell(4, 3, Cell.Type.PASSAGE);
        maze.setCell(4, 4, Cell.Type.PASSAGE);

        String example =
            "⬜⬜⬛⬛⬛"
                + "🪙🟧⬜⬛⬛"
                + "⬜⬛⬜⬛⬛"
                + "⬜⬜⬜⬜⬜"
                + "⬜⬜⬜⬜⬜";

        return maze;
    }

    private int SumWeightPath(List<Coordinate> path) {
        Maze maze = mazeSolveType();

        int totalWeight = 0;

        // Проходим по каждому элементу пути
        for (Coordinate coord : path) {
            Cell cellType = maze.getCell(coord.row(), coord.col()); // Получаем тип ячейки по координатам
            if (cellType.getType() == Cell.Type.PASSAGE) {
                totalWeight += 2;
            }
            else if (cellType.getType() == Cell.Type.COIN) {
                totalWeight += 0;
            }
            else {
                totalWeight += 4;
            }
        }

        return totalWeight;
    }
}
