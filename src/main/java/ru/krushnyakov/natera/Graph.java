/**
 * 
 */
package ru.krushnyakov.natera;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kkrushnyakov
 * @param <V> vertices type 
 *
 */
public class Graph<V> {

    protected static Logger log = LoggerFactory.getLogger(Graph.class);;

    protected Set<V> vertices;

    protected Set<Edge<V>> edges;

    protected ReadWriteLock lock = new ReentrantReadWriteLock();

    public Graph() {
        super();
        this.vertices = new HashSet<V>();
        this.edges = new HashSet<Edge<V>>();
    }

    public Graph(Set<V> vertices, Set<Edge<V>> edges) {

        if (vertices == null || edges == null) {
            throw new IllegalArgumentException("Edges and Verticles can't be null!");
        }
        this.vertices = vertices;
        this.edges = edges;
    }

    public void addVertex(V vertex) {

        lock.writeLock().lock();
        try {
            vertices.add(vertex);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void addEdge(Edge<V> edge) {
        lock.writeLock().lock();
        try {
            edges.add(edge);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Implements https://en.wikipedia.org/wiki/Dijkstra's_algorithm 
     * 
     * <pre>
     * In the following algorithm, the code u ← vertex in Q with min dist[u], searches for the vertex u in the vertex set Q that has the least dist[u] value. length(u, v) returns the length of the edge joining (i.e. the distance between) the two neighbor-nodes u and v. The variable alt on line 17 is the length of the path from the root node to the neighbor node v if it were to go through u. If this path is shorter than the current shortest path recorded for v, that current path is replaced with this alt path. The prev array is populated with a pointer to the "next-hop" node on the source graph to get the shortest route to the source.
     *
     *
     * 1  function Dijkstra(Graph, source):
     * 2
     * 3      create vertex set Q
     * 4
     * 5      for each vertex v in Graph:             // Initialization
     * 6          dist[v] ← INFINITY                  // Unknown distance from source to v
     * 7          prev[v] ← UNDEFINED                 // Previous node in optimal path from source
     * 8          add v to Q                          // All nodes initially in Q (unvisited nodes)
     * 9
     * 10      dist[source] ← 0                        // Distance from source to source
     * 11
     * 12      while Q is not empty:
     * 13          u ← vertex in Q with min dist[u]    // Node with the least distance will be selected first
     * 14          remove u from Q
     * 15
     * 16          for each neighbor v of u:           // where v is still in Q.
     * 17              alt ← dist[u] + length(u, v)
     * 18              if alt < dist[v]:               // A shorter path to v has been found
     * 19                  dist[v] ← alt
     * 20                  prev[v] ← u
     * 21
     * 22      return dist[], prev[]
     * 
     * If we are only interested in a shortest path between vertices source and target,
     *  we can terminate the search after line 13 if u = target. Now we can read the shortest path from source to target by reverse iteration:
     *  
     *  1  S ← empty sequence
     *  2  u ← target
     *  3  while prev[u] is defined:                  // Construct the shortest path with a stack S
     *  4      insert u at the beginning of S         // Push the vertex onto the stack
     *  5      u ← prev[u]                            // Traverse from target to source
     *  6  insert u at the beginning of S             // Push the source onto the stack
     *  
     *  Now sequence S is the list of vertices constituting one of the shortest paths from source to target, or the empty sequence if no path exists. 
     * 
     * </pre>
     * 
     * @param sourceVertex
     * @param destinationVertex
     * @return List of edges contained in the result path
     */

    public List<Edge<V>> getPath(V sourceVertex, V destinationVertex) {

        lock.readLock().lock();
        try {

            log.debug("Graph verticies: {}", vertices);
            log.debug("Graph edges {}", edges);

            List<Edge<V>> result = new ArrayList<>();
            if (sourceVertex == null || destinationVertex == null) {
                throw new IllegalArgumentException("Vertex can't be null");
            }
            if (sourceVertex.equals(destinationVertex)) {
                return result;
            }
            Set<V> unvisitedVertices = new HashSet<>();
            Map<V, Integer> verticesDistances = new HashMap<>();
            Map<V, V> previousVertices = new HashMap<>();

            vertices.forEach(v -> {
                unvisitedVertices.add(v);
                verticesDistances.put(v, Integer.MAX_VALUE);
                previousVertices.put(v, null);
            });

            verticesDistances.put(sourceVertex, 0);

            while (!unvisitedVertices.isEmpty()) {
                V minimumDistanceUnvisited = Collections.min(unvisitedVertices,
                        (e1, e2) -> Integer.compare(verticesDistances.get(e1), verticesDistances.get(e2)));
                if (minimumDistanceUnvisited.equals(destinationVertex))
                    break;
                unvisitedVertices.remove(minimumDistanceUnvisited);

                unvisitedNeighboursOf(minimumDistanceUnvisited, unvisitedVertices).forEach(v -> {
                    int distance = verticesDistances.get(minimumDistanceUnvisited)
                            + shortestEdgeBetween(minimumDistanceUnvisited, v).getWeight();
                    if (distance < verticesDistances.get(v)) {
                        verticesDistances.put(v, distance);
                        previousVertices.put(v, minimumDistanceUnvisited);
                    }
                });
            }

            log.debug("verticesDistances = {}", verticesDistances);

            V v = destinationVertex;
            List<V> pathVertices = new ArrayList<>();

            do {
                pathVertices.add(0, v);
                v = previousVertices.get(v);
            } while (v != null);

            log.debug("previousVertices = {}", previousVertices);
            log.debug("pathVertices = {}", pathVertices);

            if (pathVertices.isEmpty())
                return result;
            v = pathVertices.get(0);

            for (int i = 0; i < pathVertices.size() - 1; i++) {
                result.add(shortestEdgeBetween(pathVertices.get(i), pathVertices.get(i + 1)));
            }

            return result;
        } finally {
            lock.readLock().unlock();
        }
    }

    private List<V> unvisitedNeighboursOf(V v, Set<V> unvisitedVertices) {

        return edges.stream().filter(e -> e.startsAt(v)).map(e -> e.getOtherVertex(v)).distinct()
                .filter(vv -> unvisitedVertices.contains(vv)).collect(Collectors.toList());
    }

    private Edge<V> shortestEdgeBetween(V vertexA, V vertexB) {
        return Collections.min(edges.stream().filter(e -> e.connectsVertices(vertexA, vertexB)).collect(Collectors.toList()),
                (e1, e2) -> Integer.compare(e1.getWeight(), e2.getWeight()));
    }

    public List<?> traverse(Function<V, ?> function) {
        
        return vertices.stream().map(function).collect(Collectors.toList());
        
    }
    
}
