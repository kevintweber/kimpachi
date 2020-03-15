package com.kevintweber.kimpachi.board.influence;

import com.google.common.collect.ImmutableMap;
import com.kevintweber.kimpachi.board.Board;
import com.kevintweber.kimpachi.board.Point;
import com.kevintweber.kimpachi.board.Stone;
import com.kevintweber.kimpachi.board.influence.engine.InfluenceEngine;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.Map;

@EqualsAndHashCode
public final class InfluenceArea {

    private final Stone stone;
    private final ImmutableMap<Point, Integer> influences;

    private InfluenceArea(
            @NonNull Stone stone,
            @NonNull Map<Point, Integer> influences) {
        this.stone = stone;
        this.influences = ImmutableMap.copyOf(influences);
    }

    public static InfluenceArea of(
            @NonNull Stone stone,
            @NonNull Board board,
            @NonNull InfluenceEngine engine) {
        return new InfluenceArea(stone, engine.calculate(stone, board));
    }

    public Stone getStone() {
        return stone;
    }

    public int getInfluence(@NonNull Point point) {
        if (influences.containsKey(point)) {
            return influences.get(point);
        }

        return 0;
    }

    @Override
    public String toString() {
        return "InfluenceGroup{" +
                hashCode() +
                '}';
    }
}
