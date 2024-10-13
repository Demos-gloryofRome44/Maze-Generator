package backend.academy.samples;

import backend.academy.Coordinate;
import backend.academy.InputValidator;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputValidatorTest {

    // тест на правильную установку высоты подавая строку "abs и 10" высота становиться 10
    @Test
    public void testValidateHeightWithInvalidInput() {
        String input = "abc\n10\n";
        Scanner scanner = new Scanner(input);
        PrintStream out = System.out;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream testOut = new PrintStream(outputStream);

        // Переопределяем вывод
        System.setOut(testOut);

        int height = InputValidator.validateHeight(scanner, testOut);

        // Проверяем, что высота была установлена правильно
        assertEquals(10, height);

        // Восстанавливаем оригинальный вывод
        System.setOut(out);
    }

    // тест на правильную установку ширины подавая строку "abs и 10" ширина становиться 10
    @Test
    public void testValidateWidthWithTooHighValue() {
        String input = "abc\n10\n";
        Scanner scanner = new Scanner(input);
        PrintStream out = System.out;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream testOut = new PrintStream(outputStream);

        // Переопределяем вывод
        System.setOut(testOut);

        int width = InputValidator.validateWidth(scanner, testOut);

        // Проверяем, что ширина была установлена правильно
        assertEquals(10, width); // Ожидаемое значение должно быть 170

        // Восстанавливаем оригинальный вывод
        System.setOut(out);
    }

    // тест на правильный ввод координаты подавая строку "abs и 1 4" точка становится 1 4
    @Test
    public void testValidateCoordinateWithInvalidRow() {
        String input = "abc\n1 4\n";
        Scanner scanner = new Scanner(input);
        PrintStream out = System.out;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream testOut = new PrintStream(outputStream);

        System.setOut(testOut);

        Coordinate coord = InputValidator.validateCoordinate(scanner, 5, 5, "начальную", "A", testOut);

        assertEquals(new Coordinate(1, 4), coord);

        System.setOut(out);
    }

    // тест на правильный выбор алгоритма подавая строку "abc и 4 b 1" выбор устнавливается на 1
    // так как в enum не установлен 4-ый алгоритм
    // такой же тест подходит и к выбору алгоритма для поиска пути как и к выбору алгоритма для генерации лабиринта
    @Test
    public void testValidateAlgorithmChoiceWithInvalidInput() {
        String input = "abc\n4\n1\n";
        Scanner scanner = new Scanner(input);
        PrintStream out = System.out;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream testOut = new PrintStream(outputStream);

        System.setOut(testOut);

        int choice = InputValidator.validateAlgoritmGeneratorChoice(scanner, testOut);

        assertEquals(1, choice);

        System.setOut(out);
    }
}
