package com.kevintweber.kimpachi.game;

import com.kevintweber.kimpachi.board.Board;
import com.kevintweber.kimpachi.board.Color;
import com.kevintweber.kimpachi.board.Move;
import com.kevintweber.kimpachi.exception.IllegalMoveException;
import com.kevintweber.kimpachi.game.turn.Turn;
import com.kevintweber.kimpachi.rules.Rules;
import lombok.NonNull;

import java.util.*;

public final class Game {

    private final Set<Integer> boardHashes;
    private final Configuration configuration;
    private final UUID gameId;
    private final Rules rules;
    private final Deque<Turn> turns;
    private Board board;

    public Game(
            @NonNull Configuration configuration,
            @NonNull Rules rules) {
        this.boardHashes = new HashSet<>();
        this.configuration = configuration;
        this.gameId = UUID.randomUUID();
        this.rules = rules;
        this.turns = new LinkedList<>();
        this.board = Board.empty(configuration);
    }

    public void addMove(@NonNull Move move) {
        if (!getNextMoveColor().equals(move.getColor())) {
            throw new IllegalMoveException("Illegal color is moving: " + move);
        }

        Turn turn = rules.move(configuration, boardHashes, board, move);

        turns.addLast(turn);
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public UUID getGameId() {
        return gameId;
    }

    public Deque<Turn> getTurns() {
        return new LinkedList<>(turns);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        return gameId.equals(game.gameId);
    }

    @Override
    public int hashCode() {
        return gameId.hashCode();
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameId=" + gameId +
                ", configuration=" + configuration +
                '}';
    }
}
