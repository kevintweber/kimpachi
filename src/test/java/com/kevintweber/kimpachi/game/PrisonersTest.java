package com.kevintweber.kimpachi.game;

import com.kevintweber.kimpachi.board.Point;
import com.kevintweber.kimpachi.board.Stone;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class PrisonersTest {

    @Test
    void countWithNoPrisoners() {
        Prisoners prisoners = Prisoners.of(Set.of(), Set.of());
        assertThat(prisoners.count(Stone.White))
                .isEqualTo(0);
        assertThat(prisoners.count(Stone.Black))
                .isEqualTo(0);
        assertThat(prisoners.isEmpty())
                .isTrue();
    }

    @Test
    void count() {
        Prisoners prisoners = Prisoners.of(Set.of(Point.of(4, 4)), Set.of());
        assertThat(prisoners.count(Stone.White))
                .isEqualTo(0);
        assertThat(prisoners.count(Stone.Black))
                .isEqualTo(1);
        assertThat(prisoners.isEmpty())
                .isFalse();
    }
}