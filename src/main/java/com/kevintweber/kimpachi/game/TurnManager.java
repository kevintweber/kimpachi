package com.kevintweber.kimpachi.game;

import com.kevintweber.kimpachi.board.Board;
import com.kevintweber.kimpachi.board.Stone;
import com.kevintweber.kimpachi.exception.IllegalMoveException;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.Deque;
import java.util.Iterator;
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

    public Board getCurrentBoard() {
        if (turns.isEmpty()) {
            return Board.of(configuration);
        }

        return turns.getLast().getBoard();
    }

    public Deque<Turn> getTurns() {
        return new LinkedList<>(turns);
    }

    public void addTurn(@NonNull Turn turn) {
        validateTurn(turn);
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

        Iterator<Turn> descendingIterator = turns.descendingIterator();
        Turn lastTurn = descendingIterator.next();
        if (!lastTurn.isPass()) {
            return false;
        }

        Turn secondToLastTurn = descendingIterator.next();

        return secondToLastTurn.isPass();
    }

    public String toSgf() {
        StringBuilder sb = new StringBuilder();
        for (Turn turn : turns) {
            sb.append(turn.toSgf());
        }

        return sb.toString();
    }

    private void validateTurn(Turn nextTurn) {
        Stone nextMoveStone = getNextMoveStone();
        if (!nextTurn.getStone().equals(nextMoveStone)) {
            throw new IllegalMoveException("Out of turn. Stone must be " + nextMoveStone.toString());
        }

        if (nextTurn.isPass()) {
            return;
        }

        for (Turn turn : turns) {
            if (turn.equals(nextTurn)) {
                throw new IllegalMoveException("Illegal Ko move.");
            }
        }
    }
}
