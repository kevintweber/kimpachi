package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.exception.IllegalMoveException;
import lombok.Data;
import lombok.NonNull;

@Data
public final class Move {

    private final Color color;
    private final Position position;
    private final boolean isPassMove;

    private Move(
            @NonNull Color color,
            Position position,
            boolean isPassMove) {
        this.color = color;
        this.position = position;
        this.isPassMove = isPassMove;
    }

    public static Move normalMove(
            @NonNull Color color,
            @NonNull Position position) {
        if (color.equals(Color.Empty)) {
            throw new IllegalMoveException("Move color cannot be EMPTY.");
        }

        return new Move(color, position, false);
    }

    public static Move passMove(@NonNull Color color) {
        if (color.equals(Color.Empty)) {
            throw new IllegalMoveException("Move color cannot be EMPTY.");
        }

        return new Move(color, null, true);
    }

    public String toSgf() {
        String result = ";";
        if (color.equals(Color.Black)) {
            result += "B[";
        } else {
            result += "W[";
        }

        if (isPassMove) {
            return result + "]";
        }

        return result + position.toSgf() + "]";
    }
}
