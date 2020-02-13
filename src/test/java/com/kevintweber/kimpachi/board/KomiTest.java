package com.kevintweber.kimpachi.board;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class KomiTest {

    @Test
    void isKomi() {
        Komi komi = Komi.KOMI;
        assertThat(komi.isKomi(Point.of(1,1)))
                .isFalse();
        assertThat(komi.isKomi(Point.of(4,4)))
                .isTrue();
    }

    @Test
    void testToString() {
        Komi komi = Komi.KOMI;
        assertThat(komi.toString())
                .as("Checking toString")
                .isEqualTo("Komi{}");
    }
}