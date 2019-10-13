package ru.krushnyakov.natera;

import java.util.Arrays;
import java.util.HashSet;

import ru.krushnyakov.natera.lib.DirectedEdge;
import ru.krushnyakov.natera.lib.Graph;
import ru.krushnyakov.natera.lib.SimpleGraphLib;
import ru.krushnyakov.natera.lib.UndirectedEdge;

/*

Should support 2 types of graphs - directed and undirected with 3 operations:

 addVertex - adds vertex to the graph

 addEdge - adds edge to the graph

 getPath - returns a list of edges between 2 vertices (path doesn’t have to be optimal)

 Vertices should be of a user defined type.

 Questions to be ready to answer (don’t have to implement):

 Add weighted edges support in your lib.

Add traverse function that will take a user defined function and apply it on every vertex of the graph.

Make you graphs thread safe.


FAQ:
Нужно ли реализовывать базу данных для графов , нужны ли тесты, tdd?
Базу не нужно, а вот тесты желательны, но на самом деле на Ваше усмотрение.


 * 
 */
public class SimpleGraphLibDemo {
    
    public static void main(String[] args) {

        Graph<String> graph = SimpleGraphLib.getGraphFactory().createDirectedGraph(new HashSet<>(Arrays.asList("A", "B")), new HashSet<>(Arrays.asList(new UndirectedEdge<>("A", "B"), new DirectedEdge<>("A", "B"))));
        
        System.out.println(graph.getPath("B", "A"));

        Graph<String> synchGraph = SimpleGraphLib.getSynchronyzedGraphFactory().createUndirectedGraph(new HashSet<>(Arrays.asList("A", "B")), new HashSet<>(Arrays.asList(new UndirectedEdge<>("A", "B"))));
        
        System.out.println(synchGraph.getPath("B", "A"));

    }

}
