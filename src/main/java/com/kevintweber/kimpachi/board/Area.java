package com.kevintweber.kimpachi.board;

import com.google.common.collect.ImmutableSet;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode
@ToString
public final class Area {

    private final ImmutableSet<Position> positions;

    private static final Area EMPTY = new Area(Set.of());

    private Area(@NonNull Set<Position> positions) {
        this.positions = ImmutableSet.copyOf(positions);
    }

    public static Area empty() {
        return EMPTY;
    }

    public static Area copyOf(@NonNull Area otherArea) {
        return new Area(otherArea.positions);
    }

    public boolean contains(@NonNull Position position) {
        return positions.contains(position);
    }

    public Area enlarge() {
        Area enlargedArea = Area.copyOf(this);
        for (Position position : positions) {
            enlargedArea = enlargedArea.union(enlargePosition(position));
        }

        return enlargedArea;
    }

    public Area enlarge(int count) {
        if (count < 1) {
            return this;
        }

        Area startingArea = Area.copyOf(this);
        for (int i = 0; i < count; i++) {
            Area enlargedArea = startingArea.enlarge();
            if (enlargedArea.equals(startingArea)) {
                return enlargedArea;
            }

            startingArea = Area.copyOf(enlargedArea);
        }

        return startingArea;
    }

    private Area enlargePosition(Position position) {
        Area enlargedArea = new Area(Set.of(position));
        int x = position.getX();
        int y = position.getY();
        enlargedArea = addPositionToArea(enlargedArea, x - 1, y);
        enlargedArea = addPositionToArea(enlargedArea, x + 1, y);
        enlargedArea = addPositionToArea(enlargedArea, x, y - 1);
        enlargedArea = addPositionToArea(enlargedArea, x, y + 1);

        return enlargedArea;
    }

    private Area addPositionToArea(Area area, int x, int y) {
        try {
            return area.with(Position.of(x, y));
        } catch (Exception e) {
            return area;
        }
    }

    public Set<Position> getPositions() {
        return new HashSet<>(positions);
    }

    public int count() {
        return positions.size();
    }

    public Area intersection(@NonNull Area otherArea) {
        Set<Position> commonPositions = new HashSet<>(positions);
        commonPositions.retainAll(otherArea.positions);

        return new Area(commonPositions);
    }

    public Area union(@NonNull Area otherArea) {
        Set<Position> positionSet = new HashSet<>(positions);
        positionSet.addAll(otherArea.positions);

        return new Area(positionSet);
    }

    public Area with(@NonNull Position position) {
        Set<Position> positionSet = new HashSet<>(positions);
        positionSet.add(position);

        return new Area(positionSet);
    }

    public Area without(@NonNull Position position) {
        Set<Position> positionSet = new HashSet<>(positions);
        positionSet.remove(position);

        return new Area(positionSet);
    }

    public static class Builder {

        private final Set<Position> builderPositions;

        public Builder() {
            this.builderPositions = new HashSet<>();
        }

        public Builder addPosition(@NonNull Position position) {
            this.builderPositions.add(position);

            return this;
        }

        public Area build() {
            return new Area(builderPositions);
        }
    }
}
