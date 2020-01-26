package com.kevintweber.kimpachi.game;

import com.kevintweber.kimpachi.board.Color;
import com.kevintweber.kimpachi.board.Move;
import com.kevintweber.kimpachi.board.Position;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

class TurnManagerTest {

    @Test
    void getNextMoveColor() {
        TurnManager turnManager = new TurnManager(new LinkedList<>());
        assertThat(turnManager.getNextMoveColor())
                .as("Checking first move color")
                .isEqualTo(Color.Black);

        turnManager.addTurn(
                new Turn(Move.normalMove(Color.Black, Position.of(2, 2)))
        );
        assertThat(turnManager.getNextMoveColor())
                .as("Checking next color")
                .isEqualTo(Color.White);
    }

    @Test
    void isGameOver() {
        TurnManager turnManager = new TurnManager(new LinkedList<>());
        turnManager.addTurn(
                new Turn(Move.normalMove(Color.Black, Position.of(2, 2)))
        );

        assertThat(turnManager.isGameOver())
                .as("Checking is game over after 1 turn")
                .isFalse();

        turnManager.addTurn(
                new Turn(Move.normalMove(Color.White, Position.of(3, 3)))
        );

        assertThat(turnManager.isGameOver())
                .as("Checking is game over after 2 turns")
                .isFalse();

        turnManager.addTurn(new Turn(Move.passMove(Color.Black)));
        assertThat(turnManager.isGameOver())
                .as("Checking is game over after 1 pass move")
                .isFalse();

        turnManager.addTurn(
                new Turn(Move.normalMove(Color.White, Position.of(4, 4)))
        );
        turnManager.addTurn(new Turn(Move.passMove(Color.Black)));
        assertThat(turnManager.isGameOver())
                .as("Checking is game over after 2 non-consecutinve pass moves")
                .isFalse();

        turnManager.addTurn(new Turn(Move.passMove(Color.White)));
        assertThat(turnManager.isGameOver())
                .as("Checking is game over after 2 non-consecutinve pass moves")
                .isTrue();
    }
}