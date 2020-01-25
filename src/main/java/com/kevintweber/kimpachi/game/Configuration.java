package com.kevintweber.kimpachi.game;

import com.kevintweber.kimpachi.exception.ConfigurationException;
import lombok.Data;

@Data
public final class Configuration {

    private final int boardSize;

    private Configuration(int boardSize) {
        this.boardSize = boardSize;
    }

    public static class Builder {

        private int builderBoardSize = 19;

        public Builder() {
        }

        public void withBoardSize(int boardSize) {
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
        }

        public Configuration build() {
            return new Configuration(builderBoardSize);
        }
    }
}
