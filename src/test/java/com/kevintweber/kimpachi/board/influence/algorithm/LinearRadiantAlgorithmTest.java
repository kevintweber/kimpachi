package com.kevintweber.kimpachi.board.influence.algorithm;

import com.kevintweber.kimpachi.board.Point;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class LinearRadiantAlgorithmTest {

    @ParameterizedTest
    @MethodSource("getInfluenceDataProvider")
    void getInfluence(Point testPoint, BigDecimal factor, int expectedInfluence) {
        Point source = Point.of(1, 1);
        LinearRadiantAlgorithm linearAlgorithm = new LinearRadiantAlgorithm(factor);

        int influence = linearAlgorithm.getInfluence(source, testPoint);

        assertThat(influence)
                .as("Checking influence")
                .isEqualTo(expectedInfluence);
    }

    private static Stream<Arguments> getInfluenceDataProvider() {
        return Stream.of(
                Arguments.of(
                        Point.of(1, 1),
                        new BigDecimal("0.5"),
                        100
                ),
                Arguments.of(
                        Point.of(1, 2),
                        new BigDecimal("0.5"),
                        50
                ),
                Arguments.of(
                        Point.of(1, 3),
                        new BigDecimal("0.5"),
                        25
                ),
                Arguments.of(
                        Point.of(1, 4),
                        new BigDecimal("0.5"),
                        12
                ),
                Arguments.of(
                        Point.of(2, 1),
                        new BigDecimal("0.5"),
                        50
                ),
                Arguments.of(
                        Point.of(2, 2),
                        new BigDecimal("0.5"),
                        37
                ),
                Arguments.of(
                        Point.of(2, 3),
                        new BigDecimal("0.5"),
                        21
                ),
                Arguments.of(
                        Point.of(2, 4),
                        new BigDecimal("0.5"),
                        11
                ),
                Arguments.of(
                        Point.of(3, 1),
                        new BigDecimal("0.5"),
                        25
                ),
                Arguments.of(
                        Point.of(3, 2),
                        new BigDecimal("0.5"),
                        21
                ),
                Arguments.of(
                        Point.of(3, 3),
                        new BigDecimal("0.5"),
                        14
                ),
                Arguments.of(
                        Point.of(3, 4),
                        new BigDecimal("0.5"),
                        8
                ),
                Arguments.of(
                        Point.of(4, 6),
                        new BigDecimal("0.5"),
                        1
                ),
                Arguments.of(
                        Point.of(12, 6),
                        new BigDecimal("0.5"),
                        0
                ),
                Arguments.of(
                        Point.of(1, 1),
                        new BigDecimal("0.2"),
                        100
                ),
                Arguments.of(
                        Point.of(1, 2),
                        new BigDecimal("0.2"),
                        20
                ),
                Arguments.of(
                        Point.of(1, 3),
                        new BigDecimal("0.2"),
                        4
                )
        );
    }
}