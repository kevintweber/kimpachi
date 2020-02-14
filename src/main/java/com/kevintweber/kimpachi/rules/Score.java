package com.kevintweber.kimpachi.rules;

import com.kevintweber.kimpachi.board.Stone;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class Score {

    private final BigDecimal score;
    private final Stone stone;

    public Score(
            @NonNull BigDecimal score,
            @NonNull Stone stone) {
        this.score = score.setScale(1, RoundingMode.HALF_UP);
        this.stone = stone;
    }
}
