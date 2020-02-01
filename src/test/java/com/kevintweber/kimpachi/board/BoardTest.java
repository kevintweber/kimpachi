package com.kevintweber.kimpachi.board;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

    @Test
    void empty() {
        Board board1 = Board.empty();
        Board board2 = Board.empty();

        assertThat(board1)
                .as("Checking empty board")
                .isSameAs(board2);
    }

    @Test
    void boardManipulations() {
        Board board = Board.empty();
        Position position = Position.of(1, 1);
        assertThat(board.getColor(position))
                .as("Checking empty position")
                .isEqualTo(Color.Empty);
        assertThat(board.isOccupied(position))
                .as("Checking position is not occupied")
                .isFalse();

        Move blackMove = Move.normalMove(Stone.Black, position);
        Board nextBoard = board.withMove(blackMove);
        assertThat(nextBoard)
                .as("Checking boards are immutable")
                .isNotEqualTo(board);
        assertThat(nextBoard.getColor(position))
                .as("Checking modified position")
                .isEqualTo(Color.Black);
        assertThat(nextBoard.isOccupied(position))
                .as("Checking position is occupied")
                .isTrue();
        assertThat(nextBoard.toString())
                .as("Checking toString")
                .isNotEqualTo(board.toString());

        Board nextBoard2 = nextBoard.withMove(blackMove);
        assertThat(nextBoard2)
                .as("Checking immutability of adding same color")
                .isSameAs(nextBoard);

        Board nextBoard3 = nextBoard.clear(position);
        assertThat(nextBoard3)
                .as("Checking cyclic immutablility")
                .isNotSameAs(board)
                .isEqualTo(board);
        assertThat(nextBoard3.toString())
                .as("Checking toString")
                .isEqualTo(board.toString());
    }
}