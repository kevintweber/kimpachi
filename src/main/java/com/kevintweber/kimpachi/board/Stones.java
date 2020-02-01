package com.kevintweber.kimpachi.board;

import lombok.NonNull;

import java.util.Set;

public interface Stones {

    Stone getStone();

    boolean contains(@NonNull Position position);

    int count();
    
    Set<Position> getPositions();

    boolean isEmpty();

}
