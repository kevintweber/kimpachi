package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.exception.InvalidPositionException;
import com.kevintweber.kimpachi.exception.SgfException;
import lombok.Data;
import lombok.NonNull;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * A coordinate on the board.
 */
@Data
public final class Point implements Comparable<Point>, Points, Printable {

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

    @Override
    public boolean contains(@NonNull Point point) {
        return point.equals(this);
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public Set<Point> getPoints() {
        return Set.of(this);
    }

    @Override
    public Set<Point> getNeighboringPoints() {
        Set<Point> pointSet = new HashSet<>();
        addPositionToPointSet(pointSet, x + 1, y);
        addPositionToPointSet(pointSet, x - 1, y);
        addPositionToPointSet(pointSet, x, y + 1);
        addPositionToPointSet(pointSet, x, y - 1);

        return pointSet;
    }

    private void addPositionToPointSet(Set<Point> points, int x, int y) {
        try {
            Point point = Point.of(x, y);
            points.add(point);
        } catch (Exception e) {
            // Do nothing.
        }
    }

    @Override
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
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isIntersecting(@NonNull Points otherGroup) {
        return otherGroup.contains(this);
    }

    @Override
    public String print() {
        Komi komi = Komi.KOMI;
        StringBuilder sb = new StringBuilder("    ");
        for (int i = 0; i < 19; i++) {
            sb.append(Board.positionCharacters.charAt(i));
            sb.append(" ");
        }

        sb.append("\n");

        for (int y = 19; y >= 1; y--) {
            sb.append(y);
            sb.append("  ");
            if (y < 10) {
                sb.append(" ");
            }

            for (int x = 1; x <= 19; x++) {
                Point point = Point.of(x, y);
                if (equals(point)) {
                    sb.append("# ");
                } else {
                    if (komi.isKomi(point)) {
                        sb.append("+ ");
                    } else {
                        sb.append(". ");
                    }
                }
            }

            sb.append("\n");
        }

        return sb.toString();
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
