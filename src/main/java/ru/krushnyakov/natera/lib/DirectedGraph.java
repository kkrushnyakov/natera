/**
 * 
 */
package ru.krushnyakov.natera.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * https://en.wikipedia.org/wiki/Directed_graph
 * 
 * @author kkrushnyakov
 * @param <V> vertices type 
 *
 */
public class DirectedGraph<V> implements Graph<V> {

    protected Set<V> vertices;

    protected Set<Edge<V>> edges;

    protected Map<V, Set<Edge<V>>> outgoingEdges = new HashMap<>();

    protected Map<V, Set<Edge<V>>> incomingEdges = new HashMap<>();;

    protected DirectedGraph() {
        super();
        this.vertices = new HashSet<V>();
        this.edges = new HashSet<Edge<V>>();
    }

    protected DirectedGraph(Set<V> vertices, Set<Edge<V>> edges) {

        if (vertices == null || edges == null) {
            throw new IllegalArgumentException("Edges and Verticles can't be null!");
        }
        this.vertices = vertices;
        this.edges = edges;

        this.edges.forEach(e -> {
            this.vertices.add(e.getSource());
            this.vertices.add(e.getDestination());

            if (!this.outgoingEdges.containsKey(e.getSource())) {
                this.outgoingEdges.put(e.getSource(), new HashSet<Edge<V>>(Arrays.asList(e)));
            } else {
                this.outgoingEdges.get(e.getSource()).add(e);
            }
            if (!this.incomingEdges.containsKey(e.getDestination())) {
                this.incomingEdges.put(e.getDestination(), new HashSet<Edge<V>>(Arrays.asList(e)));
            } else {
                this.incomingEdges.get(e.getDestination()).add(e);
            }
            if (e.startsAt(e.getDestination())) {
                if (!this.outgoingEdges.containsKey(e.getDestination())) {
                    this.outgoingEdges.put(e.getDestination(), new HashSet<Edge<V>>(Arrays.asList(e)));
                } else {
                    this.outgoingEdges.get(e.getDestination()).add(e);
                }
                if (!this.incomingEdges.containsKey(e.getSource())) {
                    this.incomingEdges.put(e.getSource(), new HashSet<Edge<V>>(Arrays.asList(e)));
                } else {
                    this.incomingEdges.get(e.getSource()).add(e);
                }
            }
        });

    }

    @Override
    public Graph<V> addVertex(V vertex) {
        vertices.add(vertex);
        return this;
    }

    @Override
    public Graph<V> addEdge(Edge<V> edge) {
        edges.add(edge);
        this.vertices.add(edge.getSource());
        this.vertices.add(edge.getDestination());
        if (!this.outgoingEdges.containsKey(edge.getSource())) {
            this.outgoingEdges.put(edge.getSource(), new HashSet<Edge<V>>(Arrays.asList(edge)));
        } else {
            this.outgoingEdges.get(edge.getSource()).add(edge);
        }
        if (!this.incomingEdges.containsKey(edge.getDestination())) {
            this.incomingEdges.put(edge.getDestination(), new HashSet<Edge<V>>(Arrays.asList(edge)));
        } else {
            this.incomingEdges.get(edge.getDestination()).add(edge);
        }
        if (edge.startsAt(edge.getDestination())) {
            if (!this.outgoingEdges.containsKey(edge.getDestination())) {
                this.outgoingEdges.put(edge.getDestination(), new HashSet<Edge<V>>(Arrays.asList(edge)));
            } else {
                this.outgoingEdges.get(edge.getDestination()).add(edge);
            }
            if (!this.incomingEdges.containsKey(edge.getSource())) {
                this.incomingEdges.put(edge.getSource(), new HashSet<Edge<V>>(Arrays.asList(edge)));
            } else {
                this.incomingEdges.get(edge.getSource()).add(edge);
            }
        }

        return this;
    }

    /**
     * Implements https://en.wikipedia.org/wiki/Dijkstra's_algorithm 
     * 
     * @param sourceVertex
     * @param destinationVertex
     * @return List of edges contained in the result path
     */

    @Override
    public List<Edge<V>> getPath(V sourceVertex, V destinationVertex) {

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

        V v = destinationVertex;
        List<V> pathVertices = new ArrayList<>();

        do {
            pathVertices.add(0, v);
            v = previousVertices.get(v);
        } while (v != null);

        if (pathVertices.isEmpty())
            return result;
        v = pathVertices.get(0);

        for (int i = 0; i < pathVertices.size() - 1; i++) {
            result.add(shortestEdgeBetween(pathVertices.get(i), pathVertices.get(i + 1)));
        }

        return result;
    }

    @Override
    public List<?> traverse(Function<V, ?> function) {

        return vertices.stream().map(function).collect(Collectors.toList());
    }

    private List<V> unvisitedNeighboursOf(V v, Set<V> unvisitedVertices) {
        if(outgoingEdges.get(v) == null) return new ArrayList<>();
        return outgoingEdges.get(v).stream().map(e -> e.getOtherVertex(v)).distinct().filter(vv -> unvisitedVertices.contains(vv))
                .collect(Collectors.toList());
    }

    private Edge<V> shortestEdgeBetween(V vertexA, V vertexB) {
        Set<Edge<V>> intersection = new HashSet<>(outgoingEdges.get(vertexA));
        intersection.retainAll(incomingEdges.get(vertexB));
        return Collections.min(intersection, (e1, e2) -> Integer.compare(e1.getWeight(), e2.getWeight()));
    }

}
