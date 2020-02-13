package com.kevintweber.kimpachi.game;

import com.kevintweber.kimpachi.board.Board;
import com.kevintweber.kimpachi.board.Move;
import com.kevintweber.kimpachi.board.Point;
import com.kevintweber.kimpachi.board.Stone;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TurnManagerTest {

    @Test
    void isGameOver() {
        Configuration configuration = new Configuration.Builder()
                .build();
        TurnManager turnManager = new TurnManager(configuration);
        assertThat(turnManager.isGameOver())
                .isFalse();
        assertThat(turnManager.getNextMoveStone())
                .isEqualTo(Stone.Black);

        Move firstMove = Move.normalMove(Stone.Black, Point.of(4, 4));
        Board firstBoard = Board.of(configuration);
        firstBoard = firstBoard.withMove(firstMove);
        turnManager.addTurn(
                new Turn(firstBoard, firstMove, Prisoners.empty())
        );
        assertThat(turnManager.isGameOver())
                .isFalse();
        assertThat(turnManager.getNextMoveStone())
                .isEqualTo(Stone.White);

        Move secondMove = Move.normalMove(Stone.White, Point.of(12, 12));
        Board secondBoard = firstBoard.withMove(secondMove);
        turnManager.addTurn(
                new Turn(secondBoard, secondMove, Prisoners.empty())
        );
        assertThat(turnManager.isGameOver())
                .isFalse();
        assertThat(turnManager.getNextMoveStone())
                .isEqualTo(Stone.Black);

        Move thirdMove = Move.passMove(Stone.Black);
        Board thirdBoard = secondBoard.withMove(thirdMove);
        turnManager.addTurn(
                new Turn(thirdBoard, thirdMove, Prisoners.empty())
        );
        assertThat(turnManager.isGameOver())
                .isFalse();
        assertThat(turnManager.getNextMoveStone())
                .isEqualTo(Stone.White);

        Move fourthMove = Move.passMove(Stone.White);
        Board fourthBoard = thirdBoard.withMove(fourthMove);
        turnManager.addTurn(
                new Turn(fourthBoard, fourthMove, Prisoners.empty())
        );
        assertThat(turnManager.isGameOver())
                .isTrue();
        assertThat(turnManager.getNextMoveStone())
                .isEqualTo(Stone.Black);

        assertThat(turnManager.toSgf())
                .as("Checking SGF output")
                .isEqualTo(";B[dd];W[ll];B[];W[]");
    }
}