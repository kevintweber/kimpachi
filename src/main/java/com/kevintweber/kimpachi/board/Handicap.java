package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.game.Configuration;
import lombok.NonNull;

import java.util.List;

public final class Handicap {

    public static Area getHandicap(@NonNull Configuration configuration) {
        int boardSize = configuration.getBoardSize();
        int handicap = configuration.getHandicap();

        if (handicap <= 1) {
            return Area.empty(boardSize);
        }

        List<Position> handicapPositions;
        switch (boardSize) {
            case 9:
                handicapPositions = getHandicapList9();
                break;

            case  13:
                handicapPositions = getHandicapList13();
                break;

            default:
                handicapPositions = getHandicapList19();
        }

        Area handicapArea = Area.empty(boardSize);
        for (int i = 0; i < handicap; i++) {
            if (i >= handicapPositions.size()) {
                break;
            }

            handicapArea = handicapArea.with(handicapPositions.get(i));
        }

        return handicapArea;
    }

    private static List<Position> getHandicapList9() {
        return List.of(
                Position.of(3, 3)
        );
    }

    private static List<Position> getHandicapList13() {
        return List.of(
                Position.of(4, 4)
        );
    }

    private static List<Position> getHandicapList19() {
        return List.of(
                Position.of(4, 4)
        );
    }
}
