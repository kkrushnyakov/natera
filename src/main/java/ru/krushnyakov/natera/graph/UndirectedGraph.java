/**
 * 
 */
package ru.krushnyakov.natera.graph;

import java.util.Set;

/**
 * @author kkrushnyakov
 *
 */
public class UndirectedGraph<V> extends DirectedGraph<V> {

    static {
//        log = LoggerFactory.getLogger(UndirectedGraph.class);
    }

    public UndirectedGraph() {
        super();
    }

    public UndirectedGraph(Set<V> vertices, Set<Edge<V>> edges) {
        super(vertices, edges);
        if(edges.stream().anyMatch(e -> e.getClass() != UndirectedEdge.class)) throw new IllegalArgumentException("Can initialize undirected graph only with undirected edges!");
    }

    @Override
    public void addEdge(Edge<V> edge) {
        if (!(edge instanceof UndirectedEdge)) {
            throw new IllegalArgumentException("Only undirected edges are possible in undirected graph!");
        }
        super.addEdge(edge);
    }

}
