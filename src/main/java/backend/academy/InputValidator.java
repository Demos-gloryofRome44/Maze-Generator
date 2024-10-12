package backend.academy;

import java.io.PrintStream;
import java.util.Scanner;

public class InputValidator {
    private static final int heightWeight = 5;
    private static final int maxHeight = 35;
    private  static final int maxWidth = 170;

    public static int validateHeight(Scanner scanner, PrintStream out) {
        int height;
        while (true) {
            out.print("Введите высоту лабиринта (макс. 35; мин. 5): ");
            if (scanner.hasNextInt()) {
                height = scanner.nextInt();
                if (height >= heightWeight && height <= maxHeight) {
                    return height;
                } else {
                    out.println("Некорректное значение. Пожалуйста, введите число от 1 до 35.");
                }
            } else {
                out.println("Некорректный ввод. Пожалуйста, введите целое число.");
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
                if (width >= heightWeight && width <= maxWidth) {
                    return width; // Ввод корректен
                } else {
                    out.println("Некорректное значение. Пожалуйста, введите число от 1 до 170.");
                }
            } else {
                out.println("Некорректный ввод. Пожалуйста, введите целое число.");
                scanner.next(); // Сбрасываем некорректный ввод
            }
        }
    }
}
