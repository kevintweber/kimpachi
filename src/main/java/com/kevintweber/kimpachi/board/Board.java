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

    private static final Map<Integer, Board> EMPTY = new HashMap<>();

    private Board(
            final int size,
            @NonNull Map<Position, Color> positions) {
        this.size = size;
        this.positions = positions;
        if (positions.size() != (size * size)) {
            throw new IllegalStateException("Board has invalid number of positions: " + positions.size());
        }
    }

    public static Board empty(@NonNull Configuration configuration) {
        int boardSize = configuration.getBoardSize();
        if (EMPTY.containsKey(boardSize)) {
            return EMPTY.get(boardSize);
        }

        Map<Position, Color> positionMap = new HashMap<>();
        for (int y = 1; y <= boardSize; y++) {
            for (int x = 1; x <= boardSize; x++) {
                positionMap.put(Position.of(x, y), Color.Empty);
            }
        }

        Board empty = new Board(boardSize, positionMap);
        EMPTY.put(boardSize, empty);

        return empty;
    }

    public Board clear(@NonNull Position position) {
        return withMove(Move.empty(position));
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

    public Board withMove(@NonNull Move move) {
        if (!isValid(move.getPosition())) {
            throw new InvalidPositionException("Position is not on the board: " + move);
        }

        Color currentColor = getColor(move.getPosition());
        if (currentColor.equals(move.getColor())) {
            return this;
        }

        Map<Position, Color> newPositions = new HashMap<>(positions);
        newPositions.replace(move.getPosition(), move.getColor());

        return new Board(size, newPositions);
    }

    @Override
    public String toString() {
        return "Board{" +
                positions.hashCode() +
                '}';
    }
}
