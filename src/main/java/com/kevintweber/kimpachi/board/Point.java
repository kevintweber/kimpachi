package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.exception.InvalidPositionException;
import com.kevintweber.kimpachi.exception.SgfException;
import lombok.Data;
import lombok.NonNull;

import java.util.Comparator;

/**
 * A coordinate on the board.
 */
@Data
public final class Point implements Comparable<Point> {

    public final static String sgfCharacters = "abcdefghijklmnopqrs";

    private final int x;
    private final int y;

    private Point(int x, int y) {
        if (x <= 0 || y <= 0 || x > 19 || y > 19) {
            throw new InvalidPositionException("Invalid position: x=" + x + ";y=" + y);
        }

        this.x = x;
        this.y = y;
    }

    public static Point of(int x, int y) {
        return new Point(x, y);
    }

    public static Point fromSgf(@NonNull String sgfPosition) {
        if (sgfPosition.length() != 2) {
            throw new SgfException("Invalid SGF position string length. Value=" + sgfPosition);
        }

        String[] characters = sgfPosition.toLowerCase().split("");
        int x = sgfCharacters.indexOf(characters[0]) + 1;
        int y = sgfCharacters.indexOf(characters[1]) + 1;

        return of(x, y);
    }

    public boolean isAdjacent(@NonNull Point otherPoint) {
        if (equals(otherPoint)) {
            return false;
        }

        int otherX = otherPoint.getX();
        int otherY = otherPoint.getY();
        if (x == otherX && Math.abs(y - otherY) == 1) {
            return true;
        }

        return y == otherY && Math.abs(x - otherX) == 1;
    }

    @Override
    public int compareTo(Point o) {
        if (o == null) {
            return -1;
        }

        return Comparator.comparing(Point::getX)
                .thenComparing(Point::getY)
                .compare(this, o);
    }

    @Override
    public String toString() {
        return sgfCharacters.substring(x - 1, x) + sgfCharacters.substring(y - 1, y);
    }
}
