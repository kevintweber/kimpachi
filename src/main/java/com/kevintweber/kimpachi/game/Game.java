package com.kevintweber.kimpachi.game;

import com.kevintweber.kimpachi.board.*;
import com.kevintweber.kimpachi.rules.Score;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Deque;
import java.util.Optional;
import java.util.UUID;

public final class Game implements Printable {

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
            Turn turn = new Turn(
                    turnManager.getCurrentBoard(),
                    move,
                    Prisoners.empty()
            );

            turnManager.addTurn(turn);

            return;
        }

        Board currentBoard = turnManager.getCurrentBoard();
        Prisoners prisoners = determinePrisoners(currentBoard, move);
        if (logger.isDebugEnabled() && !prisoners.isEmpty()) {
            logger.debug("Prisoners found: {}", prisoners);
        }

        Turn turn = new Turn(currentBoard.withMove(move, prisoners), move, prisoners);
        turnManager.addTurn(turn);
    }

    private Prisoners determinePrisoners(
            Board currentBoard,
            Move move) {
        Board nextBoard = currentBoard.withMove(move, Prisoners.empty());
        if (move.getStone().equals(Stone.Black)) {
            Area whiteDeadArea = nextBoard.getDeadArea(Stone.White);
            nextBoard = nextBoard.clear(whiteDeadArea);
            Area blackDeadArea = nextBoard.getDeadArea(Stone.Black);

            return Prisoners.of(blackDeadArea.getPoints(), whiteDeadArea.getPoints());
        }

        Area blackDeadArea = nextBoard.getDeadArea(Stone.Black);
        nextBoard = nextBoard.clear(blackDeadArea);
        Area whiteDeadArea = nextBoard.getDeadArea(Stone.White);

        return Prisoners.of(blackDeadArea.getPoints(), whiteDeadArea.getPoints());
    }

    public UUID getGameId() {
        return gameId;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public Optional<Score> getScore() {
        return configuration.getRules().getScore(
                configuration.getKomi(),
                turnManager.getCurrentBoard(),
                turnManager.getTotalPrisoners()
        );
    }

    public Deque<Turn> getTurns() {
        return turnManager.getTurns();
    }

    public String toSgf() {
        return configuration.toSgf() +
                turnManager.toSgf();
    }

    @Override
    public String print() {
        return turnManager.getCurrentBoard().print();
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
