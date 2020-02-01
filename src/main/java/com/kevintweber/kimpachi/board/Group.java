package com.kevintweber.kimpachi.board;

import com.google.common.graph.ElementOrder;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.ImmutableGraph;
import com.google.common.graph.MutableGraph;
import com.kevintweber.kimpachi.exception.NonAdjacentPositionException;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

/**
 * A Group is a set of always connected stones of the same color.
 */
@EqualsAndHashCode
@ToString
public final class Group implements Stones {

    private final Stone stone;
    private final ImmutableGraph<Position> positions;

    private Group(
            @NonNull Stone stone,
            @NonNull Position position) {
        this.stone = stone;
        this.positions = GraphBuilder.undirected()
                .allowsSelfLoops(false)
                .<Position>nodeOrder(ElementOrder.natural())
                .immutable()
                .addNode(position)
                .build();
    }

    private Group(
            @NonNull Stone stone,
            @NonNull ImmutableGraph<Position> positions) {
        this.stone = stone;
        if (positions.isDirected()) {
            throw new IllegalArgumentException("Cannot construct Group from directed graph");
        }

        this.positions = positions;
    }

    private Group(
            @NonNull Stone stone,
            @NonNull MutableGraph<Position> positions) {
        this.stone = stone;
        if (positions.isDirected()) {
            throw new IllegalArgumentException("Cannot construct Group from directed graph");
        }

        this.positions = GraphBuilder.from(positions)
                .<Position>immutable()
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
        return positions.nodes().contains(position);
    }

    @Override
    public int count() {
        return positions.nodes().size();
    }

    @Override
    public Stone getStone() {
        return stone;
    }

    @Override
    public Set<Position> getPositions() {
        return positions.nodes();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    private Set<Position> getAdjacentPositions(Position position) {
        Set<Position> adjacentPositions = new HashSet<>();
        for (Position innerPosition : positions.nodes()) {
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

        MutableGraph<Position> enlargedGroup = GraphBuilder.from(positions)
                .build();
        enlargedGroup.addNode(position);
        for (Position adjacentPosition : adjacentPositions) {
            enlargedGroup.putEdge(adjacentPosition, position);
        }

        return new Group(stone, enlargedGroup);
    }
}
