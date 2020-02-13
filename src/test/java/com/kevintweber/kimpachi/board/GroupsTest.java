package com.kevintweber.kimpachi.board;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class GroupsTest {

    @Test
    void associateNothing() {
        List<Group> groupList = Groups.associate(Set.of());

        assertThat(groupList)
                .as("Checking empty group list.")
                .isEmpty();
    }

    @Test
    void intersection() {
        Group group = Group.of(Set.of(Point.of(5, 5), Point.of(5, 6)));
        Group otherGroup = Group.of(Set.of(Point.of(5, 5), Point.of(5, 4)));
        List<Group> intersection = Groups.intersection(group, otherGroup);
        assertThat(intersection.size())
                .as("Checking the number of intersecting groups.")
                .isEqualTo(1);
        assertThat(intersection.get(0).getPoints())
                .as("Checking intersecting points.")
                .containsExactly(Point.of(5, 5));
    }

    @Test
    void union() {
        Group group = Group.of(Set.of(Point.of(5, 5), Point.of(5, 6)));
        Group otherGroup = Group.of(Set.of(Point.of(5, 5), Point.of(5, 4)));
        List<Group> union = Groups.union(group, otherGroup);
        assertThat(union.size())
                .as("Checking the number of union groups.")
                .isEqualTo(1);
        assertThat(union.get(0).getPoints())
                .as("Checking union group points")
                .containsExactlyInAnyOrder(
                        Point.of(5, 5),
                        Point.of(5, 6),
                        Point.of(5, 4)
                );
    }
}