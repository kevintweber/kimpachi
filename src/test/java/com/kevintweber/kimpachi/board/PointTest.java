package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.exception.InvalidPositionException;
import com.kevintweber.kimpachi.exception.SgfException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PointTest {

    @Test
    void of() {
        Point test = Point.of(1, 2);
        assertThat(test.getX())
                .as("Checking x")
                .isEqualTo(1);
        assertThat(test.getY())
                .as("Checking y")
                .isEqualTo(2);

        assertThatThrownBy(() -> Point.of(-1, 15))
                .as("Checking invalid coordinates")
                .isInstanceOf(InvalidPositionException.class);

        assertThatThrownBy(() -> Point.of(15, -15))
                .as("Checking invalid coordinates")
                .isInstanceOf(InvalidPositionException.class);

        assertThatThrownBy(() -> Point.of(15, 150))
                .as("Checking invalid coordinates")
                .isInstanceOf(InvalidPositionException.class);
    }

    @ParameterizedTest
    @MethodSource("fromSgfDataProvider")
    void fromSgf(String sgfValue, Point expectedPoint) {
        Point actualPoint = Point.fromSgf(sgfValue);
        assertThat(actualPoint)
                .as("Checking position")
                .isEqualTo(expectedPoint);
    }

    private static Stream<Arguments> fromSgfDataProvider() {
        return Stream.of(
                Arguments.of(
                        "aa",
                        Point.of(1, 1)
                ),
                Arguments.of(
                        "bb",
                        Point.of(2, 2)
                ),
                Arguments.of(
                        "eh",
                        Point.of(5, 8)
                )
        );
    }

    @Test
    void fromSgfException() {
        assertThatThrownBy(() -> Point.fromSgf("asdf"))
                .as("Checking invalid coordinates")
                .isInstanceOf(SgfException.class);
    }

    @Test
    void contains() {
        Point test = Point.of(1, 2);
        Point testCopy = Point.of(1, 2);
        Point anotherPoint = Point.of(2, 3);

        assertThat(test.contains(test))
                .isTrue();
        assertThat(test.contains(testCopy))
                .isTrue();
        assertThat(test.contains(anotherPoint))
                .isFalse();
    }

    @Test
    void count() {
        Point test = Point.of(1, 2);
        assertThat(test.size())
                .isEqualTo(1);
    }

    @Test
    void getPoints() {
        Point test = Point.of(1, 2);
        assertThat(test.getPoints())
                .isEqualTo(Set.of(test));
    }

    @Test
    void getNeighboringPoints() {
        Point test = Point.of(1, 2);
        assertThat(test.getNeighboringPoints())
                .isEqualTo(Set.of(
                        Point.of(1, 1),
                        Point.of(1, 3),
                        Point.of(2, 2)
                ));
    }

    @ParameterizedTest
    @MethodSource("isAdjacentDataProvider")
    void isAdjacent(Point point, Point otherPoint, boolean expectedResult) {
        assertThat(point.isAdjacent(otherPoint))
                .as("Checking isAdjacent")
                .isEqualTo(expectedResult);
    }

    private static Stream<Arguments> isAdjacentDataProvider() {
        return Stream.of(
                Arguments.of(
                        Point.of(1, 1),
                        Point.of(1, 2),
                        true
                ),
                Arguments.of(
                        Point.of(2, 2),
                        Point.of(1, 2),
                        true
                ),
                Arguments.of(
                        Point.of(2, 2),
                        Point.of(1, 1),
                        false
                ),
                Arguments.of(
                        Point.of(2, 2),
                        Point.of(5, 12),
                        false
                )
        );
    }

    @Test
    void print() {
        Point point = Point.of(3, 3);
        assertThat(point.print())
                .as("Checking printing the board.")
                .isEqualTo("    A B C D E F G H J K L M N O P Q R S T \n" +
                        "19  . . . . . . . . . . . . . . . . . . . \n" +
                        "18  . . . . . . . . . . . . . . . . . . . \n" +
                        "17  . . . . . . . . . . . . . . . . . . . \n" +
                        "16  . . . + . . . . . + . . . . . + . . . \n" +
                        "15  . . . . . . . . . . . . . . . . . . . \n" +
                        "14  . . . . . . . . . . . . . . . . . . . \n" +
                        "13  . . . . . . . . . . . . . . . . . . . \n" +
                        "12  . . . . . . . . . . . . . . . . . . . \n" +
                        "11  . . . . . . . . . . . . . . . . . . . \n" +
                        "10  . . . + . . . . . + . . . . . + . . . \n" +
                        "9   . . . . . . . . . . . . . . . . . . . \n" +
                        "8   . . . . . . . . . . . . . . . . . . . \n" +
                        "7   . . . . . . . . . . . . . . . . . . . \n" +
                        "6   . . . . . . . . . . . . . . . . . . . \n" +
                        "5   . . . . . . . . . . . . . . . . . . . \n" +
                        "4   . . . + . . . . . + . . . . . + . . . \n" +
                        "3   . . # . . . . . . . . . . . . . . . . \n" +
                        "2   . . . . . . . . . . . . . . . . . . . \n" +
                        "1   . . . . . . . . . . . . . . . . . . . \n");
    }

    @Test
    void toSgf() {
        Point test = Point.of(1, 2);
        assertThat(test.toString())
                .as("Checking SGF output")
                .isEqualTo("ab");
    }

    @Test
    void compareTo() {
        Point test1 = Point.of(1, 2);
        Point test2 = Point.of(1, 2);
        Point test3 = Point.of(5, 5);
        assertThat(test1.compareTo(null))
                .as("Checking compareTo with null")
                .isEqualTo(-1);
        assertThat(test1.compareTo(test2))
                .as("Checking compareTo with equal position")
                .isEqualTo(0);
        assertThat(test1.compareTo(test3))
                .as("Checking compareTo with less position")
                .isLessThan(0);
        assertThat(test3.compareTo(test1))
                .as("Checking compareTo with greater position")
                .isGreaterThan(0);
    }
}