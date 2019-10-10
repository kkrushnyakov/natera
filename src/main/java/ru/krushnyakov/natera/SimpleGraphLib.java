package ru.krushnyakov.natera;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

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
@SpringBootApplication
public class SimpleGraphLib {

    public static void main(String[] args) {
        SpringApplication.run(SimpleGraphLib.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext context) {
        return args -> {

            Graph<String> graph = new Graph<>(new HashSet<>(Arrays.asList("A", "B")), new HashSet<>(Arrays.asList(new UndirectedEdge<>("A", "B"), new DirectedEdge<>("A", "B"))));
            
            System.out.print(graph.getPath("B", "A"));
        };
    }

}
