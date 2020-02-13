package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.exception.ConfigurationException;
import com.kevintweber.kimpachi.game.Configuration;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.Set;

@EqualsAndHashCode
public final class Board {

    public final static String positionCharacters = "ABCDEFGHJKLMNOPQRST";

    private final Area blackArea;
    private final Area whiteArea;
    private final Komi komi;

    private static final Board EMPTY = new Board(Area.empty(Stone.Black), Area.empty(Stone.White));

    private Board(
            @NonNull Area blackArea,
            @NonNull Area whiteArea) {
        Set<Point> blackPoints = blackArea.getPoints();
        blackPoints.retainAll(whiteArea.getPoints());
        if (!blackPoints.isEmpty()) {
            throw new ConfigurationException("Black and white areas overlap.");
        }

        this.blackArea = Area.copyOf(blackArea);
        this.whiteArea = Area.copyOf(whiteArea);
        this.komi = Komi.KOMI;
    }

    public static Board copyOf(@NonNull Board otherBoard) {
        return new Board(otherBoard.blackArea, otherBoard.whiteArea);
    }

    public static Board empty() {
        return EMPTY;
    }

    public static Board of(@NonNull Configuration configuration) {
        if (configuration.getHandicap() <= 1) {
            return EMPTY;
        }

        Area blackHandicapArea = Handicap.getHandicap(configuration);

        return new Board(blackHandicapArea, Area.empty(Stone.White));
    }

    public Board clear(@NonNull Point point) {
        Color color = getColor(point);
        if (color.equals(Color.Empty)) {
            return this;
        }

        return modifyPosition(point, Color.Empty);
    }

    public Board clear(@NonNull Board otherBoard) {
        Board resultBoard = Board.copyOf(this);
        for (Point blackPoint : otherBoard.blackArea.getPoints()) {
            resultBoard = clear(blackPoint);
        }

        for (Point whitePoint : otherBoard.whiteArea.getPoints()) {
            resultBoard = clear(whitePoint);
        }

        return resultBoard;
    }

    public Color getColor(@NonNull Point point) {
        if (blackArea.contains(point)) {
            return Color.Black;
        }

        if (whiteArea.contains(point)) {
            return Color.White;
        }

        return Color.Empty;
    }

    public boolean isKomi(@NonNull Point point) {
        return komi.isKomi(point);
    }

    public boolean isOccupied(@NonNull Point point) {
        Color color = getColor(point);

        return !color.equals(Color.Empty);
    }

    public Board withMove(@NonNull Move move) {
        if (move.isPassMove()) {
            return this;
        }

        Color currentColor = getColor(move.getPoint());
        if (currentColor.equals(Color.of(move.getStone()))) {
            return this;
        }

        return modifyPosition(move.getPoint(), Color.of(move.getStone()));
    }

    private Board modifyPosition(Point point, Color color) {
        if (color.equals(Color.Empty)) {
            return new Board(blackArea.without(point), whiteArea.without(point));
        }

        if (color.equals(Color.Black)) {
            return new Board(blackArea.with(point), whiteArea.without(point));
        }

        return new Board(blackArea.without(point), whiteArea.with(point));
    }

    public void toStdOut() {
        String header = "    ";
        for (int i = 0; i < 19; i++) {
            header += Board.positionCharacters.charAt(i) + " ";
        }

        System.out.println(header);

        for (int y = 19; y >= 1; y--) {
            String row = y + "  ";
            if (y < 10) {
                row += " ";
            }

            for (int x = 1; x <= 19; x++) {
                Point point = Point.of(x, y);
                switch (getColor(point)) {
                    case Empty:
                        if (isKomi(point)) {
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
