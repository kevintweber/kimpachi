package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.exception.ConfigurationException;
import com.kevintweber.kimpachi.game.Configuration;
import com.kevintweber.kimpachi.game.Prisoners;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The primary board type.
 */
@EqualsAndHashCode
public final class Board implements Printable {

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

    public static Board of(
            @NonNull Area blackArea,
            @NonNull Area whiteArea) {
        return new Board(blackArea, whiteArea);
    }

    public Board clear(@NonNull Point point) {
        Color color = getColor(point);
        if (color.equals(Color.Empty)) {
            return this;
        }

        return modifyPosition(point, Color.Empty);
    }

    public Board clear(@NonNull Area area) {
        if (area.isEmpty()) {
            return this;
        }

        if (area.getStone().equals(Stone.Black)) {
            Area newBlackArea = blackArea.without(area);

            return new Board(newBlackArea, whiteArea);
        }

        Area newWhiteArea = whiteArea.without(area);

        return new Board(blackArea, newWhiteArea);
    }

    private Area getArea(Stone stone) {
        if (stone.equals(Stone.Black)) {
            return blackArea;
        }

        return whiteArea;
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

    public Area getDeadArea(@NonNull Stone stone) {
        List<Group> deadGroups = new ArrayList<>();
        for (Group group : getArea(stone).getGroups()) {
            if (isDead(group)) {
                deadGroups.add(group);
            }
        }

        return Area.of(stone, deadGroups);
    }

    public Set<Point> getPoints(@NonNull Color color) {
        Set<Point> colorPoints = new HashSet<>();
        for (int x = 1; x <= 19; x++) {
            for (int y = 1; y <= 19; y++) {
                Point point = Point.of(x, y);
                if (getColor(point).equals(color)) {
                    colorPoints.add(point);
                }
            }
        }

        return colorPoints;
    }

    private boolean isDead(@NonNull Group group) {
        Set<Point> neighboringPoints = group.getNeighboringPoints();
        for (Point point : neighboringPoints) {
            if (!isOccupied(point)) {
                return false;
            }
        }

        return true;
    }

    public boolean isEmpty() {
        return equals(EMPTY);
    }

    public boolean isKomi(@NonNull Point point) {
        return komi.isKomi(point);
    }

    public boolean isOccupied(@NonNull Point point) {
        Color color = getColor(point);

        return !color.equals(Color.Empty);
    }

    public Board withMove(
            @NonNull Move move,
            @NonNull Prisoners prisoners) {
        if (move.isPassMove()) {
            return this;
        }

        Color currentColor = getColor(move.getPoint());
        if (currentColor.equals(Color.of(move.getStone()))) {
            return this;
        }

        Board modifiedBoard = modifyPosition(move.getPoint(), Color.of(move.getStone()));
        if (prisoners.isEmpty()) {
            return modifiedBoard;
        }

        if (move.getStone().equals(Stone.Black)) {
            Area newWhiteArea = modifiedBoard.whiteArea.without(prisoners.getWhitePrisoners());
            Area newBlackArea = modifiedBoard.blackArea.without(prisoners.getBlackPrisoners());

            return new Board(newBlackArea, newWhiteArea);
        }

        Area newBlackArea = modifiedBoard.blackArea.without(prisoners.getBlackPrisoners());
        Area newWhiteArea = modifiedBoard.whiteArea.without(prisoners.getWhitePrisoners());

        return new Board(newBlackArea, newWhiteArea);
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

    @Override
    public String print() {
        StringBuilder sb = new StringBuilder("    ");
        for (int i = 0; i < 19; i++) {
            sb.append(Board.positionCharacters.charAt(i));
            sb.append(" ");
        }

        sb.append("\n");

        for (int y = 19; y >= 1; y--) {
            sb.append(y);
            sb.append("  ");
            if (y < 10) {
                sb.append(" ");
            }

            for (int x = 1; x <= 19; x++) {
                Point point = Point.of(x, y);
                switch (getColor(point)) {
                    case Empty:
                        if (isKomi(point)) {
                            sb.append("+ ");
                        } else {
                            sb.append(". ");
                        }

                        break;

                    case Black:
                        sb.append("X ");
                        break;

                    case White:
                        sb.append("O ");
                        break;
                }
            }

            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return "Board{" +
                hashCode() +
                '}';
    }

    public static class Builder {
        private Area blackArea;
        private Area whiteArea;

        public Builder() {
            this.blackArea = Area.empty(Stone.Black);
            this.whiteArea = Area.empty(Stone.White);
        }

        public Builder(@NonNull Configuration configuration) {
            Board board = Board.of(configuration);
            this.blackArea = board.blackArea;
            this.whiteArea = board.whiteArea;
        }

        public Builder add(@NonNull Position position) {
            if (position.getStone().equals(Stone.Black)) {
                blackArea = blackArea.with(position.getPoint());

                return this;
            }

            whiteArea = whiteArea.with(position.getPoint());

            return this;
        }

        public Builder add(
                @NonNull Stone stone,
                @NonNull Set<Point> points) {
            if (stone.equals(Stone.Black)) {
                blackArea = blackArea.with(points);

                return this;
            }

            whiteArea = whiteArea.with(points);

            return this;
        }

        public Builder remove(@NonNull Point point) {
            blackArea = blackArea.without(point);
            whiteArea = whiteArea.without(point);

            return this;
        }

        public Builder remove(@NonNull Set<Point> points) {
            blackArea = blackArea.without(points);
            whiteArea = whiteArea.without(points);

            return this;
        }

        public Board build() {
            return Board.of(blackArea, whiteArea);
        }
    }
}
