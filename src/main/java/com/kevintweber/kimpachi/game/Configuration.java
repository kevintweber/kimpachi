package com.kevintweber.kimpachi.game;

import com.kevintweber.kimpachi.exception.ConfigurationException;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
public final class Configuration {

    private final int boardSize;
    private final int handicap;
    private final BigDecimal komi;
    private final String blackPlayer;
    private final String whitePlayer;

    private Configuration(
            int boardSize,
            int handicap,
            @NonNull BigDecimal komi,
            @NonNull String blackPlayer,
            @NonNull String whitePlayer) {
        this.boardSize = boardSize;
        this.handicap = handicap;
        this.komi = komi;
        this.blackPlayer = blackPlayer;
        this.whitePlayer = whitePlayer;
    }

    public static class Builder {

        private int builderBoardSize = 19;
        private int builderHandicap = 0;
        private BigDecimal builderKomi = new BigDecimal("6.5");
        private String builderBlackPlayer = "<Black>";
        private String builderWhitePlayer = "<White>";

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
            if (handicap < 0) {
                throw new ConfigurationException("Illegal handicap: " + handicap);
            }

            this.builderHandicap = handicap;

            return this;
        }

        public Builder withKomi(@NonNull BigDecimal komi) {
            this.builderKomi = komi;

            return this;
        }

        public Builder withBlackPlayer(@NonNull String blackPlayerName) {
            builderBlackPlayer = blackPlayerName.trim();

            return this;
        }

        public Builder withWhitePlayer(@NonNull String whitePlayerName) {
            builderWhitePlayer = whitePlayerName.trim();

            return this;
        }

        public Configuration build() {
            return new Configuration(
                    builderBoardSize,
                    builderHandicap,
                    builderKomi,
                    builderBlackPlayer,
                    builderWhitePlayer
            );
        }
    }
}
