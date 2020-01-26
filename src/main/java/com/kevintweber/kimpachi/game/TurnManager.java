package com.kevintweber.kimpachi.game;

import com.kevintweber.kimpachi.board.Color;
import com.kevintweber.kimpachi.exception.IllegalMoveException;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.Deque;
import java.util.LinkedList;

@EqualsAndHashCode
@ToString
public final class TurnManager {

    private final Deque<Turn> turns;

    public TurnManager() {
        this.turns = new LinkedList<>();
    }

    public TurnManager(@NonNull Deque<Turn> turns) {
        this.turns = turns;
    }

    public Deque<Turn> getTurns() {
        return new LinkedList<>(turns);
    }

    public void addTurn(@NonNull Turn turn) {
        Color nextMoveColor = getNextMoveColor();
        if (!turn.getColor().equals(nextMoveColor)) {
            throw new IllegalMoveException("Out of turn. Color must be " + nextMoveColor.toString());
        }

        this.turns.addLast(turn);
    }

    public Color getNextMoveColor() {
        if (turns.isEmpty()) {
            return Color.Black;
        }

        Color previousMoveColor = turns.getLast().getColor();
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

    public String toSgf() {
        StringBuilder sb = new StringBuilder();
        for (Turn turn : turns) {
            sb.append(turn.toSgf());
        }

        return sb.toString();
    }
}
