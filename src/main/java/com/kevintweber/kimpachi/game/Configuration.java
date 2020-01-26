package com.kevintweber.kimpachi.game;

import com.kevintweber.kimpachi.exception.ConfigurationException;
import com.kevintweber.kimpachi.player.Players;
import com.kevintweber.kimpachi.player.Rank;
import com.kevintweber.kimpachi.rules.ChineseRules;
import com.kevintweber.kimpachi.rules.JapaneseRules;
import com.kevintweber.kimpachi.rules.RuleSet;
import com.kevintweber.kimpachi.rules.Rules;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public final class Configuration {

    private final int boardSize;
    private final int handicap;
    private final BigDecimal komi;
    private final Rules rules;
    private final Players players;
    private final LocalDate date;

    private Configuration(
            int boardSize,
            int handicap,
            @NonNull BigDecimal komi,
            @NonNull Rules rules,
            @NonNull Players players,
            @NonNull LocalDate date) {
        this.boardSize = boardSize;
        this.handicap = handicap;
        this.komi = komi;
        this.rules = rules;
        this.players = players;
        this.date = LocalDate.from(date);
    }

    public String toSgf() {
        StringBuilder sb = new StringBuilder(
                "(;FF[4]GM[1]SZ[" + boardSize + "]AP[Kimpachi:" +
                        this.getClass().getPackage().getImplementationVersion() + "]\n\n"
        );

        sb.append(players.toSgf());
        sb.append("KM[" + komi.toPlainString() + "]HA[" + handicap + "]\n");
        sb.append("DT[" + date.format(DateTimeFormatter.ISO_LOCAL_DATE) + "]\n");
        sb.append(rules.toSgf());
        sb.append("\n");

        return sb.toString();
    }

    public static class Builder {

        private int builderBoardSize = 19;
        private int builderHandicap = 0;
        private BigDecimal builderKomi = new BigDecimal("6.5");
        private Rules builderRules = new JapaneseRules();
        private Players.Builder playersBuilder = new Players.Builder();
        private LocalDate builderDate = LocalDate.now();

        public Builder() {
        }

        public Builder withBoardSize(int boardSize) {
            switch (boardSize) {
                case 9:
                case 13:
                case 19:
                    // Allowed so do nothing.
                    break;

                default:
                    throw new ConfigurationException("Illegal board size: " + boardSize);
            }

            this.builderBoardSize = boardSize;

            return this;
        }

        public Builder withHandicap(int handicap) {
            if (handicap < 0 || handicap > 9) {
                throw new ConfigurationException("Illegal handicap: " + handicap);
            }

            this.builderHandicap = handicap;

            return this;
        }

        public Builder withKomi(@NonNull BigDecimal komi) {
            this.builderKomi = komi;

            return this;
        }

        public Builder withRules(@NonNull RuleSet ruleSet) {
            if (ruleSet.equals(RuleSet.JapaneseRules)) {
                this.builderRules = new JapaneseRules();

                return this;
            }

            this.builderRules = new ChineseRules();

            return this;
        }

        public Builder withBlackName(@NonNull String blackName) {
            playersBuilder.withBlackName(blackName);

            return this;
        }

        public Builder withBlackRank(@NonNull Rank blackRank) {
            playersBuilder.withBlackRank(blackRank);

            return this;
        }

        public Builder withWhiteName(@NonNull String whiteName) {
            playersBuilder.withWhiteName(whiteName);

            return this;
        }

        public Builder withWhiteRank(@NonNull Rank whiteRank) {
            playersBuilder.withWhiteRank(whiteRank);

            return this;
        }

        public Builder withDate(@NonNull LocalDate date) {
            builderDate = LocalDate.from(date);

            return this;
        }

        public Configuration build() {
            return new Configuration(
                    builderBoardSize,
                    builderHandicap,
                    builderKomi,
                    builderRules,
                    playersBuilder.build(),
                    builderDate
            );
        }
    }
}
