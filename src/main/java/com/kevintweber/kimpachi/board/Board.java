package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.exception.ConfigurationException;
import com.kevintweber.kimpachi.exception.InvalidPositionException;
import com.kevintweber.kimpachi.game.Configuration;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
public final class Board {

    private final Area blackArea;
    private final Area whiteArea;
    private final Area komi;

    private static final Map<Integer, Board> EMPTY = new HashMap<>();

    private Board(
            @NonNull Area blackArea,
            @NonNull Area whiteArea) {
        Board.checkBoardSize(blackArea.getBoardSize());
        if (blackArea.getBoardSize() != whiteArea.getBoardSize()) {
            throw new ConfigurationException("Each area must have the same board size.");
        }


        if (blackArea.intersection(whiteArea).count() != 0) {
            throw new ConfigurationException("Black and white areas must not overlap.");
        }

        this.blackArea = Area.copyOf(blackArea);
        this.whiteArea = Area.copyOf(whiteArea);
        this.komi = Area.copyOf(Komi.getKomi(blackArea.getBoardSize()));
    }

    public static Board copyOf(@NonNull Board otherBoard) {
        return new Board(otherBoard.blackArea, otherBoard.whiteArea);
    }

    public static Board empty(@NonNull Configuration configuration) {
        int boardSize = configuration.getBoardSize();
        if (EMPTY.containsKey(boardSize)) {
            return EMPTY.get(boardSize);
        }

        Board empty = new Board(Area.empty(boardSize), Area.empty(boardSize));
        EMPTY.put(boardSize, empty);

        return empty;
    }

    public static Board of(@NonNull Configuration configuration) {
        Board empty = Board.empty(configuration);
        if (configuration.getHandicap() <= 1) {
            return empty;
        }

        Area blackHandicapArea = Handicap.getHandicap(configuration);

        return new Board(blackHandicapArea, Area.empty(configuration.getBoardSize()));
    }

    public Board clear(@NonNull Position position) {
        Color color = getColor(position);
        if (color.equals(Color.Empty)) {
            return this;
        }

        return modifyPosition(position, Color.Empty);
    }

    public int getBoardSize() {
        return blackArea.getBoardSize();
    }

    public Color getColor(@NonNull Position position) {
        if (!isOnBoard(position)) {
            throw new InvalidPositionException("Position is not on the board: x=" + position.getX() + ";y=" + position.getY());
        }

        if (blackArea.contains(position)) {
            return Color.Black;
        }

        if (whiteArea.contains(position)) {
            return Color.White;
        }

        return Color.Empty;
    }

    public boolean isKomi(@NonNull Position position) {
        return komi.contains(position);
    }

    public boolean isOccupied(@NonNull Position position) {
        Color color = getColor(position);

        return !color.equals(Color.Empty);
    }

    public boolean isOnBoard(@NonNull Position position) {
        int x = position.getX();
        int y = position.getY();
        int size = blackArea.getBoardSize();

        return x > 0 &&
                y > 0 &&
                x <= size &&
                y <= size;
    }

    public static void checkBoardSize(int boardSize) {
        if (boardSize != 19 && boardSize != 13 && boardSize != 9) {
            throw new ConfigurationException("Invalid board size: " + boardSize);
        }
    }

    public Board withMove(@NonNull Move move) {
        if (!isOnBoard(move.getPosition())) {
            throw new InvalidPositionException("Position is not on the board: " + move);
        }

        Color currentColor = getColor(move.getPosition());
        if (currentColor.equals(move.getColor())) {
            return this;
        }

        return modifyPosition(move.getPosition(), move.getColor());
    }

    private Board modifyPosition(Position position, Color color) {
        if (color.equals(Color.Empty)) {
            return new Board(blackArea.without(position), whiteArea.without(position));
        }

        if (color.equals(Color.Black)) {
            return new Board(blackArea.with(position), whiteArea.without(position));
        }

        return new Board(blackArea.without(position), whiteArea.with(position));
    }

    public void toStdOut() {
        String header = "   ";
        for (int i = 0; i < blackArea.getBoardSize(); i++) {
            header += Position.sgfCharacters.charAt(i) + " ";
        }

        System.out.println(header);

        for (int y = 1; y <= blackArea.getBoardSize(); y++) {
            String row = Position.sgfCharacters.charAt(y - 1) + "  ";
            for (int x = 1; x <= blackArea.getBoardSize(); x++) {
                Position position = Position.of(x, y);
                switch (getColor(position)) {
                    case Empty:
                        if (isKomi(position)) {
                            row += "+ ";
                        } else {
                            row += ". ";
                        }

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
                hashCode() +
                '}';
    }
}
