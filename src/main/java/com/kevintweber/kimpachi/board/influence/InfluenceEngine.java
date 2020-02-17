package com.kevintweber.kimpachi.board.influence;

import com.kevintweber.kimpachi.board.Board;
import com.kevintweber.kimpachi.board.Color;
import com.kevintweber.kimpachi.board.Point;
import com.kevintweber.kimpachi.board.Stone;
import com.kevintweber.kimpachi.board.influence.algorithm.InfluenceAlgorithm;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class InfluenceEngine {

    private final InfluenceAlgorithm algorithm;

    public InfluenceEngine(@NonNull InfluenceAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public Map<Point, Integer> calculate(
            @NonNull Stone stone,
            @NonNull Board board) {
        Map<Point, Integer> results = new HashMap<>();
        Set<Point> emptyPoints = board.getPoints(Color.Empty);
        for (Point emptyPoint : emptyPoints) {
            results.put(emptyPoint, 0);
        }

        for (Point coloredPoint : board.getPoints(Color.of(stone))) {
            results.put(coloredPoint, 100);
            for (Point emptyPoint : emptyPoints) {
                int value = results.get(emptyPoint);
                int newValue = value + algorithm.getInfluence(coloredPoint, emptyPoint);
                results.replace(emptyPoint, newValue);
            }
        }

        return results;
    }


}
