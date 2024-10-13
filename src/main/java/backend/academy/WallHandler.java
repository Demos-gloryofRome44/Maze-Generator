package backend.academy;

import java.io.PrintStream;
import java.util.Scanner;

public class WallHandler {
    private final Maze maze;
    private final Scanner scanner;

    public WallHandler(Maze maze) {
        this.maze = maze;
        this.scanner = new Scanner(System.in);
    }

    @SuppressWarnings("all")
    public Coordinate checkAndModifyCellType(Coordinate coordinate, String pointLabel) {
        Coordinate coord = coordinate;
        while (maze.getCell(coord.row(), coord.col()).getType() != Cell.Type.PASSAGE) {
            System.out.printf("Вы выбрали за точку %s стену. Вы хотите сделать её проходом? Да/Нет? ", pointLabel);
            String response = scanner.nextLine();

            if (response.equalsIgnoreCase("Д")) {
                maze.getCell(coord.row(), coord.col()).setType(Cell.Type.PASSAGE);
                break;
            } else {
                coord = getUserInputCoordinate(String.format("Введите новые координаты для точки %s (row col): ",
                    pointLabel), System.out);
            }
        }
        return coord;
    }

    private Coordinate getUserInputCoordinate(String prompt, PrintStream out) {
        out.print(prompt);
        int row = scanner.nextInt();
        int col = scanner.nextInt();
        return new Coordinate(row, col);
    }
}
