package com.kevintweber.kimpachi.board;

import lombok.NonNull;

public final class AugmentedPointSets<T> {

    public static <T> String print(@NonNull AugmentedPointSet<T> augmentedPointSet, T defaultValue) {
        StringBuilder sb = new StringBuilder("    ");
        for (int i = 0; i < 19; i++) {
            sb.append(Board.positionCharacters.charAt(i));
            sb.append("    ");
        }

        sb.append("\n");

        for (int y = 19; y >= 1; y--) {
            sb.append(y);
            sb.append("  ");
            if (y < 10) {
                sb.append(" ");
            }

            for (int x = 1; x <= 19; x++) {
                Point point = Point.of(x, y);
                if (augmentedPointSet.contains(point)) {
                    String value = String.valueOf(augmentedPointSet.get(point, defaultValue));
                    sb.append(value);
                    sb.append(" ".repeat(5 - value.length()));
                } else {
                    sb.append("-    ");
                }
            }

            sb.append("\n");
        }

        return sb.toString();
    }
}
