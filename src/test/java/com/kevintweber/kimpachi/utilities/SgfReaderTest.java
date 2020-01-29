package com.kevintweber.kimpachi.utilities;

import com.kevintweber.kimpachi.game.Game;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class SgfReaderTest {

    @Test
    void readEmptySgf() {
        Path sgfEmpty = Path.of(getClass().getClassLoader().getResource("sgf/Simple/Empty.sgf").getPath());

        SgfReader sgfReader = new SgfReader();
        Game game = sgfReader.read(sgfEmpty);

        assertThat(game.getConfiguration().getBoardSize())
                .as("Checking default board size configuration")
                .isEqualTo(19);
        assertThat(game.getConfiguration().getPlayers().getBlackName())
                .as("Checking default black player name configuration")
                .isNull();
        assertThat(game.getTurns())
                .as("Checking there are no turns")
                .isEmpty();
    }

    @Test
    void readWithOnlyConfiguration() {
        Path sgfConfigurationOnly = Path.of(
                getClass().getClassLoader().getResource("sgf/Simple/ConfigurationOnly.sgf").getPath()
        );

        SgfReader sgfReader = new SgfReader();
        Game game = sgfReader.read(sgfConfigurationOnly);

        assertThat(game.getConfiguration().getBoardSize())
                .as("Checking board size configuration")
                .isEqualTo(19);
        assertThat(game.getConfiguration().getPlayers().getBlackName())
                .as("Checking default black player name configuration")
                .isEqualTo("Kevin Weber");
    }

    @Test
    void readWithOnlyConfigurationInOneLine() {
        Path sgfConfigurationOnly = Path.of(
                getClass().getClassLoader().getResource("sgf/Simple/ConfigurationOnlyOneLine.sgf").getPath()
        );

        SgfReader sgfReader = new SgfReader();
        Game game = sgfReader.read(sgfConfigurationOnly);

        assertThat(game.getConfiguration().getBoardSize())
                .as("Checking board size configuration")
                .isEqualTo(19);
        assertThat(game.getConfiguration().getPlayers().getBlackName())
                .as("Checking default black player name configuration")
                .isEqualTo("Kevin Weber");
    }

    @Test
    void readWithComplexExampleDosaku022() {
        Path sgfConfigurationOnly = Path.of(
                getClass().getClassLoader().getResource("sgf/Dosaku/022.sgf").getPath()
        );

        SgfReader sgfReader = new SgfReader();
        Game game = sgfReader.read(sgfConfigurationOnly);

        assertThat(game.getConfiguration().getBoardSize())
                .as("Checking board size configuration")
                .isEqualTo(19);
        assertThat(game.getConfiguration().getPlayers().getBlackName())
                .as("Checking default black player name configuration")
                .isEqualTo("Yasui Chitetsu");
        assertThat(game.getTurns())
                .as("Checking number of moves")
                .hasSize(185);
        game.toStdOut();
    }
}