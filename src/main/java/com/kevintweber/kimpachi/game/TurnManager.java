package com.kevintweber.kimpachi.game;

import com.kevintweber.kimpachi.board.Stone;
import com.kevintweber.kimpachi.exception.IllegalMoveException;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.Deque;
import java.util.LinkedList;

@EqualsAndHashCode
@ToString
public final class TurnManager {

    private final Configuration configuration;
    private final Deque<Turn> turns;

    public TurnManager(@NonNull Configuration configuration) {
        this.configuration = configuration;
        this.turns = new LinkedList<>();
    }

    public TurnManager(
            @NonNull Configuration configuration,
            @NonNull Deque<Turn> turns) {
        this.configuration = configuration;
        this.turns = turns;
    }

    public Deque<Turn> getTurns() {
        return new LinkedList<>(turns);
    }

    public void addTurn(@NonNull Turn turn) {
        Stone nextMoveStone = getNextMoveStone();
        if (!turn.getStone().equals(nextMoveStone)) {
            throw new IllegalMoveException("Out of turn. Color must be " + nextMoveStone.toString());
        }

        this.turns.addLast(turn);
    }

    public Stone getNextMoveStone() {
        if (turns.isEmpty()) {
            if (configuration.getHandicap() > 1) {
                return Stone.White;
            }

            return Stone.Black;
        }

        Stone previousMoveStone = turns.getLast().getStone();
        if (previousMoveStone.equals(Stone.Black)) {
            return Stone.White;
        }

        return Stone.Black;
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
