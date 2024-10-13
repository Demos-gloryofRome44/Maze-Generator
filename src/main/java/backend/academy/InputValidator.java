package backend.academy;

import java.io.PrintStream;
import java.util.Scanner;

public class InputValidator {
    private static final int HEIGHTWEIGHT = 5;
    private static final int MAXHEIGHT = 35;
    private  static final int MAXWIDTH = 170;

    private static final String ERRORMESSAGEINT = "Некорректный ввод. Пожалуйста, введите целое число.";
    private static final String ERRORMESSAGECHOUSE = "Некорректный выбор. Пожалуйста, выберите 1 или 2.";

    private InputValidator() {
        throw new UnsupportedOperationException("Утилитарный класс не может быть инстанцирован");
    }

    public static int validateHeight(Scanner scanner, PrintStream out) {
        int height;
        while (true) {
            out.print("Введите высоту лабиринта (макс. 35; мин. 5): ");
            if (scanner.hasNextInt()) {
                height = scanner.nextInt();
                if (height >= HEIGHTWEIGHT && height <= MAXHEIGHT) {
                    return height;
                } else {
                    out.println("Некорректное значение. Пожалуйста, введите число от 1 до 35.");
                }
            } else {
                out.println(ERRORMESSAGEINT);
                scanner.next();
            }
        }
    }

    public static int validateWidth(Scanner scanner, PrintStream out) {
        int width;
        while (true) {
            out.print("Введите ширину лабиринта (макс. 170; мин. 5): ");
            if (scanner.hasNextInt()) {
                width = scanner.nextInt();
                if (width >= HEIGHTWEIGHT && width <= MAXWIDTH) {
                    return width; // Ввод корректен
                } else {
                    out.println("Некорректное значение. Пожалуйста, введите число от 1 до 170.");
                }
            } else {
                out.println(ERRORMESSAGEINT);
                scanner.next();
            }
        }
    }

    public static Coordinate validateCoordinate(Scanner scanner, int height, int width, String pointName,
        String pointName2, PrintStream out) {
        while (true) {
            out.print("Введите " + pointName + " точку " + pointName2 + " (row column): ");
            if (scanner.hasNextInt()) {
                int row = scanner.nextInt();
                int col = scanner.nextInt();
                if (row >= 0 && row < height && col >= 0 && col < width) {
                    return new Coordinate(row, col);
                } else {
                    out.println("Некорректные координаты точки " + pointName2 + ".");
                }
            } else {
                out.println("Некорректный ввод! Координаты должны быть целыми числами.");
                scanner.next();
            }
            scanner.nextLine();
        }
    }

    public static int validateAlgoritmGeneratorChoice(Scanner scanner, PrintStream out) {
        int choice;
        while (true) {
            out.println("Выберите алгоритм для создания лабиринта\n1. Идеальный алгоритм\n2. Алгоритм Крускала");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (AlgoritmsGenerator.KRUSKAL.getValue() == choice || AlgoritmsGenerator.IDEAL.getValue() == choice) {
                    return choice;
                } else {
                    out.println(ERRORMESSAGECHOUSE);
                }
            } else {
                out.println(ERRORMESSAGEINT);
                scanner.next();
            }
        }
    }

    public static int validateSolverChoice(Scanner scanner, PrintStream out) {
        int choice;
        while (true) {
            out.println("Выберите алгоритм поиска пути\n1. BFS\n2. DFS");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (AlgoritmsSolver.BFS.getValue() == choice  || AlgoritmsSolver.DFS.getValue() == choice) {
                    return choice;
                } else {
                    out.println(ERRORMESSAGECHOUSE);
                }
            } else {
                out.println(ERRORMESSAGEINT);
                scanner.next();
            }
        }
    }
}
