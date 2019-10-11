package ru.krushnyakov.natera.graph;

public abstract class Edge<V> {

    private V source;

    private V destination;

    private int weight;

    public Edge(V source, V destination, int weight) {
        if (source == null || destination == null)
            throw new IllegalArgumentException("Vertex can't be null!");
        if (weight <= 0)
            throw new IllegalArgumentException("Edge weight must be positive!");
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public Edge(V source, V destination) {
        this(source, destination, 1);
    }

    public V getSource() {
        return source;
    }

    public V getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }

    abstract public boolean startsAt(V vertex);

    abstract public boolean endsAt(V vertex);

    abstract public boolean connectsVertices(V vertexA, V vertexB);

    public V getOtherVertex(V v) {
        if(!(source.equals(v) || destination.equals(v))) {
            throw new IllegalArgumentException("Vertex doesn't belong to edge!");
        }
        return source.equals(v) ? destination : source;
    }


}
