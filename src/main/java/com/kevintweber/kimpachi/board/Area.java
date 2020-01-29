package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.exception.ConfigurationException;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@EqualsAndHashCode
@ToString
public final class Area {

    private final int boardSize;
    private final Set<Position> positions;

    private static final Map<Integer, Area> EMPTY = new HashMap<>();

    private Area(
            final int boardSize,
            @NonNull Set<Position> positions) {
        if (!Board.isValidBoardSize(boardSize)) {
            throw new ConfigurationException("Invalid board size: " + boardSize);
        }

        this.boardSize = boardSize;
        this.positions = new HashSet<>(positions);
    }

    public static Area empty(final int boardSize) {
        if (EMPTY.containsKey(boardSize)) {
            return EMPTY.get(boardSize);
        }

        Area emptyArea = new Area(boardSize, Set.of());
        EMPTY.put(boardSize, emptyArea);

        return emptyArea;
    }

    public static Area of(@NonNull Area otherArea) {
        return new Area(otherArea.boardSize, otherArea.positions);
    }

    public boolean contains(@NonNull Position position) {
        return positions.contains(position);
    }

    public Area enlarge() {
        Area enlargedArea = Area.of(this);
        for (Position position : positions) {
            enlargedArea = enlargedArea.union(enlargePosition(position));
        }

        return enlargedArea;
    }

    public Area enlarge(int count) {
        if (count < 1) {
            return this;
        }

        Area startingArea = Area.of(this);
        for (int i = 0; i < count; i++) {
            Area enlargedArea = startingArea.enlarge();
            if (enlargedArea.equals(startingArea)) {
                return enlargedArea;
            }

            startingArea = Area.of(enlargedArea);
        }

        return startingArea;
    }

    private Area enlargePosition(Position position) {
        Area enlargedArea = new Area(boardSize, Set.of(position));
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

    public int getBoardSize() {
        return boardSize;
    }

    public Set<Position> getPositions() {
        return new HashSet<>(positions);
    }

    public int getSize() {
        return positions.size();
    }

    public Area intersection(@NonNull Area otherArea) {
        if (boardSize != otherArea.boardSize) {
            throw new IllegalArgumentException(
                    "When combining Areas, both Areas must have the same board size: " + otherArea.boardSize
            );
        }

        Set<Position> commonPositions = new HashSet<>(positions);
        commonPositions.retainAll(otherArea.positions);

        return new Area(boardSize, commonPositions);
    }

    public boolean isValidPosition(@NonNull Position position) {
        int x = position.getX();
        int y = position.getY();

        return x > 0 &&
                y > 0 &&
                x <= boardSize &&
                y <= boardSize;
    }

    public Area union(@NonNull Area otherArea) {
        if (boardSize != otherArea.boardSize) {
            throw new IllegalArgumentException(
                    "When combining Areas, both Areas must have the same board size: " + otherArea.boardSize
            );
        }

        Set<Position> positionSet = new HashSet<>(positions);
        positionSet.addAll(otherArea.positions);

        return new Area(boardSize, positionSet);
    }

    public Area with(@NonNull Position position) {
        if (!isValidPosition(position)) {
            throw new ConfigurationException("Invalid position: x=" + position.getX() + ";y=" + position.getY());
        }

        Set<Position> positionSet = new HashSet<>(positions);
        positionSet.add(position);

        return new Area(boardSize, positionSet);
    }

    public Area without(@NonNull Position position) {
        if (!isValidPosition(position)) {
            throw new ConfigurationException("Invalid position: x=" + position.getX() + ";y=" + position.getY());
        }

        Set<Position> positionSet = new HashSet<>(positions);
        positionSet.remove(position);

        return new Area(boardSize, positionSet);
    }

    public static class Builder {

        private final int builderSize;
        private final Set<Position> builderPositions;

        public Builder(int builderSize) {
            if (!Board.isValidBoardSize(builderSize)) {
                throw new ConfigurationException("Invalid board size: " + builderSize);
            }

            this.builderSize = builderSize;
            this.builderPositions = new HashSet<>();
        }

        public Builder addPosition(@NonNull Position position) {
            this.builderPositions.add(position);

            return this;
        }

        public Area build() {
            return new Area(builderSize, builderPositions);
        }
    }
}
