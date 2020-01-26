package com.kevintweber.kimpachi.rules;

import com.kevintweber.kimpachi.board.Board;
import com.kevintweber.kimpachi.board.Move;
import com.kevintweber.kimpachi.exception.IllegalMoveException;
import com.kevintweber.kimpachi.game.Configuration;
import com.kevintweber.kimpachi.game.Prisoners;
import com.kevintweber.kimpachi.game.turn.NormalTurn;
import com.kevintweber.kimpachi.game.turn.PassTurn;
import com.kevintweber.kimpachi.game.turn.Turn;
import lombok.NonNull;

import java.util.Set;

abstract public class AbstractRules implements Rules {

    @Override
    public Turn move(
            @NonNull Configuration configuration,
            @NonNull Set<Integer> boardHashes,
            @NonNull Board board,
            Move move) {
        if (move == null) {
            return new PassTurn();
        }

        Board tempBoard = board.withMove(move);
        if (isBoardRepeated(tempBoard, boardHashes)) {
            throw new IllegalMoveException("Board is not unique with move: " + move);
        }

        handleSpecializedRules(configuration, board, move);
        Prisoners prisoners = determinePrisoners(configuration, board, move);

        return new NormalTurn(move, prisoners);
    }

    Prisoners determinePrisoners(
            @NonNull Configuration configuration,
            @NonNull Board board,
            @NonNull Move move) {
        // Temp until I figure out how to determine prisoners.
        return Prisoners.empty();
    }

    boolean isBoardRepeated(
            @NonNull Board board,
            @NonNull Set<Integer> boardHashes) {
        int boardHash = board.hashCode();

        return boardHashes.contains(boardHash);
    }

    abstract void handleSpecializedRules(
            @NonNull Configuration configuration,
            @NonNull Board board,
            @NonNull Move move);
}
