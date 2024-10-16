package backend.academy.samples;

import backend.academy.Cell;
import backend.academy.ConsoleRenderer;
import backend.academy.Coordinate;
import backend.academy.Maze;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsoleRendererTest {

    // Тест на вывод в консоли лабиринта не проходит с инексами ряда, без них проходил
    @Test
    public void testRenderMaze() {
        Maze maze = createSimpleMaze();
        ConsoleRenderer renderer = new ConsoleRenderer();

        String expectedOutput =
            "4 ⬛️⬜⬛️⬜⬛️\n"
        + "3 ⬛️⬜⬛️⬜⬛️\n"
        + "2 ⬛️⬜⬛️⬜⬛️\n"
        + "1 ⬛️⬜⬛️⬜⬛️\n"
        + "0 ⬛️⬜⬛️⬜⬛️\n";

        String actualOutput = renderer.render(maze);
        assertEquals(expectedOutput, actualOutput, "The rendered maze should match the expected output.");
    }

    // Тест на вывод лабирнта с правильным путем между двумя точками лабиринта в консоли
    @Test
    public void testRenderWithPath() {
        Maze maze = createSimpleMaze();
        ConsoleRenderer renderer = new ConsoleRenderer();

        List<Coordinate> path = List.of(new Coordinate(1, 1), new Coordinate(2, 1));
        Coordinate start = new Coordinate(0, 1);
        Coordinate end = new Coordinate(3, 1);

        String expectedOutput =
            "4 ⬛⬜⬛⬜⬛\n"
                + "3 ⬛🅱️⬛⬜⬛\n"
                + "2 ⬛🟩⬛⬜⬛\n"
                + "1 ⬛🟩⬛⬜⬛\n"
                + "0 ⬛🅰️⬛⬜⬛\n";

        String actualOutput = renderer.renderWithPath(maze, path, start, end);
        assertEquals(expectedOutput, actualOutput, "The rendered maze with path should match the expected output.");
    }

    private Maze createSimpleMaze() {
        Maze maze = new Maze(5, 5);
        maze.setCell(0, 0, Cell.Type.WALL);
        maze.setCell(0, 1, Cell.Type.PASSAGE);
        maze.setCell(0, 2, Cell.Type.WALL);
        maze.setCell(0, 3, Cell.Type.PASSAGE);
        maze.setCell(0, 4, Cell.Type.WALL);

        maze.setCell(1, 0, Cell.Type.WALL);
        maze.setCell(1, 1, Cell.Type.PASSAGE);
        maze.setCell(1, 2, Cell.Type.WALL);
        maze.setCell(1, 3, Cell.Type.PASSAGE);
        maze.setCell(1, 4, Cell.Type.WALL);

        maze.setCell(2, 0, Cell.Type.WALL);
        maze.setCell(2, 1, Cell.Type.PASSAGE);
        maze.setCell(2, 2, Cell.Type.WALL);
        maze.setCell(2, 3, Cell.Type.PASSAGE);
        maze.setCell(2, 4, Cell.Type.WALL);

        maze.setCell(3, 0, Cell.Type.WALL);
        maze.setCell(3, 1, Cell.Type.PASSAGE);
        maze.setCell(3, 2, Cell.Type.WALL);
        maze.setCell(3, 3, Cell.Type.PASSAGE);
        maze.setCell(3, 4, Cell.Type.WALL);

        maze.setCell(4, 0, Cell.Type.WALL);
        maze.setCell(4, 1, Cell.Type.PASSAGE);
        maze.setCell(4, 2, Cell.Type.WALL);
        maze.setCell(4, 3, Cell.Type.PASSAGE);
        maze.setCell(4, 4, Cell.Type.WALL);

        return maze;
    }
}
