package com.kevintweber.kimpachi.board;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PositionTest {

    @Test
    void of() {
        Position position = Position.of(Stone.Black, Point.of(1, 2));

        assertThat(position.getStone())
                .as("Checking stone")
                .isEqualTo(Stone.Black);
        assertThat(position.getPoint())
                .as("Checking point")
                .isEqualTo(Point.of(1, 2));
    }

    @ParameterizedTest
    @MethodSource("isAdjacentDataProvider")
    void isAdjacent(Position otherPosition, boolean expectedResult) {
        Position position = Position.of(Stone.Black, Point.of(2, 2));

        assertThat(position.isAdjacent(otherPosition))
                .as("Checking adjacent position")
                .isEqualTo(expectedResult);
        assertThat(position.isAdjacent(otherPosition.getPoint()))
                .as("Checking adjacent point")
                .isEqualTo(expectedResult);
    }

    private static Stream<Arguments> isAdjacentDataProvider() {
        return Stream.of(
                Arguments.of(Position.of(Stone.Black, Point.of(2, 2)), false),
                Arguments.of(Position.of(Stone.Black, Point.of(2, 3)), true),
                Arguments.of(Position.of(Stone.Black, Point.of(3, 3)), false)
        );
    }

    @Test
    void compare() {
        Position position1 = Position.of(Stone.Black, Point.of(2, 2));
        Position position2 = Position.copyOf(position1);
        assertThat(position1.compareTo(position2))
                .isEqualTo(0);

        Position position3 = Position.of(Stone.Black, Point.of(1, 1));
        assertThat(position1.compareTo(position3))
                .isGreaterThan(0);

        Position position4 = Position.of(Stone.Black, Point.of(3, 3));
        assertThat(position1.compareTo(position4))
                .isLessThan(0);

        Position position5 = Position.of(Stone.White, Point.of(2, 2));
        assertThat(position1.compareTo(position5))
                .isLessThan(0);

        assertThat(position1.compareTo(null))
                .isEqualTo(-1);
    }

    @Test
    void testToString() {
        Position position = Position.of(Stone.Black, Point.of(2, 2));
        assertThat(position.toString())
                .as("Checking toString")
                .isEqualTo("B[bb]");
    }
}