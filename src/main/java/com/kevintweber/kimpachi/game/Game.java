package com.kevintweber.kimpachi.game;

import com.kevintweber.kimpachi.board.BoardManager;
import com.kevintweber.kimpachi.board.Move;
import com.kevintweber.kimpachi.exception.IllegalMoveException;
import com.kevintweber.kimpachi.game.turn.NormalTurn;
import com.kevintweber.kimpachi.game.turn.PassTurn;
import com.kevintweber.kimpachi.game.turn.Turn;
import com.kevintweber.kimpachi.game.turn.TurnManager;
import com.kevintweber.kimpachi.rules.Rules;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public final class Game {

    private static final Logger logger = LoggerFactory.getLogger(Game.class);

    private final UUID gameId;
    private final BoardManager boardManager;
    private final Configuration configuration;
    private final Rules rules;
    private final TurnManager turnManager;

    public Game(
            @NonNull UUID gameId,
            @NonNull BoardManager boardManager,
            @NonNull Configuration configuration,
            @NonNull Rules rules,
            @NonNull TurnManager turnManager) {
        this.gameId = gameId;
        this.boardManager = boardManager;
        this.configuration = configuration;
        this.rules = rules;
        this.turnManager = turnManager;
    }

    public void addMove(Move move) {
        logger.info("Add move: {}", move);
        if (!boardManager.isMoveValid(move)) {
            throw new IllegalMoveException("Move is illegal: " + move);
        }

        if (!rules.isMoveValid(move, boardManager.getBoard())) {
            throw new IllegalMoveException("Move is illegal: " + move);
        }

        Turn turn;
        if (move == null) {
            turn = new PassTurn();
        } else {
            Prisoners prisoners = boardManager.getPrisoners(move);
            turn = new NormalTurn(move, prisoners);
        }

        logger.debug("Turn={}", turn);

        boardManager.addTurn(turn);
        turnManager.addTurn(turn);
    }

    public UUID getGameId() {
        return gameId;
    }

    public Configuration getConfiguration() {
        return configuration;
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
