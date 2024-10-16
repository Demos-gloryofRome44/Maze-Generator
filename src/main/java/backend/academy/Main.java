package backend.academy;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import lombok.experimental.UtilityClass;


@UtilityClass
public class Main {
    private static final String ERRORUNCKNOWN = "Неизвестный алгоритм: ";

    public static void main(String[] args) {
        PrintStream out = System.out;

        out.println("Учтите, прежде чем генерировать лабиринт, что его размер подходит под вашу консоль\n"
            + "рекомендованное максимально число на высоту - 35 и ширину - 170\n"
            + "Условные обозначение ⬛ - стена ⬜ - проход 🟩 - путь от двух точек");

        while (true) {
            out.println("----- Запуск программы -----");
            Scanner scanner = new Scanner(System.in);
            int height = InputValidator.validateHeight(scanner, out);
            int width = InputValidator.validateWidth(scanner, out);

            int choiceGenerator = InputValidator.validateAlgoritmGeneratorChoice(scanner, out);
            AlgoritmsGenerator algoritm = AlgoritmsGenerator.fromValue(choiceGenerator);

            Maze maze;
            Generator generator;
            switch (algoritm) {
                case KRUSKAL:
                    generator = new KruskalGenerator();
                    maze = generator.generate(height, width);
                    out.println("Лабиринт сгенерирован с использованием алгоритма Крускала.");
                    break;
                case IDEAL:
                    generator = new IdealGenerator();
                    maze = generator.generate(height, width);
                    out.println("Лабиринт сгенерирован с использованием идеального алгоритма.");
                    break;
                default:
                    throw new IllegalStateException(ERRORUNCKNOWN + algoritm);
            }

            out.println("Сгенерированный лабиринт:");
            ConsoleRenderer renderer = new ConsoleRenderer();
            out.println(renderer.render(maze));

            WallHandler wallHandler = new WallHandler(maze);

            Coordinate startPoint = InputValidator.validateCoordinate(scanner, height, width,
                "начальную", "A", out);
            startPoint = wallHandler.checkAndModifyCellType(startPoint, "A", out);

            Coordinate endPoint = InputValidator.validateCoordinate(scanner, height, width,
                "конечную", "B", out);
            endPoint = wallHandler.checkAndModifyCellType(endPoint, "B", out);

            int choiceSolver = InputValidator.validateSolverChoice(scanner, out);

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
                out.println("Найденный путь:");
                out.println(renderer.renderWithPath(maze, path, startPoint, endPoint));
            } else {
                out.println("Путь не найден.");
            }

            out.print("Хотите сгенерировать еще один лабиринт? ([Д]а/[Н]ет): ");
            Scanner scanner1 = new Scanner(System.in);
            String answer = scanner1.nextLine().trim().toLowerCase();
            if (!answer.equalsIgnoreCase("Д") && !answer.equalsIgnoreCase("Да")) {
                out.println("Спасибо, что восполользовались генератором лабиринтов ;)");
                scanner1.close();
                scanner.close();
                break;
            }

        }
    }
}
