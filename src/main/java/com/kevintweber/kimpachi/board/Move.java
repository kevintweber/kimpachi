package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.utilities.SgfToken;
import lombok.Data;
import lombok.NonNull;

@Data
public final class Move {

    private final Stone stone;
    private final Position position;
    private final boolean isPassMove;

    private Move(
            @NonNull Stone stone,
            Position position,
            boolean isPassMove) {
        this.stone = stone;
        this.position = position;
        this.isPassMove = isPassMove;
    }

    public static Move normalMove(
            @NonNull Stone stone,
            @NonNull Position position) {
        return new Move(stone, position, false);
    }

    public static Move passMove(@NonNull Stone stone) {
        return new Move(stone, null, true);
    }

    public static Move fromSgf(@NonNull SgfToken token) {
        Stone stone = token.getKey().equalsIgnoreCase("B") ? Stone.Black : Stone.White;
        if (token.getValue().isBlank()) {
            return passMove(stone);
        }

        return normalMove(stone, Position.fromSgf(token.getValue()));
    }

    @Override
    public String toString() {
        String result = "";
        if (stone.equals(Stone.Black)) {
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
