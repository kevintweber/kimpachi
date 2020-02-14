package com.kevintweber.kimpachi.game;

import com.google.common.collect.ImmutableSet;
import com.kevintweber.kimpachi.board.Point;
import com.kevintweber.kimpachi.board.Stone;
import lombok.Data;
import lombok.NonNull;

import java.util.Set;

@Data
public final class Prisoners {

    private final ImmutableSet<Point> blackPrisoners;
    private final ImmutableSet<Point> whitePrisoners;

    private static final Prisoners EMPTY = new Prisoners(Set.of(), Set.of());

    private Prisoners(
            @NonNull Set<Point> blackPrisoners,
            @NonNull Set<Point> whitePrisoners) {
        this.blackPrisoners = ImmutableSet.copyOf(blackPrisoners);
        this.whitePrisoners = ImmutableSet.copyOf(whitePrisoners);
    }

    public static Prisoners of(
            @NonNull Set<Point> blackPrisoners,
            @NonNull Set<Point> whitePrisoners) {
        if (blackPrisoners.isEmpty() && whitePrisoners.isEmpty()) {
            return EMPTY;
        }

        return new Prisoners(blackPrisoners, whitePrisoners);
    }

    public static Prisoners empty() {
        return EMPTY;
    }

    public int count(Stone stone) {
        if (stone.equals(Stone.Black)) {
            return blackPrisoners.size();
        }

        return whitePrisoners.size();
    }

    public PrisonerCount getPrisonerCount() {
        return new PrisonerCount(blackPrisoners.size(), whitePrisoners.size());
    }

    public boolean isEmpty() {
        return blackPrisoners.isEmpty() && whitePrisoners.isEmpty();
    }
}
