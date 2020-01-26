package com.kevintweber.kimpachi.board;

import lombok.Data;
import lombok.NonNull;

@Data
public final class Move {

    private final Color color;
    private final Position position;

    private Move(
            @NonNull Color color,
            @NonNull Position position) {
        this.color = color;
        this.position = position;
    }

    public static Move of(
            @NonNull Color color,
            @NonNull Position position) {
        if (color.equals(Color.Empty)) {
            throw new IllegalStateException("Move must be a color, not empty.");
        }

        return new Move(color, position);
    }
}
