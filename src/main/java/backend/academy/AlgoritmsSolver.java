package backend.academy;

public enum AlgoritmsSolver {
    BFS(1),
    DFS(2);

    private final int value;

    AlgoritmsSolver(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static AlgoritmsSolver fromValue(int value) {
        for (AlgoritmsSolver topic : AlgoritmsSolver.values()) {
            if (topic.getValue() == value) {
                return topic;
            }
        }
        throw new IllegalArgumentException("Нет алгоритма с таким значением: " + value);
    }
}
