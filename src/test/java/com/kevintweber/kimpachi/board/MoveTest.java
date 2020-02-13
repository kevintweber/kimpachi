package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.sgf.SgfToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MoveTest {

    @ParameterizedTest
    @MethodSource("sgfDataProvider")
    void sgf(String description, SgfToken token, Move expectedMove) {
        Move actual = Move.fromSgf(token);
        assertThat(actual)
                .as("Checking sgfToken to move: " + description)
                .isEqualTo(expectedMove);
    }

    private static Stream<Arguments> sgfDataProvider() {
        return Stream.of(
                Arguments.of(
                        "Black normal move",
                        new SgfToken("B", "AA"),
                        Move.normalMove(Stone.Black, Point.of(1, 1))
                ),
                Arguments.of(
                        "White normal move",
                        new SgfToken("W", "CA"),
                        Move.normalMove(Stone.White, Point.of(3, 1))
                ),
                Arguments.of(
                        "Black pass move",
                        new SgfToken("B", ""),
                        Move.passMove(Stone.Black)
                ),
                Arguments.of(
                        "White pass move",
                        new SgfToken("W", ""),
                        Move.passMove(Stone.White)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("toStringDataProvider")
    void toSgf(String description, Move move, String expected) {
        String actual = move.toString();
        assertThat(actual)
                .as("Checking toSgf: " + description)
                .isEqualTo(expected);
    }

    private static Stream<Arguments> toStringDataProvider() {
        return Stream.of(
                Arguments.of(
                        "Black pass move",
                        Move.passMove(Stone.Black),
                        "B[]"
                ),
                Arguments.of(
                        "White pass move",
                        Move.passMove(Stone.White),
                        "W[]"
                ),
                Arguments.of(
                        "Black normal move",
                        Move.normalMove(Stone.Black, Point.of(2,4)),
                        "B[bd]"
                ),
                Arguments.of(
                        "White normal move",
                        Move.normalMove(Stone.White, Point.of(4,2)),
                        "W[db]"
                )
        );
    }

    @Test
    void toPositionAndPoint() {
        Move move1 = Move.normalMove(Stone.Black, Point.of(2,4));
        assertThat(move1.getPosition())
                .as("Checking position")
                .isEqualTo(Position.of(Stone.Black, Point.of(2,4)));
        assertThat(move1.getPoint())
                .as("Checking point")
                .isEqualTo(Point.of(2,4));

        Move move2 = Move.passMove(Stone.Black);
        assertThatThrownBy(move2::getPosition)
                .as("Checking position of pass move")
                .isInstanceOf(IllegalStateException.class);
        assertThat(move2.getPoint())
                .as("Checking point of pass move")
                .isNull();
    }
}