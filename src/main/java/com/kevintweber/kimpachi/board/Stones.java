package com.kevintweber.kimpachi.board;

import lombok.NonNull;

import java.util.Set;

public interface Stones {

    Color getColor();

    boolean contains(@NonNull Position position);

    int count();
    
    int getBoardSize();

    Set<Position> getPositions();

    boolean isEmpty();

}
