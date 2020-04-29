package com.kevintweber.kimpachi.board.influence.engine;

import com.kevintweber.kimpachi.board.*;
import lombok.NonNull;

import java.util.Map;
import java.util.Set;

public final class BouzyEngine implements InfluenceEngine {

    private final int dilations;
    private final int erosions;
    private AugmentedPointSet<Integer> bouzyBoard;

    public BouzyEngine() {
        this.dilations = 5;
        this.erosions = 21;
        this.bouzyBoard = AugmentedPointSet.of(Map.of());
    }

    public BouzyEngine(int dilations, int erosions) {
        if (dilations < 0) {
            throw new IllegalArgumentException("Dilations count must be non-negative.");
        }

        if (erosions < 0) {
            throw new IllegalArgumentException("Erosions count must be non-negative.");
        }

        this.dilations = dilations;
        this.erosions = erosions;
        this.bouzyBoard = AugmentedPointSet.of(Map.of());
    }

    @Override
    public Map<Point, Integer> calculate(
            @NonNull Stone stone,
            @NonNull Board board) {
        if (board.isEmpty()) {
            return Map.of();
        }

        initialize(board);
        for (int d = 0; d < dilations; d++) {
            dilate();
        }

        for (int e = 0; e < erosions; e++) {
            erode();
        }

        return bouzyBoard.getPointMap();
    }

    private void dilate() {
        for (int x = 1; x <= 19; x++) {
            for (int y = 1; y <= 19; y++) {
                Point point = Point.of(x, y);
                int pointValue = bouzyBoard.get(point, 0);
                Set<Point> neighboringPointSet = point.getNeighboringPoints();
                int minValueOfNeighboringPointSet = neighboringPointSet.stream()
                        .mapToInt((p) -> bouzyBoard.get(p, 0))
                        .min()
                        .orElse(0);
                int maxValueOfNeighboringPointSet = neighboringPointSet.stream()
                        .mapToInt((p) -> bouzyBoard.get(p, 0))
                        .max()
                        .orElse(0);
                int newPointValue = pointValue;

                // Handle black
                if (pointValue >= 0 && minValueOfNeighboringPointSet >= 0) {
                    int countIntersections = (int) neighboringPointSet.stream()
                            .mapToInt((p) -> bouzyBoard.get(p, 0))
                            .filter((v) -> v > 0)
                            .count();
                    newPointValue += countIntersections;
                }

                // Handle white
                if (pointValue <= 0 && maxValueOfNeighboringPointSet <= 0) {
                    int countIntersections = (int) neighboringPointSet.stream()
                            .mapToInt((p) -> bouzyBoard.get(p, 0))
                            .filter((v) -> v < 0)
                            .count();
                    newPointValue -= countIntersections;
                }

                if (newPointValue == 0) {
                    bouzyBoard = bouzyBoard.remove(point);
                } else {
                    bouzyBoard = bouzyBoard.put(point, newPointValue);
                }
            }
        }
    }

    private void erode() {
        for (int x = 1; x <= 19; x++) {
            for (int y = 1; y <= 19; y++) {
                Point point = Point.of(x, y);
                int pointValue = bouzyBoard.get(point, 0);
                Set<Point> neighboringPointSet = point.getNeighboringPoints();
                int newPointValue = pointValue;

                // Handle black
                if (pointValue > 0) {
                    int countIntersections = (int) neighboringPointSet.stream()
                            .mapToInt((p) -> bouzyBoard.get(p, 0))
                            .filter((v) -> v <= 0)
                            .count();
                    if (countIntersections > pointValue) {
                        newPointValue = 0;
                    } else {
                        newPointValue -= countIntersections;
                    }
                }

                // Handle white
                if (pointValue < 0) {
                    int countIntersections = (int) neighboringPointSet.stream()
                            .mapToInt((p) -> bouzyBoard.get(p, 0))
                            .filter((v) -> v >= 0)
                            .count();
                    if (countIntersections > Math.abs(pointValue)) {
                        newPointValue = 0;
                    } else {
                        newPointValue += countIntersections;
                    }
                }

                if (newPointValue == 0) {
                    bouzyBoard = bouzyBoard.remove(point);
                } else {
                    bouzyBoard = bouzyBoard.put(point, newPointValue);
                }
            }
        }
    }

    private void initialize(Board board) {
        for (int x = 1; x <= 19; x++) {
            for (int y = 1; y <= 19; y++) {
                Point point = Point.of(x, y);
                Color color = board.getColor(point);
                if (color.equals(Color.Black)) {
                    bouzyBoard = bouzyBoard.put(point, 128);

                    continue;
                }

                if (color.equals(Color.White)) {
                    bouzyBoard = bouzyBoard.put(point, -128);
                }
            }
        }
    }
}
