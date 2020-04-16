package com.kevintweber.kimpachi.board;

import lombok.NonNull;

import java.util.Set;

public interface Points {

    boolean contains(@NonNull Point point);

    Set<Point> getPoints();

    Set<Point> getNeighboringPoints();

    boolean isAdjacent(@NonNull Point point);

    boolean isEmpty();

    boolean isIntersecting(@NonNull Points otherGroup);

    int size();

}
