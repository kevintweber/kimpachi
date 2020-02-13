package com.kevintweber.kimpachi.sgf;

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

        assertThat(game.getConfiguration().getPlayers().getBlackName())
                .as("Checking default black player name configuration")
                .isEqualTo("Yasui Chitetsu");
        assertThat(game.getTurns())
                .as("Checking number of moves")
                .hasSize(185);
        assertThat(game.printBoard())
                .as("Checking board is in correct state.")
                .isEqualTo("    A B C D E F G H J K L M N O P Q R S T \n" +
                        "19  . . . . . . . . . . . . . . . . . . . \n" +
                        "18  . O O X X O . O . O X . X X O . . . . \n" +
                        "17  . X O X O O X X O . X . X O O O O . . \n" +
                        "16  X . X X O X . . O + . . X O X X . . . \n" +
                        "15  . X . X O O . O O X . X . X O . X . . \n" +
                        "14  . . O . . . X . . O X . X . O . . . . \n" +
                        "13  . . . . O O X . X X . . . . . . O . . \n" +
                        "12  . . O . O X X . X . X O . . . O . O . \n" +
                        "11  . X . X X . . O O X X O . . . O O X . \n" +
                        "10  . X . + X O . . . O O . . X . O X X . \n" +
                        "9   . O O O X . . . . . . O X . X O X O . \n" +
                        "8   . O X O X O O O . . O X O X X O O X . \n" +
                        "7   . X X X X O X X O . O . . X . O X X . \n" +
                        "6   . . . . O X . X . O . . X . . O X . . \n" +
                        "5   . X X X O . O . X . X X . . . . O X . \n" +
                        "4   . O . O X X X X . + X O O O . O O X . \n" +
                        "3   O X . O X . . . X O O X O . X O X X . \n" +
                        "2   . O . O X O . . X . O X O . . O O X . \n" +
                        "1   . . . . O . . . . . . . . . . . . . . \n");
    }
}