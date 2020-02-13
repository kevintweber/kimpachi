package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.sgf.SgfToken;
import lombok.Data;
import lombok.NonNull;

/**
 * Similar to Position, except point is nullable.
 * <p>
 * Null point means a pass move.
 */
@Data
public final class Move {

    private final Point point;
    private final Stone stone;

    private Move(
            @NonNull Stone stone,
            Point point) {
        this.point = point;
        this.stone = stone;
    }

    public static Move normalMove(
            @NonNull Stone stone,
            @NonNull Point point) {
        return new Move(stone, point);
    }

    public static Move passMove(@NonNull Stone stone) {
        return new Move(stone, null);
    }

    public static Move fromSgf(SgfToken token) {
        Stone stone = Stone.White;
        if (token.getKey().equals("B")) {
            stone = Stone.Black;
        }

        if (token.getValue().isEmpty()) {
            return passMove(stone);
        }

        return normalMove(stone, Point.fromSgf(token.getValue()));
    }

    public Position getPosition() {
        if (point == null) {
            throw new IllegalStateException("Cannot convert pass move to position.");
        }

        return Position.of(stone, point);
    }

    public boolean isPassMove() {
        return point == null;
    }

    @Override
    public String toString() {
        String result = "";
        if (stone.equals(Stone.Black)) {
            result += "B[";
        } else {
            result += "W[";
        }

        if (isPassMove()) {
            return result + "]";
        }

        return result + point + "]";
    }
}
