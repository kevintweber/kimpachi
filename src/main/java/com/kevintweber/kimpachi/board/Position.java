package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.exception.InvalidPositionException;
import com.kevintweber.kimpachi.exception.SgfException;
import lombok.Data;
import lombok.NonNull;

import java.util.Comparator;

@Data
public final class Position implements Comparable<Position> {

    public final static String sgfCharacters = "abcdefghijklmnopqrstuvwxyz";

    private final int x;
    private final int y;

    private Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Position of(int x, int y) {
        if (x <= 0 || y <= 0) {
            throw new InvalidPositionException("Invalid position: x=" + x + ";y=" + y);
        }

        return new Position(x, y);
    }

    public static Position fromSgf(@NonNull String sgfPosition) {
        if (sgfPosition.length() != 2) {
            throw new SgfException("Invalid SGF position string length. Value=" + sgfPosition);
        }

        String[] characters = sgfPosition.split("");
        int x = sgfCharacters.indexOf(characters[0]) + 1;
        int y = sgfCharacters.indexOf(characters[1]) + 1;

        return of(x, y);
    }

    public boolean isAdjacent(@NonNull Position otherPosition) {
        int otherX = otherPosition.getX();
        int otherY = otherPosition.getY();
        if (x == otherX && Math.abs(y - otherY) == 1) {
            return true;
        }

        return y == otherY && Math.abs(x - otherX) == 1;
    }

    @Override
    public int compareTo(Position o) {
        if (o == null) {
            return -1;
        }

        return Comparator.comparing(Position::getX)
                .thenComparing(Position::getY)
                .compare(this, o);
    }

    @Override
    public String toString() {
        return sgfCharacters.substring(x - 1, x) + sgfCharacters.substring(y - 1, y);
    }
}
