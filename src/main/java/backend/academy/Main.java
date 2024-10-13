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
            + "рекомендованное максимально число на высоту - 35 и ширину - 170");
        int height = InputValidator.validateHeight(scanner, System.out);
        int width = InputValidator.validateWidth(scanner, System.out);

        int choiceGenerator = InputValidator.validateAlgoritmGeneratorChoice(scanner, System.out);
        AlgoritmsGenerator algoritm = AlgoritmsGenerator.fromValue(choiceGenerator);

        Maze maze;
        switch (algoritm) {
            case KRUSKAL:
                KruskalGenerator generator = new KruskalGenerator();
                maze = generator.generate(height, width);
                System.out.println("Лабиринт сгенерирован с использованием алгоритма Крускала.");
                break;
            case IDEAL:
                IdealGenerator idealgenerator = new IdealGenerator();
                maze = idealgenerator.generate(height, width);
                System.out.println("Лабиринт сгенерирован с использованием идеального алгоритма.");
                break;
            default:
                throw new IllegalStateException(ERRORUNCKNOWN + algoritm);
        }



        System.out.println("Сгенерированный лабиринт:");
        ConsoleRenderer renderer = new ConsoleRenderer();
        System.out.println(renderer.render(maze));

        Coordinate startPoint = InputValidator.validateCoordinate(scanner, height, width,
            "начальную", "A", System.out);


        Coordinate endPoint = InputValidator.validateCoordinate(scanner, height, width,
            "конечную", "B", System.out);


        int choiceSolver = InputValidator.validateSolverChoice(scanner, System.out);

        AlgoritmsSolver algoritmsSolver = AlgoritmsSolver.fromValue(choiceSolver);
        List<Coordinate> path;
        switch (algoritmsSolver) {
            case BFS:
                BFSSolver bfsSolver = new BFSSolver();
                path = bfsSolver.solve(maze, startPoint, endPoint);
                break;
            case DFS:
                DFSSolver dfsSolver = new DFSSolver();
                path = dfsSolver.solve(maze, startPoint, endPoint);
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
