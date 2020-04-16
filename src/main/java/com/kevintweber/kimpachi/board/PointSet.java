package com.kevintweber.kimpachi.board;

import com.google.common.collect.ImmutableSet;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

/**
 * A PointSet is a set of points.
 */
@EqualsAndHashCode
@ToString
public final class PointSet implements Points {

    private final ImmutableSet<Point> points;

    private PointSet(@NonNull Point point) {
        this.points = ImmutableSet.of(point);
    }

    private PointSet(@NonNull Set<Point> points) {
        this.points = ImmutableSet.copyOf(points);
    }

    public static PointSet of(@NonNull Point point) {
        return new PointSet(point);
    }

    public static PointSet of(@NonNull Set<Point> points) {
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
        return false;
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

    public PointSet with(Point point) {
        if (contains(point)) {
            return this;
        }

        Set<Point> enlargedPointSet = new HashSet<>(points);
        enlargedPointSet.add(point);

        return new PointSet(enlargedPointSet);
    }

    public PointSet without(Point point) {
        if (!contains(point)) {
            return this;
        }

        Set<Point> reducedPointSet = new HashSet<>(points);
        reducedPointSet.remove(point);

        return new PointSet(reducedPointSet);
    }
}
