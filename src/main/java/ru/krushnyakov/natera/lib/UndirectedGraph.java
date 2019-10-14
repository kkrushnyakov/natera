/**
 * 
 */
package ru.krushnyakov.natera.lib;

import java.util.Set;

/**
 * https://en.wikipedia.org/wiki/Graph_(discrete_mathematics)#Undirected_graph
 * 
 * @author kkrushnyakov
 *
 */
public class UndirectedGraph<V> extends DirectedGraph<V> {

    public UndirectedGraph() {
        super();
    }

    public UndirectedGraph(Set<V> vertices, Set<Edge<V>> edges) {
        super(vertices, edges);
        if(edges.stream().anyMatch(e -> e.getClass() != UndirectedEdge.class)) throw new IllegalArgumentException("Can initialize undirected graph only with undirected edges!");
    }

    @Override
    public Graph<V> addEdge(Edge<V> edge) {
        if (!(edge instanceof UndirectedEdge)) {
            throw new IllegalArgumentException("Only undirected edges are possible in undirected graph!");
        }
        super.addEdge(edge);
        return this;
    }

}
