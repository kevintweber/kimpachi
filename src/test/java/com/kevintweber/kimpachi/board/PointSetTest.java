package com.kevintweber.kimpachi.board;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class PointSetTest {

    @Test
    void contains() {
        PointSet pointSet = PointSet.of(Point.of(4, 4));
        assertThat(pointSet.contains(Point.of(4, 4)))
                .isTrue();
        assertThat(pointSet.contains(Point.of(3, 3)))
                .isFalse();
    }

    @Test
    void getPoints() {
        PointSet pointSet = PointSet.of(Point.of(4, 4));
        Set<Point> setOfPoints = pointSet.getPoints();
        assertThat(setOfPoints)
                .containsExactly(Point.of(4, 4));
    }

    @Test
    void getNeighboringPointsOfSinglePoint() {
        PointSet pointSet = PointSet.of(Point.of(4, 4));
        Set<Point> neighbors = pointSet.getNeighboringPoints();
        assertThat(neighbors.size())
                .isEqualTo(4);
        assertThat(neighbors)
                .containsExactlyInAnyOrder(
                        Point.of(3, 4),
                        Point.of(4, 3),
                        Point.of(5, 4),
                        Point.of(4, 5)
                );
    }

    @Test
    void getNeighboringPointsOfMultiplePoints() {
        PointSet pointSet = PointSet.of(Set.of(Point.of(4, 4), Point.of(4, 5)));
        Set<Point> neighbors = pointSet.getNeighboringPoints();
        assertThat(neighbors.size())
                .isEqualTo(6);
        assertThat(neighbors)
                .contains(
                        Point.of(3, 4),
                        Point.of(4, 3),
                        Point.of(5, 4)
                );
    }

    @Test
    void isAdjacent() {
        PointSet pointSet = PointSet.of(Point.of(4, 4));
        assertThat(pointSet.isAdjacent(Point.of(4, 5)))
                .isTrue();
        assertThat(pointSet.isAdjacent(Point.of(4, 6)))
                .isFalse();
        assertThat(pointSet.isAdjacent(Point.of(4, 4)))
                .isFalse();
    }

    @Test
    void isEmpty() {
        PointSet pointSet = PointSet.of(Point.of(4, 4));
        assertThat(pointSet.isEmpty())
                .isFalse();
    }

    @Test
    void isIntersecting() {
        PointSet pointSet1 = PointSet.of(Set.of(Point.of(4, 4), Point.of(4, 5)));
        PointSet pointSet2 = PointSet.of(Set.of(Point.of(4, 4), Point.of(5, 4)));
        PointSet pointSet3 = PointSet.of(Set.of(Point.of(14, 14), Point.of(14, 15)));

        assertThat(pointSet1.isIntersecting(pointSet2))
                .isTrue();
        assertThat(pointSet2.isIntersecting(pointSet1))
                .isTrue();

        assertThat(pointSet1.isIntersecting(pointSet3))
                .isFalse();
        assertThat(pointSet3.isIntersecting(pointSet1))
                .isFalse();
    }

    @Test
    void size() {
        PointSet pointSet = PointSet.of(Point.of(4, 4));
        assertThat(pointSet.size())
                .isEqualTo(1);
    }

    @Test
    void with() {
        PointSet pointSet1 = PointSet.of(Point.of(4, 4));
        PointSet pointSet2 = pointSet1.with(Point.of(10, 10));

        assertThat(pointSet1.isIntersecting(pointSet2))
                .isTrue();
        assertThat(pointSet1.size())
                .isEqualTo(1);
        assertThat(pointSet2.size())
                .isEqualTo(2);
    }

    @Test
    void withSelf() {
        PointSet pointSet1 = PointSet.of(Point.of(4, 4));
        PointSet pointSet2 = pointSet1.with(Point.of(4, 4));

        assertThat(pointSet1)
                .isEqualTo(pointSet2);
    }

    @Test
    void without() {
        PointSet pointSet1 = PointSet.of(Set.of(Point.of(4, 4), Point.of(4, 5)));
        PointSet pointSet2 = pointSet1.without(Point.of(10, 10));

        assertThat(pointSet1)
                .isEqualTo(pointSet2);
        assertThat(pointSet2.size())
                .isEqualTo(2);

        PointSet pointSet3 = pointSet1.without(Point.of(4, 4));
        assertThat(pointSet1)
                .isNotEqualTo(pointSet3);
        assertThat(pointSet3.size())
                .isEqualTo(1);
    }

    @Test
    void print() {
        PointSet pointSet = PointSet.of(Point.of(3, 3));
        assertThat(pointSet.print())
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
}