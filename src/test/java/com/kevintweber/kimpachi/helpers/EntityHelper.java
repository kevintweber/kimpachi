package com.kevintweber.kimpachi.helpers;

import com.kevintweber.kimpachi.game.Configuration;

public class EntityHelper {

    public static Configuration buildConfiguration() {
        return buildConfiguration(19);
    }

    public static Configuration buildConfiguration(int boardSize) {
        return new Configuration.Builder()
                .withBoardSize(boardSize)
                .build();
    }
}
