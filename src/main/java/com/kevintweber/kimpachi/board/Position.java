package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.exception.InvalidPositionException;
import lombok.Data;

import java.util.Comparator;

@Data
public final class Position implements Comparable<Position> {

    private static String sgfCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private final int x;
    private final int y;

    private Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Position of(int x, int y) {
        if (x <= 0 || y <= 0) {
            throw new InvalidPositionException("Invalid position: x=" + x + ";y=" + y);
        }

        return new Position(x, y);
    }

    public String toSgf() {
        return sgfCharacters.substring(x - 1, x) + sgfCharacters.substring(y - 1, y);
    }

    @Override
    public int compareTo(Position o) {
        if (o == null) {
            return -1;
        }

        return Comparator.comparing(Position::getX)
                .thenComparing(Position::getY)
                .compare(this, o);
    }
}
