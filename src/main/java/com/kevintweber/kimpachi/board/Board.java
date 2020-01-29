package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.exception.ConfigurationException;
import com.kevintweber.kimpachi.exception.InvalidPositionException;
import com.kevintweber.kimpachi.game.Configuration;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public static Board of(@NonNull Configuration configuration) {
        Board empty = Board.empty(configuration);
        if (configuration.getHandicap() == 0) {
            return empty;
        }

        List<Position> handicapPositions = new ArrayList<>();
        switch (configuration.getBoardSize()) {
            case 9:
                break;

            case 13:
                break;

            case 19:
                handicapPositions.add(Position.of(4, 4));
                break;

            default:
                throw new ConfigurationException("Illegal board size: " + configuration.getBoardSize());
        }

        for (int i = 0; i < configuration.getHandicap(); i++) {
            empty = empty.withMove(Move.normalMove(Color.Black, handicapPositions.get(i)));
        }

        return empty;
    }

    public Board clear(@NonNull Position position) {
        Color color = getColor(position);
        if (color.equals(Color.Empty)) {
            return this;
        }

        return modifyPosition(position, Color.Empty);
    }

    public int getSize() {
        return size;
    }

    public Color getColor(@NonNull Position position) {
        if (!isValid(position)) {
            throw new InvalidPositionException("Position is not on the board: x=" + position.getX() + ";y=" + position.getY());
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

        return modifyPosition(move.getPosition(), move.getColor());
    }

    private Board modifyPosition(Position position, Color color) {
        Map<Position, Color> newPositions = new HashMap<>(positions);
        newPositions.replace(position, color);

        return new Board(size, newPositions);
    }

    public void toStdOut() {
        String header = "   ";
        for (int i = 0; i < size; i++) {
            header += Position.sgfCharacters.charAt(i) + " ";
        }

        System.out.println(header);

        for (int y = 1; y <= size; y++) {
            String row = Position.sgfCharacters.charAt(y - 1) + "  ";
            for (int x = 1; x <= size; x++) {
                switch (getColor(Position.of(x, y))) {
                    case Empty:
                        row += ". ";
                        break;

                    case Black:
                        row += "X ";
                        break;

                    case White:
                        row += "O ";
                        break;
                }
            }

            System.out.println(row);
        }
    }

    @Override
    public String toString() {
        return "Board{" +
                positions.hashCode() +
                '}';
    }
}
