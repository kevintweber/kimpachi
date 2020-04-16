package com.kevintweber.kimpachi.board;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AugmentedPointSetsTest {

    @Test
    void print() {
        AugmentedPointSet<Integer> augmentedPointSet = AugmentedPointSet.of(Point.of(4,4), 10);
        assertThat(AugmentedPointSets.print(augmentedPointSet, 0))
                .as("Checking printing of augmented point set")
                .isEqualTo("");
    }
}