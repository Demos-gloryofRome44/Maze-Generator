package backend.academy.enums;

public enum AlgoritmsGenerator {
    IDEAL(1),
    KRUSKAL(2);

    private final int value;

    AlgoritmsGenerator(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static AlgoritmsGenerator fromValue(int value) {
        for (AlgoritmsGenerator topic : AlgoritmsGenerator.values()) {
            if (topic.getValue() == value) {
                return topic;
            }
        }
        throw new IllegalArgumentException("Нет алгоритма с таким значением: " + value);
    }
}
