package com.kevintweber.kimpachi.rules;

import com.kevintweber.kimpachi.board.Board;
import com.kevintweber.kimpachi.game.PrisonerCount;
import com.kevintweber.kimpachi.game.Turn;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Japanese rules
 *
 * https://senseis.xmp.net/?JapaneseRules
 */
public final class JapaneseRules implements Rules {

    @Override
    public boolean isMoveValid(@NonNull Turn turn) {
        // TODO Suicide is forbidden.
        return true;
    }

    @Override
    public RuleSet getRuleSet() {
        return RuleSet.Japanese;
    }

    @Override
    public Optional<Score> getScore(
            @NonNull BigDecimal komi,
            @NonNull Board board,
            @NonNull PrisonerCount totalPrisoners) {
        return Optional.empty();
    }

    @Override
    public String toSgf() {
        return "RU[Japanese]";
    }
}
