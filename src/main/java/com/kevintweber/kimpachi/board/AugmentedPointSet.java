package com.kevintweber.kimpachi.board;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class AugmentedPointSet<T> implements Points {

    private final ImmutableMap<Point, T> points;

    private AugmentedPointSet(
            @NonNull Point point,
            @NonNull T value) {
        this.points = ImmutableMap.of(point, value);
    }

    private AugmentedPointSet(
            @NonNull Map<Point, T> pointMap) {
        this.points = ImmutableMap.copyOf(pointMap);
    }

    public static <T> AugmentedPointSet<T> of(
            @NonNull Point point,
            @NonNull T value) {
        return new AugmentedPointSet<>(point, value);
    }

    public static <T> AugmentedPointSet<T> of(@NonNull Map<Point, T> points) {
        return new AugmentedPointSet<>(points);
    }

    @Override
    public boolean contains(@NonNull Point point) {
        return points.containsKey(point);
    }

    @Override
    public Set<Point> getPoints() {
        return points.keySet();
    }

    public T get(@NonNull Point point, T defaultValue) {
        if (contains(point)) {
            return points.get(point);
        }

        return defaultValue;
    }

    public Map<Point, T> getPointMap() {
        return points;
    }

    @Override
    public Set<Point> getNeighboringPoints() {
        PointSet pointSet = PointSet.of(points.keySet());

        return pointSet.getNeighboringPoints();
    }

    @Override
    public boolean isAdjacent(@NonNull Point point) {
        PointSet pointSet = PointSet.of(points.keySet());

        return pointSet.isAdjacent(point);
    }

    @Override
    public boolean isEmpty() {
        return points.isEmpty();
    }

    @Override
    public boolean isIntersecting(@NonNull Points otherPointSet) {
        PointSet pointSet = PointSet.of(points.keySet());

        return pointSet.isIntersecting(otherPointSet);
    }

    @Override
    public int size() {
        return points.size();
    }

    public AugmentedPointSet<T> with(
            @NonNull Point point,
            @NonNull T value) {
        Map<Point, T> pointMap = new HashMap<>(points);
        pointMap.put(point, value);

        return new AugmentedPointSet<>(pointMap);
    }

    public AugmentedPointSet<T> without(@NonNull Point point) {
        if (!points.containsKey(point)) {
            return this;
        }

        Map<Point, T> pointMap = new HashMap<>(points);
        pointMap.remove(point);

        return new AugmentedPointSet<>(pointMap);
    }
}
