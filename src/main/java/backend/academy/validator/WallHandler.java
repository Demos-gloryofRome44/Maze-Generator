package backend.academy.validator;

import backend.academy.substance.Cell;
import backend.academy.substance.Coordinate;
import backend.academy.substance.Maze;
import java.io.PrintStream;
import java.util.Scanner;

public class WallHandler {
    private final Maze maze;
    private final Scanner scanner;
    private static final String SELECTED_POINT_MESSAGE = "Вы выбрали за точку ";

    public WallHandler(Maze maze) {
        this.maze = maze;
        this.scanner = new Scanner(System.in);
    }

    public Coordinate checkAndModifyCellType(Coordinate coordinate, String pointLabel, PrintStream out) {
        Coordinate coord = coordinate;

        while (true) {
            if (maze.getCell(coord.row(), coord.col()).getType() != Cell.Type.WALL) {
                break;
            }
            out.print(SELECTED_POINT_MESSAGE + pointLabel + " стену. Вы хотите сделать её проходом? [Д]а/[Н]ет? ");
            String response = scanner.nextLine();

            if (response.equalsIgnoreCase("Д") || response.equalsIgnoreCase("Да")) {
                maze.getCell(coord.row(), coord.col()).setType(Cell.Type.PASSAGE);
                break;
            } else {
                while (true) {
                    coord = InputValidator.validateCoordinate(scanner, maze.getHeight(), maze.getWidth(),
                        pointLabel.equals("A") ? "начальную" : "конечную", pointLabel, System.out);

                    if (maze.getCell(coord.row(), coord.col()).getType() == Cell.Type.WALL) {
                        out.println(SELECTED_POINT_MESSAGE + pointLabel + " стену. "
                            + "Пожалуйста, выберите другую координату.");
                    } else {
                        return coord;
                    }
                }
            }
        }
        return coord;
    }
}
