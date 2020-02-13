package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.game.Configuration;
import lombok.NonNull;

import java.util.Set;

public final class Handicap {

    public static Area getHandicap(@NonNull Configuration configuration) {
        int handicap = configuration.getHandicap();
        if (handicap <= 1) {
            return Area.empty(Stone.Black);
        }

        Set<Point> handicapPoints;
        switch (handicap) {
            case 2:
            case 3:
            case 4:
                handicapPoints = getHandicapList2Thru4(handicap);
                break;

            case 5:
            case 6:
            case 7:
                handicapPoints = getHandicapList5Thru7(handicap);
                break;

            default:
                handicapPoints = getHandicapOver8(handicap);
        }

        return Area.of(Stone.Black, Groups.associate(handicapPoints));
    }

    private static Set<Point> getHandicapList2Thru4(int handicap) {
        return Set.of(
                Point.of(3, 3)
        );
    }

    private static Set<Point> getHandicapList5Thru7(int handicap) {
        return Set.of(
                Point.of(4, 4)
        );
    }

    private static Set<Point> getHandicapOver8(int handicap) {
        return Set.of(
                Point.of(4, 4)
        );
    }
}
