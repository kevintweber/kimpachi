package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.exception.InvalidPositionException;
import com.kevintweber.kimpachi.game.Configuration;
import com.kevintweber.kimpachi.helpers.EntityHelper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {

    @Test
    void empty() {
        Configuration configuration9 = EntityHelper.buildConfiguration(9);
        Board board1 = Board.empty(configuration9);
        Board board2 = Board.empty(configuration9);

        Configuration configuration19 = EntityHelper.buildConfiguration(19);
        Board board3 = Board.empty(configuration19);

        assertThat(board1)
                .as("Checking board")
                .isSameAs(board2)
                .isNotEqualTo(board3);
    }

    @Test
    void getSize() {
        Configuration configuration = EntityHelper.buildConfiguration(19);
        Board board = Board.empty(configuration);
        assertThat(board.getSize())
                .as("Checking size")
                .isEqualTo(19);
    }

    @Test
    void isValid() {
        Configuration configuration = EntityHelper.buildConfiguration();
        Board board = Board.empty(configuration);
        Position validPosition = Position.of(1, 1);
        assertThat(board.isOnBoard(validPosition))
                .as("Checking valid position")
                .isTrue();

        Position invalidPosition = Position.of(10000, 1);
        assertThat(board.isOnBoard(invalidPosition))
                .as("Checking invalid position")
                .isFalse();

        assertThatThrownBy(() -> board.getColor(invalidPosition))
                .as("Checking invalid position give exception")
                .isInstanceOf(InvalidPositionException.class);
    }

    @Test
    void boardManipulations() {
        Configuration configuration = EntityHelper.buildConfiguration();
        Board board = Board.empty(configuration);
        Position position = Position.of(1, 1);
        assertThat(board.getColor(position))
                .as("Checking empty position")
                .isEqualTo(Color.Empty);
        assertThat(board.isOccupied(position))
                .as("Checking position is not occupied")
                .isFalse();

        Move blackMove = Move.normalMove(Color.Black, position);
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