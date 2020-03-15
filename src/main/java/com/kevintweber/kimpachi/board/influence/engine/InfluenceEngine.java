package com.kevintweber.kimpachi.board.influence.engine;

import com.kevintweber.kimpachi.board.Board;
import com.kevintweber.kimpachi.board.Point;
import com.kevintweber.kimpachi.board.Stone;
import lombok.NonNull;

import java.util.Map;

public interface InfluenceEngine {

    Map<Point, Integer> calculate(@NonNull Stone stone, @NonNull Board board);

}
