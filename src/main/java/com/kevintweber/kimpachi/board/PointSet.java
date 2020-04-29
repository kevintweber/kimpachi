package com.kevintweber.kimpachi.board;

import com.google.common.collect.ImmutableSet;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

/**
 * A PointSet is a set of points.
 *
 * The PointSet can contain 0 or more points.  The points can be of any position.
 */
@EqualsAndHashCode
@ToString
public final class PointSet implements Points, Printable {

    private final ImmutableSet<Point> points;

    private static final PointSet EMPTY = new PointSet(Set.of());

    private PointSet(@NonNull Point point) {
        this.points = ImmutableSet.of(point);
    }

    private PointSet(@NonNull Set<Point> points) {
        this.points = ImmutableSet.copyOf(points);
    }

    public static PointSet empty() {
        return EMPTY;
    }

    public static PointSet of(@NonNull Point point) {
        return new PointSet(point);
    }

    public static PointSet of(@NonNull Set<Point> points) {
        if (points.isEmpty()) {
            return EMPTY;
        }

        return new PointSet(points);
    }

    @Override
    public boolean contains(@NonNull Point point) {
        return points.contains(point);
    }

    @Override
    public Set<Point> getPoints() {
        return points;
    }

    @Override
    public Set<Point> getNeighboringPoints() {
        Set<Point> neighboringPoints = new HashSet<>();
        for (Point point : points) {
            neighboringPoints.addAll(point.getNeighboringPoints());
        }

        neighboringPoints.removeAll(points);

        return neighboringPoints;
    }

    @Override
    public boolean isAdjacent(@NonNull Point otherPoint) {
        for (Point point : points) {
            if (point.isAdjacent(otherPoint)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isEmpty() {
        return points.isEmpty();
    }

    @Override
    public boolean isIntersecting(@NonNull Points otherPoints) {
        Set<Point> ownPoints = new HashSet<>(getPoints());
        Set<Point> otherPointsSet = otherPoints.getPoints();

        ownPoints.retainAll(otherPointsSet);

        return !ownPoints.isEmpty();
    }

    @Override
    public int size() {
        return points.size();
    }

    public PointSet with(@NonNull Point point) {
        if (contains(point)) {
            return this;
        }

        Set<Point> enlargedPointSet = new HashSet<>(points);
        enlargedPointSet.add(point);

        return new PointSet(enlargedPointSet);
    }

    public PointSet without(@NonNull Point point) {
        if (!contains(point)) {
            return this;
        }

        Set<Point> reducedPointSet = new HashSet<>(points);
        reducedPointSet.remove(point);

        if (reducedPointSet.isEmpty()) {
            return EMPTY;
        }

        return new PointSet(reducedPointSet);
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
                if (points.contains(point)) {
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
}
