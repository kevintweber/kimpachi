package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.exception.ConfigurationException;

public final class Komi {

    private static Area komi9;
    private static Area komi13;
    private static Area komi19;

    public static Area getKomi(int boardSize) {
        if (!Board.isValidBoardSize(boardSize)) {
            throw new ConfigurationException("Invalid board size: " + boardSize);
        }

        if (boardSize == 9) {
            return getKomi9();
        }

        if (boardSize == 13) {
            return getKomi13();
        }

        return getKomi19();
    }

    private static Area getKomi9() {
        if (komi9 != null) {
            return komi9;
        }

        komi9 = new Area.Builder(9)
                .addPosition(Position.of(3, 3))
                .build();

        return komi9;
    }

    private static Area getKomi13() {
        if (komi13 != null) {
            return komi13;
        }

        komi13 = new Area.Builder(13)
                .addPosition(Position.of(4, 4))
                .addPosition(Position.of(9, 4))
                .addPosition(Position.of(7, 7))
                .addPosition(Position.of(4, 9))
                .addPosition(Position.of(9, 9))
                .build();

        return komi13;
    }

    private static Area getKomi19() {
        if (komi19 != null) {
            return komi19;
        }

        komi19 = new Area.Builder(19)
                .addPosition(Position.of(4, 4))
                .addPosition(Position.of(10, 4))
                .addPosition(Position.of(16, 4))
                .addPosition(Position.of(4, 10))
                .addPosition(Position.of(10, 10))
                .addPosition(Position.of(16, 10))
                .addPosition(Position.of(4, 16))
                .addPosition(Position.of(10, 16))
                .addPosition(Position.of(16, 16))
                .build();

        return komi19;
    }
}
