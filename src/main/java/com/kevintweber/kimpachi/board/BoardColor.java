package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.exception.ColorRequiredException;
import lombok.Data;
import lombok.NonNull;

import java.util.Comparator;

@Data
public final class BoardColor implements Comparable<BoardColor> {

    private final int boardSize;
    private final Color color;

    private BoardColor(
            int boardSize,
            @NonNull Color color) {
        Board.checkBoardSize(boardSize);
        this.boardSize = boardSize;

        if (color.equals(Color.Empty)) {
            throw new ColorRequiredException("Group color cannot be EMPTY.");
        }
        this.color = color;
    }

    public static BoardColor of(int boardSize, @NonNull Color color) {
        return new BoardColor(boardSize, color);
    }

    @Override
    public int compareTo(BoardColor o) {
        if (o == null) {
            return -1;
        }

        return Comparator.comparing(BoardColor::getBoardSize)
                .thenComparing(BoardColor::getColor)
                .compare(this, o);
    }
}
