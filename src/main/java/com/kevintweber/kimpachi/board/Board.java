package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.exception.InvalidPositionException;
import com.kevintweber.kimpachi.game.Configuration;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
public final class Board {

    private final int size;
    private final Map<Position, Color> positions;

    private Board(
            final int size,
            @NonNull Map<Position, Color> positions) {
        this.size = size;
        this.positions = positions;
        if (positions.size() != (size * size)) {
            throw new IllegalStateException("Board has invalid number of positions: " + positions.size());
        }
    }

    public Board of(@NonNull Configuration configuration) {
        int boardSize = configuration.getBoardSize();
        Map<Position, Color> positionMap = new HashMap<>();
        for (int y = 1; y <= boardSize; y++) {
            for (int x = 1; x <= boardSize; x++) {
                positionMap.put(Position.of(x, y), Color.Empty);
            }
        }

        return new Board(boardSize, positionMap);
    }

    public int getSize() {
        return size;
    }

    public Color getColor(@NonNull Position position) {
        if (!isValid(position)) {
            throw new InvalidPositionException("Position is not on the board: " + position);
        }

        return positions.get(position);
    }

    public boolean isOccupied(@NonNull Position position) {
        Color color = getColor(position);

        return !color.equals(Color.Empty);
    }

    public boolean isValid(@NonNull Position position) {
        int x = position.getX();
        int y = position.getY();

        return x > 0 &&
                y > 0 &&
                x <= size &&
                y <= size;
    }

    public Board withColor(@NonNull Position position, @NonNull Color color) {
        if (!isValid(position)) {
            throw new InvalidPositionException("Position is not on the board: " + position);
        }

        Color currentColor = getColor(position);
        if (currentColor.equals(color)) {
            return this;
        }

        Map<Position, Color> newPositions = new HashMap<>(positions);
        newPositions.replace(position, color);

        return new Board(size, newPositions);
    }

    @Override
    public String toString() {
        return "Board{" +
                positions.hashCode() +
                '}';
    }
}
