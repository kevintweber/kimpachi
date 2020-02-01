package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.exception.NonAdjacentPositionException;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.builder.GraphBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * A Group is a set of connected stones of the same color.
 */
@EqualsAndHashCode
@ToString
public final class Group implements Stones {

    private final Stone stone;
    private final Graph<Position, DefaultEdge> positions;

    private Group(
            @NonNull Stone stone,
            @NonNull Position position) {
        this.stone = stone;
        this.positions = new SimpleGraph<>(DefaultEdge.class);
        this.positions.addVertex(position);
    }

    private Group(
            @NonNull Stone stone,
            @NonNull Graph<Position, DefaultEdge> positions) {
        this.stone = stone;
        this.positions = new GraphBuilder<>(positions)
                .build();
    }

    public static Group of(
            @NonNull Stone stone,
            @NonNull Position position) {
        return new Group(stone, position);
    }

    public static Group copyOf(@NonNull Group otherGroup) {
        return new Group(otherGroup.stone, otherGroup.positions);
    }

    @Override
    public boolean contains(@NonNull Position position) {
        return positions.vertexSet().contains(position);
    }

    @Override
    public int count() {
        return positions.vertexSet().size();
    }

    @Override
    public Stone getStone() {
        return stone;
    }

    @Override
    public Set<Position> getPositions() {
        return new HashSet<>(positions.vertexSet());
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    private Set<Position> getAdjacentPositions(Position position) {
        Set<Position> adjacentPositions = new HashSet<>();
        for (Position innerPosition : positions.vertexSet()) {
            if (innerPosition.isAdjacent(position)) {
                adjacentPositions.add(innerPosition);
            }
        }

        return adjacentPositions;
    }

    public Group with(@NonNull Position position) {
        if (contains(position)) {
            return this;
        }

        Set<Position> adjacentPositions = getAdjacentPositions(position);
        if (adjacentPositions.isEmpty()) {
            throw new NonAdjacentPositionException("Position is non-adjacent: " + position);
        }

        Graph<Position, DefaultEdge> enlargedGroup = new GraphBuilder<>(positions).build();
        enlargedGroup.addVertex(position);
        for (Position adjacentPosition : adjacentPositions) {
            enlargedGroup.addEdge(adjacentPosition, position);
        }

        return new Group(stone, enlargedGroup);
    }
}
