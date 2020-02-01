package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.utilities.SgfToken;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

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
                        new SgfToken("B", "aa"),
                        Move.normalMove(Color.Black, Position.of(1, 1))
                ),
                Arguments.of(
                        "White normal move",
                        new SgfToken("W", "ca"),
                        Move.normalMove(Color.White, Position.of(3, 1))
                ),
                Arguments.of(
                        "Black pass move",
                        new SgfToken("B", ""),
                        Move.passMove(Color.Black)
                ),
                Arguments.of(
                        "White pass move",
                        new SgfToken("W", ""),
                        Move.passMove(Color.White)
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
                        Move.passMove(Color.Black),
                        "B[]"
                ),
                Arguments.of(
                        "White pass move",
                        Move.passMove(Color.White),
                        "W[]"
                ),
                Arguments.of(
                        "Black normal move",
                        Move.normalMove(Color.Black, Position.of(2,4)),
                        "B[bd]"
                ),
                Arguments.of(
                        "White normal move",
                        Move.normalMove(Color.White, Position.of(4,2)),
                        "W[db]"
                )
        );
    }
}