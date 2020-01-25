package com.kevintweber.kimpachi.game;

import com.kevintweber.kimpachi.exception.ConfigurationException;
import lombok.Data;
import lombok.NonNull;

@Data
public final class Configuration {

    private final int boardSize;
    private final String blackPlayer;
    private final String whitePlayer;

    private Configuration(
            int boardSize,
            @NonNull String blackPlayer,
            @NonNull String whitePlayer) {
        this.boardSize = boardSize;
        this.blackPlayer = blackPlayer;
        this.whitePlayer = whitePlayer;
    }

    public static class Builder {

        private int builderBoardSize = 19;
        private String builderBlackPlayer = "<Black>";
        private String builderWhitePlayer = "<White>";

        public Builder() {
        }

        public Builder withBoardSize(int boardSize) {
            switch (boardSize) {
                case 9:
                case 15:
                case 19:
                    // Allowed so do nothing.
                    break;

                default:
                    throw new ConfigurationException("Illegal board size: " + boardSize);
            }

            this.builderBoardSize = boardSize;

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
                    builderBlackPlayer,
                    builderWhitePlayer
            );
        }
    }
}
