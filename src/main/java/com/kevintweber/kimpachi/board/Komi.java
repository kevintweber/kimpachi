package com.kevintweber.kimpachi.board;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;

public final class Komi {

    private final ImmutableSet<Point> komiPoints;

    public static final Komi KOMI = new Komi();

    private Komi() {
        this.komiPoints = ImmutableSet.<Point>builder()
                .add(Point.of(4, 4))
                .add(Point.of(10, 4))
                .add(Point.of(16, 4))
                .add(Point.of(4, 10))
                .add(Point.of(10, 10))
                .add(Point.of(16, 10))
                .add(Point.of(4, 16))
                .add(Point.of(10, 16))
                .add(Point.of(16, 16))
                .build();
    }

    public boolean isKomi(@NonNull Point point) {
        return komiPoints.contains(point);
    }

    @Override
    public String toString() {
        return "Komi{}";
    }
}
