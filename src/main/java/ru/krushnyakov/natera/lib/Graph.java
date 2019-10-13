package ru.krushnyakov.natera.lib;

import java.util.List;
import java.util.function.Function;

public interface Graph<V> {

    Graph<V> addVertex(V vertex);

    Graph<V> addEdge(Edge<V> edge);

    /**
     *
     * @param sourceVertex
     * @param destinationVertex
     * @return List of edges contained in the result path
     */

    List<Edge<V>> getPath(V sourceVertex, V destinationVertex);

    List<?> traverse(Function<V, ?> function);

}