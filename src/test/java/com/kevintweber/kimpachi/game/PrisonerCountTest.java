package com.kevintweber.kimpachi.game;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PrisonerCountTest {

    @Test
    void constructor() {
        PrisonerCount prisonerCount = new PrisonerCount(2, 4);

        assertThat(prisonerCount.getBlackPrisonerCount())
                .as("Checking black prisoner count")
                .isEqualTo(2);
        assertThat(prisonerCount.getWhitePrisonerCount())
                .as("Checking white prisoner count")
                .isEqualTo(4);

        assertThatThrownBy(() -> new PrisonerCount(-1, 0))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new PrisonerCount(0, -1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void add() {
        PrisonerCount prisonerCount1 = new PrisonerCount(2, 4);
        PrisonerCount prisonerCount2 = new PrisonerCount(1, 1);
        PrisonerCount result = prisonerCount1.add(prisonerCount2);

        assertThat(result.getBlackPrisonerCount())
                .as("Checking black prisoner count")
                .isEqualTo(3);
        assertThat(result.getWhitePrisonerCount())
                .as("Checking white prisoner count")
                .isEqualTo(5);
    }
}