package backend.academy;

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

        while (true) {
            if (maze.getCell(coord.row(), coord.col()).getType() == Cell.Type.PASSAGE) {
                break;
            }
            System.out.printf("Вы выбрали за точку %s стену. Вы хотите сделать её проходом? [Д]а/[Н]ет? ", pointLabel);
            String response = scanner.nextLine();

            if (response.equalsIgnoreCase("Д")) {
                maze.getCell(coord.row(), coord.col()).setType(Cell.Type.PASSAGE);
                break;
            } else {
                while (true) {
                    coord = InputValidator.validateCoordinate(scanner, maze.getHeight(), maze.getWidth(),
                        pointLabel.equals("A") ? "начальную" : "конечную", pointLabel, System.out);

                    if (maze.getCell(coord.row(), coord.col()).getType() == Cell.Type.WALL) {
                        System.out.printf("Вы выбрали за точку %s стену. Пожалуйста, выберите другую координату.\n", pointLabel);
                    } else {
                        return coord;
                    }
                }
            }
        }
        return coord;
    }
}
