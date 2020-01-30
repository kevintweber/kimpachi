package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.exception.InvalidPositionException;
import com.kevintweber.kimpachi.game.Configuration;
import com.kevintweber.kimpachi.game.Prisoners;
import com.kevintweber.kimpachi.game.Turn;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;

public final class BoardManager {

    private Board board;
    private final Set<Integer> boardHashes;

    private BoardManager(
            @NonNull Board board,
            @NonNull Set<Integer> boardHashes) {
        this.board = board;
        this.boardHashes = new HashSet<>(boardHashes);
    }

    public static BoardManager newBoard(@NonNull Configuration configuration) {
        return new BoardManager(
                Board.empty(configuration),
                new HashSet<>()
        );
    }

    public static BoardManager inProgressBoard(
            @NonNull Board board,
            @NonNull Set<Integer> boardHashes) {
        return new BoardManager(board, boardHashes);
    }

    public Board getBoard() {
        return board;
    }

    public void addTurn(@NonNull Turn turn) {
        if (turn.isPass()) {
            return;
        }

        // Add stone
        board = board.withMove(turn.getMove());

        // Remove opposite color prisoners
        // Remove own color prisoners
    }

    public Prisoners getPrisoners(@NonNull Move move) {
        if (move.isPassMove()) {
            return Prisoners.empty();
        }

        // Determine opposite color prisoners
        // Determine own color prisoners
        return Prisoners.empty();
    }

    public boolean isMoveValid(@NonNull Move move) {
        if (move.isPassMove()) {
            return true;
        }

        try {
            Color currentColor = board.getColor(move.getPosition());
            if (!currentColor.equals(Color.Empty)) {
                return false;
            }

            Board tempBoard = board.withMove(move);

            return !boardHashes.contains(tempBoard.hashCode());
        } catch (InvalidPositionException e) {
            return false;
        }
    }
}
