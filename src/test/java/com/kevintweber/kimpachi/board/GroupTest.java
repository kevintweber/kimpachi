package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.exception.UnconnectedException;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GroupTest {

    @Test
    void copyOf() {
        Group group = Group.of(Point.of(2, 2));
        Group otherGroup = Group.copyOf(group);
        assertThat(group)
                .as("Checking copyOf")
                .isNotSameAs(otherGroup)
                .isEqualTo(otherGroup);
        assertThat(group.toString())
                .as("Checking toString")
                .isEqualTo("Group(points=[bb])");
    }

    @Test
    void constructorWithSetOfPositions() {
        Group group = Group.of(
                Set.of(
                        Point.of(2, 2),
                        Point.of(2, 3),
                        Point.of(2, 4)
                )
        );

        assertThat(group.getPoints())
                .as("Checking points")
                .containsExactlyInAnyOrder(
                        Point.of(2, 2),
                        Point.of(2, 3),
                        Point.of(2, 4)
                );
        assertThat(group.contains(Point.of(2, 2)))
                .as("Checking contains")
                .isTrue();
        assertThat(group.isEmpty())
                .as("Checking isEmpty")
                .isFalse();
        assertThat(group.size())
                .as("Checking count")
                .isEqualTo(3);
    }

    @Test
    void constructorWithInvalidSetOfPoints() {
        assertThatThrownBy(() -> Group.of(
                Set.of(Point.of(2, 2), Point.of(2, 4))
        ))
                .as("Checking invalid set of points")
                .isInstanceOf(UnconnectedException.class);

        assertThatThrownBy(() -> Group.of(Set.of()))
                .as("Checking empty set of points")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void isAdjacent() {
        Group group = Group.of(Point.of(2, 2));

        assertThat(group.isAdjacent(Point.of(2, 3)))
                .as("Checking point is adjacent.")
                .isTrue();
        assertThat(group.isAdjacent(Point.of(5, 3)))
                .as("Checking point is not adjacent.")
                .isFalse();
        assertThat(group.isAdjacent(Point.of(2, 2)))
                .as("Checking same point is not adjacent.")
                .isFalse();
    }

    @Test
    void isIntersecting() {
        Group group = Group.of(Point.of(2, 2));
        Group otherGroup = Group.of(Point.of(3, 3));

        assertThat(group.isIntersecting(otherGroup))
                .as("Checking non-intersection.")
                .isFalse();
        assertThat(group.isIntersecting(Group.copyOf(group)))
                .as("Checking intersection of same group.")
                .isTrue();

        Group anotherGroup = Group.of(
                Set.of(Point.of(2, 2), Point.of(2, 3))
        );
        assertThat(group.isIntersecting(anotherGroup))
                .as("Checking intersection of different group.")
                .isTrue();
    }

    @Test
    void with() {
        Group group = Group.of(Point.of(2, 2));

        assertThat(group.getPoints())
                .as("Checking positions")
                .containsExactly(Point.of(2, 2));
        assertThat(group.contains(Point.of(2, 2)))
                .as("Checking contains")
                .isTrue();
        assertThat(group.contains(Point.of(2, 3)))
                .as("Checking contains")
                .isFalse();
        assertThat(group.isEmpty())
                .as("Checking isEmpty")
                .isFalse();
        assertThat(group.size())
                .as("Checking count")
                .isEqualTo(1);

        Group enlargedGroup = group.with(Point.of(2, 3));

        assertThat(enlargedGroup.getPoints())
                .as("Checking positions")
                .containsExactlyInAnyOrder(Point.of(2, 2), Point.of(2, 3));
        assertThat(enlargedGroup.contains(Point.of(2, 2)))
                .as("Checking contains")
                .isTrue();
        assertThat(enlargedGroup.contains(Point.of(2, 3)))
                .as("Checking contains")
                .isTrue();
        assertThat(enlargedGroup.isEmpty())
                .as("Checking isEmpty")
                .isFalse();
        assertThat(enlargedGroup.size())
                .as("Checking count")
                .isEqualTo(2);

        Group enlargedGroup2 = enlargedGroup.with(Point.of(2, 3));

        assertThat(enlargedGroup2)
                .as("Checking addition of same position")
                .isSameAs(enlargedGroup);

        assertThatThrownBy(() -> group.with(Point.of(8, 8)))
                .as("Checking trying to connect distant stone.")
                .isInstanceOf(UnconnectedException.class);
    }

    @Test
    void enlargeInCenter() {
        Group group = Group.of(Set.of(Point.of(5, 5), Point.of(5, 6)));
        Group enlargedGroup = group.enlarge();
        assertThat(enlargedGroup.size())
                .as("Checking enlarged group size")
                .isEqualTo(8);
        assertThat(enlargedGroup.getPoints())
                .as("Checking enlarged group points")
                .containsExactlyInAnyOrder(
                        Point.of(5, 4),
                        Point.of(4, 5),
                        Point.of(5, 5),
                        Point.of(6, 5),
                        Point.of(4, 6),
                        Point.of(5, 6),
                        Point.of(6, 6),
                        Point.of(5, 7)
                );
    }

    @Test
    void enlargeInCorner() {
        Group group = Group.of(Point.of(1, 1));
        Group enlargedGroup = group.enlarge();
        assertThat(enlargedGroup.size())
                .as("Checking enlarged group size")
                .isEqualTo(3);
        assertThat(enlargedGroup.getPoints())
                .as("Checking enlarged group points")
                .containsExactlyInAnyOrder(
                        Point.of(1, 1),
                        Point.of(2, 1),
                        Point.of(1, 2)
                );
    }

    @Test
    void enlargeMultipleTimes() {
        Group group = Group.of(Point.of(1, 1));
        Group enlargedGroup = group.enlarge(2);
        assertThat(enlargedGroup.size())
                .as("Checking enlarged group size")
                .isEqualTo(6);
        assertThat(enlargedGroup.getPoints())
                .as("Checking enlarged group points")
                .containsExactlyInAnyOrder(
                        Point.of(1, 1),
                        Point.of(2, 1),
                        Point.of(3, 1),
                        Point.of(1, 2),
                        Point.of(2, 2),
                        Point.of(1, 3)
                );
    }

    @Test
    void enlargeInvalidTimes() {
        Group group = Group.of(Point.of(1, 1));
        assertThatThrownBy(() -> group.enlarge(-1))
                .as("Checking exception is thrown with invalid enlargement count.")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void enlargeManyTimes() {
        Group group = Group.of(Point.of(9, 9));
        Group enlargedGroup = group.enlarge(100);
        assertThat(enlargedGroup.size())
                .as("Checking enlarged group size")
                .isEqualTo(361);
    }

    @Test
    void getNeighboringPoints() {
        Group group = Group.of(Point.of(1, 1));
        assertThat(group.getNeighboringPoints())
                .as("Checking neighboring points")
                .containsExactlyInAnyOrder(
                        Point.of(2, 1),
                        Point.of(1, 2)
                );
    }
}