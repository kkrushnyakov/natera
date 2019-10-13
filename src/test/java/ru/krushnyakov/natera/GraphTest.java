package ru.krushnyakov.natera;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import ru.krushnyakov.natera.lib.DirectedEdge;
import ru.krushnyakov.natera.lib.Graph;
import ru.krushnyakov.natera.lib.SimpleGraphLib;
import ru.krushnyakov.natera.lib.UndirectedEdge;

public class GraphTest {

    enum TestVertex {
        A, B, C, D, E, F, G;
    }

    @Test
    public void addVertexTest() {
        Graph<TestVertex> testUndirectedGraph = SimpleGraphLib.getGraphFactory().createUndirectedGraph();
        testUndirectedGraph.addVertex(TestVertex.G);
    }

    @Test
    public void addEdgeTest() {
        Graph<TestVertex> testUndirectedGraph = SimpleGraphLib.getSynchronyzedGraphFactory().createDirectedGraph();
        testUndirectedGraph.addEdge(new UndirectedEdge<>(TestVertex.A, TestVertex.E));
        testUndirectedGraph.addEdge(new DirectedEdge<>(TestVertex.A, TestVertex.C, 10));
    }

    
    @Test
    public void getPathSingleVertexTest() {
        
/*
         
         A
         
*/
        
        Graph<TestVertex> graph = SimpleGraphLib.getGraphFactory().createDirectedGraph(
                new HashSet<>(Arrays.asList(TestVertex.A)),
                new HashSet<>());
        
        assertEquals(Arrays.asList(), graph.getPath(TestVertex.A, TestVertex.A));

    }
    
    @Test
    public void getPathSingleEdgeAndStringVertexTest() {
/*
        A--10--B   C

*/
      
      Graph<String> graph = SimpleGraphLib.getGraphFactory().createDirectedGraph(
              new HashSet<>(Arrays.asList("A", "B", "C")),
              new HashSet<>(Arrays.asList(
                                          new UndirectedEdge<>("A", "B", 10)
                                          )));
      
      assertEquals(Arrays.asList(new UndirectedEdge<>("A", "B", 10)
                                  ), graph.getPath("A", "B"));
        
    }
  
    
    @Test
    public void getPathUndirectedEdgesGraphTest() {

        /*            
                      +----10----+
                      |          |
             +--------A----7-----D--+       G
             |        |             |
             |        |             |
             12       5             |
             |        |             |
             |        |             |
             E----3---B-----+       |
             |        |     |       15
             |        |     10      |
             5        7     |       |
             |        |     |       |
             |        |     |       |
             C---7----F-----+-------+   
         */
        
        Graph<TestVertex> graph = SimpleGraphLib.getGraphFactory().createDirectedGraph(
                new HashSet<>(Arrays.asList(TestVertex.A, TestVertex.B, TestVertex.C, TestVertex.E, TestVertex.D, TestVertex.F, TestVertex.G)),
                new HashSet<>(Arrays.asList(
                                            new UndirectedEdge<>(TestVertex.A, TestVertex.B, 5),
                                            new UndirectedEdge<>(TestVertex.A, TestVertex.D, 7),
                                            new UndirectedEdge<>(TestVertex.A, TestVertex.D, 10),
                                            new UndirectedEdge<>(TestVertex.A, TestVertex.E, 12),
                                            new UndirectedEdge<>(TestVertex.B, TestVertex.E, 3),
                                            new UndirectedEdge<>(TestVertex.B, TestVertex.F, 7),
                                            new UndirectedEdge<>(TestVertex.B, TestVertex.F, 10),
                                            new UndirectedEdge<>(TestVertex.E, TestVertex.C, 5),
                                            new UndirectedEdge<>(TestVertex.C, TestVertex.F, 7),
                                            new UndirectedEdge<>(TestVertex.D, TestVertex.F, 15)
                                            )));
        
        assertEquals(Arrays.asList(new UndirectedEdge<>(TestVertex.A, TestVertex.B, 5),
                                   new UndirectedEdge<>(TestVertex.B, TestVertex.E, 3),
                                   new UndirectedEdge<>(TestVertex.E, TestVertex.C, 5)
                                    ), graph.getPath(TestVertex.A, TestVertex.C));

    }
        

    @Test
    public void getPathUndirectedAndDirectedEdgesGraphTest() {
        
/*            
                 +----10----+
                 |          |
        +--------A----7-----D--+        
        |        |             |
        |        |             |
        12       5             |
        |        |             |
        |        |             |
        E>---3-->B>----+       |
        |        |     |       15
        |        |     10      |
        5        7     |       |
        |        |     |       |
        |        |     |       |
        C---7----F<----+-------+   
*/

        Graph<TestVertex> graph = SimpleGraphLib.getGraphFactory().createDirectedGraph(
                new HashSet<>(Arrays.asList(TestVertex.A, TestVertex.B, TestVertex.C, TestVertex.E, TestVertex.D, TestVertex.F)),
                new HashSet<>(Arrays.asList(new UndirectedEdge<>(TestVertex.A, TestVertex.B, 5),
                                            new UndirectedEdge<>(TestVertex.A, TestVertex.D, 7),
                                            new UndirectedEdge<>(TestVertex.A, TestVertex.D, 10),
                                            new UndirectedEdge<>(TestVertex.A, TestVertex.E, 12),
                                            new DirectedEdge<>(TestVertex.E, TestVertex.B, 3),
                                            new UndirectedEdge<>(TestVertex.B, TestVertex.F, 7),
                                            new DirectedEdge<>(TestVertex.B, TestVertex.F, 10),
                                            new UndirectedEdge<>(TestVertex.E, TestVertex.C, 5),
                                            new UndirectedEdge<>(TestVertex.C, TestVertex.F, 7),
                                            new UndirectedEdge<>(TestVertex.D, TestVertex.F, 15))));

        assertEquals(Arrays.asList(new UndirectedEdge<>(TestVertex.A, TestVertex.E, 12),
                                   new UndirectedEdge<>(TestVertex.E, TestVertex.C, 5)), graph.getPath(TestVertex.A, TestVertex.C));



    }

/*
      +---1---+
      |       |
      A>--1-->B
     
 */
    
    @Test
    public void oneMoreTestWithStringsAndNoWeights() {
        Graph<String> graph = SimpleGraphLib.getGraphFactory().createDirectedGraph(new HashSet<>(Arrays.asList("A", "B")), new HashSet<>(Arrays.asList(new UndirectedEdge<>("A", "B"), new DirectedEdge<>("A", "B"))));
        
        assertEquals(Arrays.asList(new UndirectedEdge<>("A", "B")), graph.getPath("B", "A"));
    }
    
    @Test
    public void traverseTest() {
        Graph<String> graph =  SimpleGraphLib.getGraphFactory().createDirectedGraph(new HashSet<>(Arrays.asList("A", "B")), new HashSet<>(Arrays.asList(new UndirectedEdge<>("A", "B"), new DirectedEdge<>("A", "B"))));
        
        assertEquals(Arrays.asList("AA", "BB"), graph.traverse(v -> v.toString() + v.toString()));
    }

    
/*
    +---1---+
    |       |
    A>--1-->B
   
*/
  
  @Test
  public void synchronizedGraphTest() {
      Graph<String> graph =  SimpleGraphLib.getSynchronyzedGraphFactory().createDirectedGraph(new HashSet<>(Arrays.asList("A", "B")), new HashSet<>(Arrays.asList(new UndirectedEdge<>("A", "B"), new DirectedEdge<>("A", "B"))));
      
      assertEquals(Arrays.asList(new UndirectedEdge<>("A", "B")), graph.getPath("B", "A"));
  }

  /*
    +---1---+
    |       |
    A>--1-->B
   
   */
  
  @Test
  public void synchronizedUndirectedGraphTest() {
      Graph<String> graph =  SimpleGraphLib.getSynchronyzedGraphFactory().createUndirectedGraph(new HashSet<>(Arrays.asList("A", "B")), new HashSet<>(Arrays.asList(new UndirectedEdge<>("A", "B"), new UndirectedEdge<>("A", "B"))));
      
      assertEquals(Arrays.asList(new UndirectedEdge<>("A", "B")), graph.getPath("B", "A"));
  }
  
}
