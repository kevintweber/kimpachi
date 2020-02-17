package com.kevintweber.kimpachi.board.influence.algorithm;

import com.kevintweber.kimpachi.board.Point;
import lombok.NonNull;

public interface InfluenceAlgorithm {

    int getInfluence(@NonNull Point source, @NonNull Point target);

}
