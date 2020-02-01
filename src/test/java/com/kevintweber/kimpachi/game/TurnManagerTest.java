package com.kevintweber.kimpachi.game;

import com.kevintweber.kimpachi.board.Move;
import com.kevintweber.kimpachi.board.Position;
import com.kevintweber.kimpachi.board.Stone;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

class TurnManagerTest {

    @Test
    void getNextMoveStone() {
        Configuration configuration = new Configuration.Builder().build();
        TurnManager turnManager = new TurnManager(configuration, new LinkedList<>());
        assertThat(turnManager.getNextMoveStone())
                .as("Checking first move Stone")
                .isEqualTo(Stone.Black);

        turnManager.addTurn(
                new Turn(Move.normalMove(Stone.Black, Position.of(2, 2)))
        );
        assertThat(turnManager.getNextMoveStone())
                .as("Checking next Stone")
                .isEqualTo(Stone.White);
    }

    @Test
    void isGameOver() {
        Configuration configuration = new Configuration.Builder().build();
        TurnManager turnManager = new TurnManager(configuration, new LinkedList<>());
        turnManager.addTurn(
                new Turn(Move.normalMove(Stone.Black, Position.of(2, 2)))
        );

        assertThat(turnManager.isGameOver())
                .as("Checking is game over after 1 turn")
                .isFalse();

        turnManager.addTurn(
                new Turn(Move.normalMove(Stone.White, Position.of(3, 3)))
        );

        assertThat(turnManager.isGameOver())
                .as("Checking is game over after 2 turns")
                .isFalse();

        turnManager.addTurn(new Turn(Move.passMove(Stone.Black)));
        assertThat(turnManager.isGameOver())
                .as("Checking is game over after 1 pass move")
                .isFalse();

        turnManager.addTurn(
                new Turn(Move.normalMove(Stone.White, Position.of(4, 4)))
        );
        turnManager.addTurn(new Turn(Move.passMove(Stone.Black)));
        assertThat(turnManager.isGameOver())
                .as("Checking is game over after 2 non-consecutinve pass moves")
                .isFalse();

        turnManager.addTurn(new Turn(Move.passMove(Stone.White)));
        assertThat(turnManager.isGameOver())
                .as("Checking is game over after 2 non-consecutinve pass moves")
                .isTrue();
    }
}