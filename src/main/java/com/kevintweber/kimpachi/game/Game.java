package com.kevintweber.kimpachi.game;

import com.kevintweber.kimpachi.board.BoardManager;
import com.kevintweber.kimpachi.board.Move;
import com.kevintweber.kimpachi.exception.IllegalMoveException;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Deque;
import java.util.UUID;

public final class Game {

    private static final Logger logger = LoggerFactory.getLogger(Game.class);

    private final UUID gameId;
    private final BoardManager boardManager;
    private final Configuration configuration;
    private final TurnManager turnManager;

    private Game(
            @NonNull UUID gameId,
            @NonNull BoardManager boardManager,
            @NonNull Configuration configuration,
            @NonNull TurnManager turnManager) {
        this.gameId = gameId;
        this.boardManager = boardManager;
        this.configuration = configuration;
        this.turnManager = turnManager;
    }

    public static Game newGame(@NonNull Configuration configuration) {
        return new Game(
                UUID.randomUUID(),
                BoardManager.newBoard(configuration),
                configuration,
                new TurnManager()
        );
    }

    public static Game inProgressGame(
            @NonNull UUID gameId,
            @NonNull BoardManager boardManager,
            @NonNull Configuration configuration,
            @NonNull TurnManager turnManager) {
        return new Game(
                gameId,
                boardManager,
                configuration,
                turnManager
        );
    }

    public void addMove(@NonNull Move move) {
        logger.info("Add move: {}", move);
        if (!boardManager.isMoveValid(move)) {
            throw new IllegalMoveException("Move is illegal: " + move);
        }

        if (!configuration.getRules().isMoveValid(move, boardManager.getBoard())) {
            throw new IllegalMoveException("Move is illegal: " + move);
        }

        Turn turn = new Turn(
                move,
                boardManager.getPrisoners(move)
        );

        boardManager.addTurn(turn);
        turnManager.addTurn(turn);
    }

    public UUID getGameId() {
        return gameId;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public Deque<Turn> getTurns() {
        return turnManager.getTurns();
    }

    public String toSgf() {
        return configuration.toSgf() +
                turnManager.toSgf();
    }

    public void toStdOut() {
        boardManager.getBoard().toStdOut();
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
                '}';
    }
}
