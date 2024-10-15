package backend.academy;

import java.util.List;
import java.util.Scanner;
import lombok.experimental.UtilityClass;


@UtilityClass
@SuppressWarnings("all")
public class Main {
    private static final String ERRORUNCKNOWN = "Неизвестный алгоритм: ";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Учтите, прежде чем генерировать лабиринт, что его размер подходит под вашу консоль\n"
            + "рекомендованное максимально число на высоту - 35 и ширину - 170\n"
            + "Условные обозначение ⬛ - стена ⬜ - проход 🟩 - путь от двух точек");
        int height = InputValidator.validateHeight(scanner, System.out);
        int width = InputValidator.validateWidth(scanner, System.out);

        int choiceGenerator = InputValidator.validateAlgoritmGeneratorChoice(scanner, System.out);
        AlgoritmsGenerator algoritm = AlgoritmsGenerator.fromValue(choiceGenerator);

        Maze maze;
        Generator generator;
        switch (algoritm) {
            case KRUSKAL:
                generator = new KruskalGenerator();
                maze = generator.generate(height, width);
                System.out.println("Лабиринт сгенерирован с использованием алгоритма Крускала.");
                break;
            case IDEAL:
                generator = new IdealGenerator();
                maze = generator.generate(height, width);
                System.out.println("Лабиринт сгенерирован с использованием идеального алгоритма.");
                break;
            default:
                throw new IllegalStateException(ERRORUNCKNOWN + algoritm);
        }

        System.out.println("Сгенерированный лабиринт:");
        ConsoleRenderer renderer = new ConsoleRenderer();
        System.out.println(renderer.render(maze));

        WallHandler wallHandler = new WallHandler(maze);

        Coordinate startPoint = InputValidator.validateCoordinate(scanner, height, width,
            "начальную", "A", System.out);
        startPoint = wallHandler.checkAndModifyCellType(startPoint, "A");

        Coordinate endPoint = InputValidator.validateCoordinate(scanner, height, width,
            "конечную", "B", System.out);
        endPoint = wallHandler.checkAndModifyCellType(endPoint, "B");


        int choiceSolver = InputValidator.validateSolverChoice(scanner, System.out);

        AlgoritmsSolver algoritmsSolver = AlgoritmsSolver.fromValue(choiceSolver);

        List<Coordinate> path;
        Solver solver;
        switch (algoritmsSolver) {
            case BFS:
                solver = new BFSSolver();
                path = solver.solve(maze, startPoint, endPoint);
                break;
            case DFS:
                solver = new DFSSolver();
                path = solver.solve(maze, startPoint, endPoint);
                break;
            default:
                throw new IllegalStateException(ERRORUNCKNOWN + algoritm);
        }

        if (!path.isEmpty()) {
            System.out.println("Найденный путь:");
            System.out.println(renderer.renderWithPath(maze, path, startPoint, endPoint));
        } else {
            System.out.println("Путь не найден.");
        }

        scanner.close();
    }
}
