package com.kevintweber.kimpachi.game;

import com.google.common.collect.ImmutableSet;
import com.kevintweber.kimpachi.board.Color;
import com.kevintweber.kimpachi.board.Position;
import lombok.Data;
import lombok.NonNull;

import java.util.Set;

@Data
public final class Prisoners {

    private final ImmutableSet<Position> blackPrisoners;
    private final ImmutableSet<Position> whitePrisoners;

    private static final Prisoners EMPTY = new Prisoners(Set.of(), Set.of());

    private Prisoners(
            @NonNull Set<Position> blackPrisoners,
            @NonNull Set<Position> whitePrisoners) {
        this.blackPrisoners = ImmutableSet.copyOf(blackPrisoners);
        this.whitePrisoners = ImmutableSet.copyOf(whitePrisoners);
    }

    public static Prisoners of(
            @NonNull Set<Position> blackPrisoners,
            @NonNull Set<Position> whitePrisoners) {
        if (blackPrisoners.isEmpty() && whitePrisoners.isEmpty()) {
            return EMPTY;
        }

        return new Prisoners(blackPrisoners, whitePrisoners);
    }

    public static Prisoners empty() {
        return EMPTY;
    }

    public int countBlackPrisoners() {
        return count(Color.Black);
    }

    public int countWhitePrisoners() {
        return count(Color.White);
    }

    private int count(Color color) {
        if (color.equals(Color.Empty)) {
            throw new IllegalStateException("Prisoners cannot be empty color.");
        }

        if (color.equals(Color.Black)) {
            return blackPrisoners.size();
        }

        return whitePrisoners.size();
    }
}
