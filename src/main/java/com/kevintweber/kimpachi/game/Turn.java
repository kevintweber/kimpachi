package com.kevintweber.kimpachi.game;

import com.kevintweber.kimpachi.board.Color;
import com.kevintweber.kimpachi.board.Move;
import com.kevintweber.kimpachi.board.Position;
import lombok.Data;
import lombok.NonNull;

@Data
public final class Turn {

    private final Move move;
    private final Prisoners prisoners;

    public Turn(@NonNull Move move) {
        this.move = move;
        this.prisoners = Prisoners.empty();
    }

    public Turn(
            @NonNull Move move,
            @NonNull Prisoners prisoners) {
        this.move = move;
        this.prisoners = prisoners;
    }

    public Color getColor() {
        return move.getColor();
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
}
