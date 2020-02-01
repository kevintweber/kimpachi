package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.exception.ConfigurationException;
import com.kevintweber.kimpachi.game.Configuration;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode
public final class Board {

    private final Area blackArea;
    private final Area whiteArea;
    private final Area komi;

    private static final Board EMPTY = new Board(Area.empty(), Area.empty());

    private Board(
            @NonNull Area blackArea,
            @NonNull Area whiteArea) {
        if (blackArea.intersection(whiteArea).count() != 0) {
            throw new ConfigurationException("Black and white areas must not overlap.");
        }

        this.blackArea = Area.copyOf(blackArea);
        this.whiteArea = Area.copyOf(whiteArea);
        this.komi = Area.copyOf(Komi.getKomi());
    }

    public static Board copyOf(@NonNull Board otherBoard) {
        return new Board(otherBoard.blackArea, otherBoard.whiteArea);
    }

    public static Board empty() {
        return EMPTY;
    }

    public static Board of(@NonNull Configuration configuration) {
        Board empty = Board.empty();
        if (configuration.getHandicap() <= 1) {
            return empty;
        }

        Area blackHandicapArea = Handicap.getHandicap(configuration);

        return new Board(blackHandicapArea, Area.empty());
    }

    public Board clear(@NonNull Position position) {
        Color color = getColor(position);
        if (color.equals(Color.Empty)) {
            return this;
        }

        return modifyPosition(position, Color.Empty);
    }

    public Color getColor(@NonNull Position position) {
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

    public Board withMove(@NonNull Move move) {
        Color currentColor = getColor(move.getPosition());
        if (currentColor.equals(Color.of(move.getStone()))) {
            return this;
        }

        return modifyPosition(move.getPosition(), Color.of(move.getStone()));
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
        for (int i = 0; i < 19; i++) {
            header += Position.sgfCharacters.charAt(i) + " ";
        }

        System.out.println(header);

        for (int y = 1; y <= 19; y++) {
            String row = Position.sgfCharacters.charAt(y - 1) + "  ";
            for (int x = 1; x <= 19; x++) {
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
