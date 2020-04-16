package com.kevintweber.kimpachi.board;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AugmentedPointSetTest {

    @Test
    void contains() {
        AugmentedPointSet<Integer> augmentedPointSet = AugmentedPointSet.of(Point.of(4,4), 10);
        assertThat(augmentedPointSet.contains(Point.of(3,3)))
                .isFalse();
        assertThat(augmentedPointSet.contains(Point.of(4,4)))
                .isTrue();
    }
}