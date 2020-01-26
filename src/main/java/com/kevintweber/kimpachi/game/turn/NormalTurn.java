package com.kevintweber.kimpachi.game.turn;

import com.kevintweber.kimpachi.board.Color;
import com.kevintweber.kimpachi.board.Move;
import com.kevintweber.kimpachi.board.Position;
import com.kevintweber.kimpachi.game.Prisoners;
import lombok.NonNull;

public class NormalTurn implements Turn {

    private final Move move;
    private final Prisoners prisoners;

    public NormalTurn(
            @NonNull Move move,
            @NonNull Prisoners prisoners) {
        this.move = move;
        this.prisoners = prisoners;
    }

    @Override
    public Color getColor() {
        return move.getColor();
    }

    @Override
    public Prisoners getPrisoners() {
        return prisoners;
    }

    @Override
    public Position getPosition() {
        return move.getPosition();
    }

    @Override
    public boolean isPass() {
        return false;
    }
}
