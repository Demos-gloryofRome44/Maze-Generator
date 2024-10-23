package backend.academy;

import backend.academy.console.ConsoleRenderer;
import backend.academy.enums.AlgoritmsGenerator;
import backend.academy.enums.AlgoritmsSolver;
import backend.academy.generator.Generator;
import backend.academy.generator.IdealGenerator;
import backend.academy.generator.KruskalGenerator;
import backend.academy.solver.BFSSolver;
import backend.academy.solver.BFSSolverNormal;
import backend.academy.solver.DFSSolver;
import backend.academy.solver.Solver;
import backend.academy.substance.Coordinate;
import backend.academy.substance.Maze;
import backend.academy.validator.InputValidator;
import backend.academy.validator.WallHandler;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import lombok.experimental.UtilityClass;


@UtilityClass
public class Main {
    private static final String ERROR_UNCKNOWN = "Неизвестный алгоритм: ";

    public static void main(String[] args) {
        PrintStream out = System.out;

        out.println("Рады вас видеть в MAZE Generator:) \nпрежде чем увлечься работой с лабиринтами, "
            + "проверьте, что размер лабиринта подходит под вашу консоль\n"
            + "рекомендованное максимально число на высоту - 35 и ширину - 170\n"
            + "Условные обозначение:\n"
            + "⬛ - стена\n⬜ - проход\n"
            + "🪙 - монета (самая хорошая поверхность доступная человечеству)\n"
            + "🟧 - песок (можно пройти, но если увязнешь сам виноват)\n"
            + "🟩 - пройденно пустое пространство\n🟢 - пройдена монета\n🔶 - пройден песок");

        while (true) {
            out.println("----- Запуск MAZE Generator -----");
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
                    throw new IllegalStateException(ERROR_UNCKNOWN + algoritm);
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
            List<Coordinate> path2;
            Solver solver;
            switch (algoritmsSolver) {
                case BFS:
                    solver = new BFSSolverNormal();
                    path = solver.solve(maze, startPoint, endPoint);

                    solver = new BFSSolver();
                    path2 = solver.solve(maze, startPoint, endPoint);
                    break;
                case DFS:
                    solver = new DFSSolver();
                    path = solver.solve(maze, startPoint, endPoint);
                    path2 = Collections.emptyList();
                    break;
                default:
                    throw new IllegalStateException(ERROR_UNCKNOWN + algoritm);
            }

            if (!path.isEmpty()) {
                out.println("Найденный путь без учета местности:");
                out.println(renderer.renderWithPath(maze, path, startPoint, endPoint));
            } else {
                out.println("Путь не найден.");
            }
            if (!path2.isEmpty()) {
                out.println("Найденный путь с учетом местности:");
                out.println(renderer.renderWithPath(maze, path2, startPoint, endPoint));
            } else {
                out.println("Путь с учетом местности не найден.");
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
