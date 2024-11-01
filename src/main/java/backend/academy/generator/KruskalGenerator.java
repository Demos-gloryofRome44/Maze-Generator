package backend.academy.generator;

import backend.academy.substance.Cell;
import backend.academy.substance.Coordinate;
import backend.academy.substance.Maze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class KruskalGenerator implements Generator {
    private final Random random = new Random();
    private static final int COIN_DENSITY = 10;
    private static final int SAND_DENSITY = 20;

    /**
     * Генерирует лабиринт заданных размеров.
     *
     * @param height Высота лабиринта.
     * @param width  Ширина лабиринта.
     * @return       Сгенерированный лабиринт.
     */
    @Override
    public Maze generate(int height, int width) throws IllegalArgumentException {
        UnionFind unionFind = new UnionFind(width, height);
        Cell[][] cells = new Cell[height][width];
        List<Wall> walls = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            cells[0][x] = new Cell(0, x, Cell.Type.WALL);
            cells[height - 1][x] = new Cell(height - 1, x, Cell.Type.WALL);
        }

        for (int y = 0; y < height; y++) {
            cells[y][0] = new Cell(y, 0, Cell.Type.WALL);
            cells[y][width - 1] = new Cell(y, width - 1, Cell.Type.WALL);
        }


        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                cells[y][x] = new Cell(y, x, Cell.Type.WALL);

                if (y == height - 2 && x == width - 2) {
                    cells[y][x].setType(Cell.Type.PASSAGE);
                }

                if (x < width - 2) {
                    walls.add(new Wall(new Coordinate(y, x), new Coordinate(y, x + 1)));
                }
                if (y < height - 2) {
                    walls.add(new Wall(new Coordinate(y, x), new Coordinate(y + 1, x)));
                }
            }
        }

        Collections.shuffle(walls, random);

        for (Wall wall : walls) {
            Coordinate cell1 = wall.cell1;
            Coordinate cell2 = wall.cell2;

            if (!unionFind.find(cell1).equals(unionFind.find(cell2))) {
                unionFind.union(cell1, cell2);

                if (cell1.row() == cell2.row()) {
                    cells[cell1.row()][Math.min(cell1.col(), cell2.col())].setType(Cell.Type.PASSAGE);
                } else {
                    cells[Math.min(cell1.row(), cell2.row())][cell1.col()].setType(Cell.Type.PASSAGE);
                }
            }
        }
        generateSurfaces(cells);

        return new Maze(cells);
    }

    /**
     * Генерирует дополнительные поверхности (монеты и песок) в лабиринте.
     *
     * @param cells Двумерный массив клеток лабиринта.
     */
    private void generateSurfaces(Cell[][] cells) {
        int height = cells.length;
        int width = cells[0].length;

        int numberOfCoins = (height * width) / COIN_DENSITY;
        int numberOfSand = (height * width) / SAND_DENSITY;

        for (int i = 0; i < numberOfCoins; i++) {
            placeRandomSurface(cells, Cell.Type.COIN);
        }

        for (int i = 0; i < numberOfSand; i++) {
            placeRandomSurface(cells, Cell.Type.SAND);
        }
    }

    /**
     * Помещает случайную поверхность в лабиринт.
     *
     * @param cells Двумерный массив клеток лабиринта.
     * @param type Тип поверхности для размещения.
     */
    private void placeRandomSurface(Cell[][] cells, Cell.Type type) {
        Random randomSurf = new Random();
        int height = cells.length;
        int width = cells[0].length;

        while (true) {
            int row = randomSurf.nextInt(height - 2) + 1;
            int col = randomSurf.nextInt(width - 2) + 1;

            if (cells[row][col].getType() == Cell.Type.PASSAGE) {
                cells[row][col].setType(type);
                break;
            }
        }
    }

    /**
     * Класс для представления стены между двумя клетками.
     */
    private static class Wall {
        public Coordinate cell1;
        public Coordinate cell2;

        Wall(Coordinate cell1, Coordinate cell2) {
            this.cell1 = cell1;
            this.cell2 = cell2;
        }
    }

    /**
     * Класс для реализации структуры "Система непересекающихся множеств" (Union-Find).
     */
    private static class UnionFind {
        private final Map<Coordinate, Coordinate> parent = new HashMap<>();

        UnionFind(int width, int height) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Coordinate coordinate = new Coordinate(y, x);
                    parent.put(coordinate, coordinate);
                }
            }
        }

        public Coordinate find(Coordinate node) {
            if (!parent.get(node).equals(node)) {
                parent.put(node, find(parent.get(node)));
            }
            return parent.get(node);
        }

        public void union(Coordinate node1, Coordinate node2) {
            Coordinate root1 = find(node1);
            Coordinate root2 = find(node2);
            if (!root1.equals(root2)) {
                parent.put(root1, root2);
            }
        }
    }
}
