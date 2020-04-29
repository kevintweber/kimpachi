package com.kevintweber.kimpachi.board.influence.engine;

import com.kevintweber.kimpachi.board.Board;
import com.kevintweber.kimpachi.board.Point;
import com.kevintweber.kimpachi.board.Stone;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class BouzyEngineTest {

    @Test
    void calculateEmptyBoard() {
        // Given
        BouzyEngine bouzyEngine = new BouzyEngine();
        Board board = Board.empty();

        // When
        Map<Point, Integer> result = bouzyEngine.calculate(Stone.Black, board);

        // Then
        assertThat(result)
                .as("Checking bouzy engine with empty board.")
                .isEqualTo(Map.of());
    }
}