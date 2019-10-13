package ru.krushnyakov.natera.lib;

import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;

public class SynchronizedGraphFactory implements GraphFactory {

    @Override
    public <V> Graph<V> createUndirectedGraph() {
        return new SynchronizedGraphDecorator<>(new UndirectedGraph<>());
    }

    @Override
    public <V> Graph<V> createUndirectedGraph(Set<V> vertices, Set<Edge<V>> edges) {
        // TODO Auto-generated method stub
        return new SynchronizedGraphDecorator<>(new UndirectedGraph<>(vertices, edges));
    }

    @Override
    public <V> Graph<V> createDirectedGraph() {
        return new SynchronizedGraphDecorator<>(new DirectedGraph<>());
    }

    @Override
    public <V> Graph<V> createDirectedGraph(Set<V> vertices, Set<Edge<V>> edges) {
        return new SynchronizedGraphDecorator<>(new DirectedGraph<>(vertices, edges));
    }

    
private class SynchronizedGraphDecorator<V> implements Graph<V> {
    
        private Graph<V> graph;
        
        private ReadWriteLock lock = new ReentrantReadWriteLock();

        public SynchronizedGraphDecorator(Graph<V> graph) {
            this.graph = graph;
        }
        
        public void addVertex(V vertex) {
            lock.writeLock().lock();
            try {
                graph.addVertex(vertex);
            } finally {
                lock.writeLock().unlock();
            }
        }

        @Override
        public void addEdge(Edge<V> edge) {
            lock.writeLock().lock();
            try {
                graph.addEdge(edge);
            } finally {
                lock.writeLock().unlock();
            }
        }

        @Override
        public List<Edge<V>> getPath(V sourceVertex, V destinationVertex) {
            lock.writeLock().lock();
            try {
                return graph.getPath(sourceVertex, destinationVertex);
            } finally {
                lock.writeLock().unlock();
            }
        }

        @Override
        public List<?> traverse(Function<V, ?> function) {
            lock.writeLock().lock();
            try {
                return graph.traverse(function);
            } finally {
                lock.writeLock().unlock();
            }

        }

    }
}
