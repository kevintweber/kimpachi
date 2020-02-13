package com.kevintweber.kimpachi.board;

import lombok.Data;
import lombok.NonNull;

import java.util.Comparator;

/**
 * A point associated with a stone color.
 */
@Data
public final class Position implements Comparable<Position> {

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

    public boolean isAdjacent(@NonNull Point otherPoint) {
        return point.isAdjacent(otherPoint);
    }

    public boolean isAdjacent(@NonNull Position position) {
        return point.isAdjacent(position.getPoint());
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
