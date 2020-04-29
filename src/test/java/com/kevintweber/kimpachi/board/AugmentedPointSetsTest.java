package com.kevintweber.kimpachi.board;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AugmentedPointSetsTest {

    @Test
    void print() {
        AugmentedPointSet<Integer> augmentedPointSet = AugmentedPointSet.of(Point.of(4, 4), 10);
        assertThat(AugmentedPointSets.print(augmentedPointSet, 0))
                .as("Checking printing of augmented point set")
                .isEqualTo(
                        "    A    B    C    D    E    F    G    H    J    K    L    M    N    O    P    Q    R    S    T    \n" +
                                "19  -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    \n" +
                                "18  -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    \n" +
                                "17  -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    \n" +
                                "16  -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    \n" +
                                "15  -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    \n" +
                                "14  -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    \n" +
                                "13  -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    \n" +
                                "12  -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    \n" +
                                "11  -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    \n" +
                                "10  -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    \n" +
                                "9   -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    \n" +
                                "8   -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    \n" +
                                "7   -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    \n" +
                                "6   -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    \n" +
                                "5   -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    \n" +
                                "4   -    -    -    10   -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    \n" +
                                "3   -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    \n" +
                                "2   -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    \n" +
                                "1   -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    -    \n"
                );
    }
}