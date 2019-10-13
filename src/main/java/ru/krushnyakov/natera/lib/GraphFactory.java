package ru.krushnyakov.natera.lib;

import java.util.Set;

public interface GraphFactory {

    <V> Graph<V> createUndirectedGraph();
    
    <V> Graph<V> createUndirectedGraph(Set<V> vertices, Set<Edge<V>> edges);

    <V> Graph<V> createDirectedGraph();
     
    <V> Graph<V> createDirectedGraph(Set<V> vertices, Set<Edge<V>> edges);
     
}
