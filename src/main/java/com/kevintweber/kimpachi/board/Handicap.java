package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.game.Configuration;
import lombok.NonNull;

import java.util.List;

public final class Handicap {

    public static Area getHandicap(@NonNull Configuration configuration) {
        int handicap = configuration.getHandicap();

        if (handicap <= 1) {
            return Area.empty();
        }

        List<Position> handicapPositions;
        switch (handicap) {
            case 2:
            case 3:
            case 4:
                handicapPositions = getHandicapList2Thru4();
                break;

            case 5:
            case 6:
            case 7:
                handicapPositions = getHandicapList5Thru7();
                break;

            default:
                handicapPositions = getHandicapOver8();
        }

        Area handicapArea = Area.empty();
        for (int i = 0; i < handicap; i++) {
            if (i >= handicapPositions.size()) {
                break;
            }

            handicapArea = handicapArea.with(handicapPositions.get(i));
        }

        return handicapArea;
    }

    private static List<Position> getHandicapList2Thru4() {
        return List.of(
                Position.of(3, 3)
        );
    }

    private static List<Position> getHandicapList5Thru7() {
        return List.of(
                Position.of(4, 4)
        );
    }

    private static List<Position> getHandicapOver8() {
        return List.of(
                Position.of(4, 4)
        );
    }
}
