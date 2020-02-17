package com.kevintweber.kimpachi.board.influence;

import com.kevintweber.kimpachi.board.Board;
import com.kevintweber.kimpachi.board.Move;
import com.kevintweber.kimpachi.board.Point;
import com.kevintweber.kimpachi.board.Stone;
import com.kevintweber.kimpachi.board.influence.algorithm.Linear;
import com.kevintweber.kimpachi.game.Prisoners;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class InfluenceEngineTest {

    @Test
    void calculate() {
        Board board = Board.empty();
        board = board.withMove(Move.normalMove(Stone.Black, Point.of(3, 3)), Prisoners.empty());
        board = board.withMove(Move.normalMove(Stone.Black, Point.of(3, 5)), Prisoners.empty());

        Linear algorithm = new Linear(new BigDecimal("0.4"));
        InfluenceEngine engine = new InfluenceEngine(algorithm);
        Map<Point, Integer> result = engine.calculate(Stone.Black, board);

        assertThat(result.size())
                .isEqualTo(361);

        assertThat(result)
                .containsEntry(Point.of(3, 3), 100)
                .containsEntry(Point.of(3, 4), 80)
                .containsEntry(Point.of(3, 5), 100);
    }
}