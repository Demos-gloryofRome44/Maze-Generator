package backend.academy;

public class Edge implements Comparable<Edge> {
    private final int weight;
    private final Coordinate from;
    private final Coordinate to;

    public Edge(int weight, Coordinate from, Coordinate to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }

    public int getWeight() {
        return weight;
    }

    public Coordinate getFrom() {
        return from;
    }

    public Coordinate getTo() {
        return to;
    }

    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.weight, other.weight);
    }
}
