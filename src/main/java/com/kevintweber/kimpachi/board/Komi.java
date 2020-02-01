package com.kevintweber.kimpachi.board;

public final class Komi {

    private static Area KOMI;

    public static Area getKomi() {
        if (KOMI != null) {
            return KOMI;
        }

        KOMI = new Area.Builder()
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

        return KOMI;
    }
}
