package com.kevintweber.kimpachi.game;

import com.kevintweber.kimpachi.board.Board;
import com.kevintweber.kimpachi.board.Color;
import com.kevintweber.kimpachi.board.Move;
import com.kevintweber.kimpachi.exception.IllegalMoveException;
import lombok.NonNull;

import java.util.Deque;
import java.util.LinkedList;
import java.util.UUID;

public final class Game {

    private final Configuration configuration;
    private final UUID gameId;
    private final Deque<Move> moves;
    private Board board;

    public Game(@NonNull Configuration configuration) {
        this.configuration = configuration;
        this.gameId = UUID.randomUUID();
        this.moves = new LinkedList<>();
        this.board = Board.empty(configuration);
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public UUID getGameId() {
        return gameId;
    }

    public Deque<Move> getMoves() {
        return new LinkedList<>(moves);
    }

    public void addMove(@NonNull Move move) {
        if (!getNextMoveColor().equals(move.getColor())) {
            throw new IllegalMoveException("Illegal color is moving: " + move);
        }

        if (board.isOccupied(move.getPosition())) {
            throw new IllegalMoveException("Position is occupied: " + move);
        }

        moves.addLast(move);
        board = board.withMove(move);
    }

    public Color getNextMoveColor() {
        if (moves.isEmpty()) {
            return Color.Black;
        }

        Color previousMoveColor = moves.peekLast().getColor();
        if (previousMoveColor.equals(Color.Black)) {
            return Color.White;
        }

        return Color.Black;
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
