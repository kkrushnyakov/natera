package ru.krushnyakov.natera.graph;

public class UndirectedEdge<V> extends Edge<V> {

    public UndirectedEdge(V source, V destination) {
        super(source, destination);
    }

    public UndirectedEdge(V source, V destination, int weight) {
        super(source, destination, weight);
    }

    @Override
    public boolean startsAt(V vertex) {
        return getSource().equals(vertex) || getDestination().equals(vertex);
    }

    @Override
    public boolean endsAt(V vertex) {
        return getSource().equals(vertex) || getDestination().equals(vertex);
    }

    @Override
    public String toString() {
        return "[" + getSource() + "]---" + getWeight() + "---[" + getDestination() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = getDestination().hashCode() + getSource().hashCode();
        result = prime * result + getWeight();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        @SuppressWarnings("unchecked")
        Edge<V> other = (Edge<V>) obj;
        if (getWeight() != other.getWeight())
            return false;
        return (getSource().equals(other.getSource()) && getDestination().equals(other.getDestination())
                || (getSource().equals(other.getDestination()) && getDestination().equals(other.getSource())));
    }

    @Override
    public boolean connectsVertices(V vertexA, V vertexB) {
        return (getSource().equals(vertexA) && getDestination().equals(vertexB)) || (getSource().equals(vertexB) && getDestination().equals(vertexA));

   }

}
