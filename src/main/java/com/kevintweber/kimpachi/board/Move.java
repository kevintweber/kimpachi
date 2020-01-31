package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.exception.ColorRequiredException;
import com.kevintweber.kimpachi.exception.SgfException;
import com.kevintweber.kimpachi.utilities.SgfToken;
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
            throw new ColorRequiredException("Move color cannot be EMPTY.");
        }

        return new Move(color, position, false);
    }

    public static Move passMove(@NonNull Color color) {
        if (color.equals(Color.Empty)) {
            throw new ColorRequiredException("Move color cannot be EMPTY.");
        }

        return new Move(color, null, true);
    }

    public static Move sgf(@NonNull SgfToken token) {
        Color color;
        switch (token.getKey()) {
            case "B":
                color = Color.Black;
                break;

            case "W":
                color = Color.White;
                break;

            default:
                throw new SgfException("Token is not a move key. Token=" + token);
        }

        if (token.getValue().isBlank()) {
            return passMove(color);
        }

        return normalMove(color, Position.fromSgf(token.getValue()));
    }

    @Override
    public String toString() {
        String result = "";
        if (color.equals(Color.Black)) {
            result += "B[";
        } else {
            result += "W[";
        }

        if (isPassMove) {
            return result + "]";
        }

        return result + position + "]";
    }
}
