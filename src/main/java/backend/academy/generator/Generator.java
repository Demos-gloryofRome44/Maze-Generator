package backend.academy.generator;

import backend.academy.substance.Maze;

public interface Generator {
    Maze generate(int height, int width);
}
