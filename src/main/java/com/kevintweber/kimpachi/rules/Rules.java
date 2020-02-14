package com.kevintweber.kimpachi.rules;

import com.kevintweber.kimpachi.board.Board;
import com.kevintweber.kimpachi.game.PrisonerCount;
import com.kevintweber.kimpachi.game.Turn;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Optional;

public interface Rules {

    boolean isMoveValid(@NonNull Turn turn);

    RuleSet getRuleSet();

    Optional<Score> getScore(
            @NonNull BigDecimal komi,
            @NonNull Board board,
            @NonNull PrisonerCount totalPrisoners);

    String toSgf();

}
