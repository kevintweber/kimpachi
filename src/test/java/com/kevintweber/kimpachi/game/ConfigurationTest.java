package com.kevintweber.kimpachi.game;

import com.kevintweber.kimpachi.player.Rank;
import com.kevintweber.kimpachi.rules.RuleSet;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigurationTest {

    @Test
    void toSgf() {
        Configuration configuration = new Configuration.Builder()
                .withBoardSize(19)
                .withHandicap(0)
                .withKomi(new BigDecimal("0.5"))
                .withRules(RuleSet.JapaneseRules)
                .withBlackName("Black Name")
                .withBlackRank(Rank.dan4)
                .withWhiteName("White Name")
                .withWhiteRank(Rank.dan1)
                .withDate(LocalDate.of(2020, 2, 2))
                .build();

        assertThat(configuration.toSgf())
                .as("Checking SGF output")
                .isEqualTo("(;FF[4]GM[1]SZ[19]AP[Kimpachi:null]\n\n" +
                        "PB[Black Name]BR[dan4]\n" +
                        "PW[White Name]WR[dan1]\n" +
                        "KM[0.5]HA[0]\n" +
                        "DT[2020-02-02]\n" +
                        "RU[Japanese]\n\n");
    }
}