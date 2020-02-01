package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.exception.NonAdjacentPositionException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GroupTest {

    @Test
    void copyOf() {
        Group group = Group.of(Stone.Black, Position.of(2, 2));
        Group otherGroup = Group.copyOf(group);
        assertThat(group)
                .as("Checking copyOf")
                .isNotSameAs(otherGroup)
                .isEqualTo(otherGroup);
    }

    @Test
    void with() {
        Group group = Group.of(Stone.Black, Position.of(2, 2));
        assertThat(group.getPositions())
                .as("Checking positions")
                .containsExactly(Position.of(2, 2));
        assertThat(group.getStone())
                .as("Checking stone")
                .isEqualTo(Stone.Black);
        assertThat(group.contains(Position.of(2, 2)))
                .as("Checking contains")
                .isTrue();
        assertThat(group.contains(Position.of(2, 3)))
                .as("Checking contains")
                .isFalse();
        assertThat(group.isEmpty())
                .as("Checking isEmpty")
                .isFalse();
        assertThat(group.count())
                .as("Checking count")
                .isEqualTo(1);

        Group enlargedGroup = group.with(Position.of(2, 3));
        assertThat(group.getPositions())
                .as("Checking positions")
                .containsExactlyInAnyOrder(Position.of(2, 2), Position.of(2, 3));
        assertThat(group.getStone())
                .as("Checking stone")
                .isEqualTo(Stone.Black);
        assertThat(group.contains(Position.of(2, 2)))
                .as("Checking contains")
                .isTrue();
        assertThat(group.contains(Position.of(2, 3)))
                .as("Checking contains")
                .isTrue();
        assertThat(group.isEmpty())
                .as("Checking isEmpty")
                .isFalse();
        assertThat(group.count())
                .as("Checking count")
                .isEqualTo(2);

        Group enlargedGroup2 = enlargedGroup.with(Position.of(2, 3));
        assertThat(enlargedGroup2)
                .as("Checking addition of same position")
                .isSameAs(enlargedGroup);

        assertThatThrownBy(() -> group.with(Position.of(8, 8)))
                .as("Checking trying to connect distant stone.")
                .isInstanceOf(NonAdjacentPositionException.class);
    }
}