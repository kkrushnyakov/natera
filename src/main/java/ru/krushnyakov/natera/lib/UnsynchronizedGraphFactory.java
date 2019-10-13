/**
 * 
 */
package ru.krushnyakov.natera.lib;

import java.util.Set;

/**
 * @author kkrushnyakov
 *
 */
public class UnsynchronizedGraphFactory implements GraphFactory  {

    @Override
    public <V> Graph<V> createUndirectedGraph() {
        return new UndirectedGraph<V>();
    }

    @Override
    public <V> Graph<V> createUndirectedGraph(Set<V> vertices, Set<Edge<V>> edges) {
        return new UndirectedGraph<V>(vertices, edges);
    }

    @Override
    public <V> Graph<V> createDirectedGraph() {
        return new DirectedGraph<V>();
    }

    @Override
    public <V> Graph<V> createDirectedGraph(Set<V> vertices, Set<Edge<V>> edges) {
        return new DirectedGraph<V>(vertices, edges);
    }

    
}
