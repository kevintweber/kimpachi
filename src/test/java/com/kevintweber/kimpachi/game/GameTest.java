package com.kevintweber.kimpachi.game;

import com.kevintweber.kimpachi.player.Rank;
import com.kevintweber.kimpachi.rules.RuleSet;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class GameTest {

    @Test
    void constructor() {
        Configuration configuration = new Configuration.Builder()
                .withHandicap(0)
                .withKomi(new BigDecimal("0.5"))
                .withRules(RuleSet.Japanese)
                .withBlackName("Black Name")
                .withBlackRank(Rank.dan4)
                .withWhiteName("White Name")
                .withWhiteRank(Rank.dan1)
                .withDate(LocalDate.of(2020, 2, 2))
                .build();

        Game game = Game.newGame(configuration);

        assertThat(game.getConfiguration())
                .isSameAs(configuration);
    }

}