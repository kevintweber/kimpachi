package com.kevintweber.kimpachi.game.turn;

import com.kevintweber.kimpachi.board.Color;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.Deque;
import java.util.LinkedList;

@EqualsAndHashCode
@ToString
public final class TurnManager {

    private final Deque<Turn> turns;

    public TurnManager(@NonNull Deque<Turn> turns) {
        this.turns = turns;
    }

    public Deque<Turn> getTurns() {
        return new LinkedList<>(turns);
    }

    public void addTurn(@NonNull Turn turn) {
        this.turns.addLast(turn);
    }

    public Color getNextMoveColor() {
        if (turns.isEmpty()) {
            return Color.Black;
        }

        Color previousMoveColor = turns.peekLast().getColor();
        if (previousMoveColor.equals(Color.Black)) {
            return Color.White;
        }

        return Color.Black;
    }

    public boolean isGameOver() {
        if (turns.size() < 2) {
            return false;
        }

        Turn lastMove = turns.removeLast();
        if (!lastMove.isPass()) {
            turns.addLast(lastMove);

            return false;
        }

        Turn secondToLastMove = turns.getLast();
        turns.addLast(lastMove);

        return secondToLastMove.isPass();
    }
}
