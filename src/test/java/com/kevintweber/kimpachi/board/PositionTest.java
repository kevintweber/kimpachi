package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.exception.InvalidPositionException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PositionTest {

    @Test
    void of() {
        Position test = Position.of(1, 2);
        assertThat(test.getX())
                .as("Checking x")
                .isEqualTo(1);
        assertThat(test.getY())
                .as("Checking y")
                .isEqualTo(2);

        assertThatThrownBy(() -> Position.of(-1, 15))
                .as("Checking invalid coordinates")
                .isInstanceOf(InvalidPositionException.class);
    }

    @Test
    void toSgf() {
        Position test = Position.of(1, 2);
        assertThat(test.toSgf())
                .as("Checking SGF output")
                .isEqualTo("ab");
    }

    @Test
    void compareTo() {
        Position test1 = Position.of(1, 2);
        Position test2 = Position.of(1, 2);
        Position test3 = Position.of(5, 5);
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