package com.kevintweber.kimpachi.game;

import com.kevintweber.kimpachi.board.Board;
import com.kevintweber.kimpachi.board.Move;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Deque;
import java.util.UUID;

public final class Game {

    private static final Logger logger = LoggerFactory.getLogger(Game.class);

    private final UUID gameId;
    private final Configuration configuration;
    private final TurnManager turnManager;

    private Game(
            @NonNull UUID gameId,
            @NonNull Configuration configuration,
            @NonNull TurnManager turnManager) {
        this.gameId = gameId;
        this.configuration = configuration;
        this.turnManager = turnManager;
    }

    public static Game newGame(@NonNull Configuration configuration) {
        return new Game(
                UUID.randomUUID(),
                configuration,
                new TurnManager(configuration)
        );
    }

    public static Game inProgressGame(
            @NonNull UUID gameId,
            @NonNull Configuration configuration,
            @NonNull TurnManager turnManager) {
        return new Game(
                gameId,
                configuration,
                turnManager
        );
    }

    public void addMove(@NonNull Move move) {
        logger.info("Add move: {}", move);
        if (move.isPassMove()) {
            addMove(
                    turnManager.getCurrentBoard(),
                    move,
                    Prisoners.empty()
            );

            return;
        }

        Turn turn = new Turn(
                turnManager.getCurrentBoard().withMove(move),
                move,
                Prisoners.empty() //temp
        );

        turnManager.addTurn(turn);
    }

    private void addMove(
            Board board,
            Move move,
            Prisoners prisoners) {
        Turn turn = new Turn(
                turnManager.getCurrentBoard(),
                move,
                Prisoners.empty()
        );

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
        turnManager.getCurrentBoard().toStdOut();
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
