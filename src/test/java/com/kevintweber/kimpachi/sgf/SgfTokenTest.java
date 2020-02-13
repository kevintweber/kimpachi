package com.kevintweber.kimpachi.sgf;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SgfTokenTest {

    @Test
    void constructor() {
        SgfToken sgfToken = new SgfToken("asdf  ", "qwer  ");
        assertThat(sgfToken.getKey())
                .as("Checking key normalization.")
                .isEqualTo("ASDF");
        assertThat(sgfToken.getValue())
                .as("Checking value normalization.")
                .isEqualTo("qwer");

        SgfToken sgfToken2 = new SgfToken("B", "qwer");
        assertThat(sgfToken2.getKey())
                .as("Checking key normalization.")
                .isEqualTo("B");
        assertThat(sgfToken2.getValue())
                .as("Checking value normalization.")
                .isEqualTo("QWER");
    }
}