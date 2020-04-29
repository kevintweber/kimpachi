package com.kevintweber.kimpachi.board;

import lombok.Data;
import lombok.NonNull;

import java.util.Comparator;
import java.util.Set;

/**
 * A point associated with a stone.
 */
@Data
public final class Position implements Points, Comparable<Position> {

    private final Stone stone;
    private final Point point;

    private Position(
            @NonNull Stone stone,
            @NonNull Point point) {
        this.stone = stone;
        this.point = point;
    }

    public static Position copyOf(@NonNull Position position) {
        return new Position(position.stone, position.point);
    }

    public static Position of(
            @NonNull Stone stone,
            @NonNull Point point) {
        return new Position(stone, point);
    }

    @Override
    public boolean contains(@NonNull Point point) {
        return this.point.equals(point);
    }

    @Override
    public Set<Point> getPoints() {
        return Set.of(point);
    }

    @Override
    public Set<Point> getNeighboringPoints() {
        return point.getNeighboringPoints();
    }

    @Override
    public boolean isAdjacent(@NonNull Point otherPoint) {
        return point.isAdjacent(otherPoint);
    }

    public boolean isAdjacent(@NonNull Position position) {
        return point.isAdjacent(position.getPoint());
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isIntersecting(@NonNull Points otherGroup) {
        return otherGroup.contains(point);
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public int compareTo(Position o) {
        if (o == null) {
            return -1;
        }

        return Comparator.comparing(Position::getStone)
                .thenComparing(Position::getPoint)
                .compare(this, o);
    }

    @Override
    public String toString() {
        String result = stone.equals(Stone.Black) ? "B[" : "W[";

        return result + point.toString() + "]";
    }
}
