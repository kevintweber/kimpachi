package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.exception.InvalidPositionException;
import com.kevintweber.kimpachi.game.Prisoners;
import com.kevintweber.kimpachi.game.turn.Turn;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;

public final class BoardManager {

    private Board board;
    private final Set<Integer> boardHashes;

    public BoardManager(
            @NonNull Board board,
            @NonNull Set<Integer> boardHashes) {
        this.board = board;
        this.boardHashes = new HashSet<>(boardHashes);
    }

    public Board getBoard() {
        return board;
    }

    public void addTurn(@NonNull Turn turn) {
        if (turn.isPass()) {
            return;
        }

        // Add stone
        // Remove opposite color prisoners
        // Remove own color prisoners
    }

    public Prisoners getPrisoners(Move move) {
        if (move == null) {
            return Prisoners.empty();
        }

        // Determine opposite color prisoners
        // Determine own color prisoners
        return Prisoners.empty();
    }

    public boolean isMoveValid(Move move) {
        if (move == null) {
            return true;
        }

        try {
            Color currentColor = board.getColor(move.getPosition());
            if (!currentColor.equals(Color.Empty)) {
                return false;
            }

            Board tempBoard = board.withMove(move);

            return boardHashes.contains(tempBoard.hashCode());
        } catch (InvalidPositionException e) {
            return false;
        }
    }
}
