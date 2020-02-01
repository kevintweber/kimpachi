package com.kevintweber.kimpachi.board;

import lombok.NonNull;

public enum Color {

    Empty,
    Black,
    White;

    public static Color of(@NonNull Stone stone) {
        if (stone.equals(Stone.Black)) {
            return Black;
        }

        return White;
    }
}
