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
            System.out.print("Вы выбрали за точку " + pointLabel + " стену. Вы хотите сделать её проходом? [Д]а/[Н]ет? ");
            String response = scanner.nextLine();

            if (response.equalsIgnoreCase("Д") || response.equalsIgnoreCase("Да")) {
                maze.getCell(coord.row(), coord.col()).setType(Cell.Type.PASSAGE);
                break;
            } else {
                while (true) {
                    coord = InputValidator.validateCoordinate(scanner, maze.getHeight(), maze.getWidth(),
                        pointLabel.equals("A") ? "начальную" : "конечную", pointLabel, System.out);

                    if (maze.getCell(coord.row(), coord.col()).getType() == Cell.Type.WALL) {
                        System.out.println("Вы выбрали за точку " + pointLabel + " стену. Пожалуйста, выберите другую координату.");
                    } else {
                        return coord;
                    }
                }
            }
        }
        return coord;
    }
}
