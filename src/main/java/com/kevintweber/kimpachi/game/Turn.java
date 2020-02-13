package com.kevintweber.kimpachi.game;

import com.kevintweber.kimpachi.board.*;
import lombok.Data;
import lombok.NonNull;

@Data
public final class Turn {

    private final Board board;
    private final Move move;
    private final Prisoners prisoners;

    public Turn(
            @NonNull Board board,
            @NonNull Move move,
            @NonNull Prisoners prisoners) {
        this.board = board;
        this.move = move;
        this.prisoners = prisoners;
    }

    public Stone getStone() {
        return move.getStone();
    }

    public Point getPoint() {
        return move.getPoint();
    }

    public Position getPosition() {
        return move.getPosition();
    }

    public boolean isPass() {
        return move.isPassMove();
    }

    public String toSgf() {
        return ";" + move.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Turn turn = (Turn) o;

        return board.equals(turn.board);
    }

    @Override
    public int hashCode() {
        return board.hashCode();
    }
}
