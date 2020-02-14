package com.kevintweber.kimpachi.rules;

import com.kevintweber.kimpachi.board.Board;
import com.kevintweber.kimpachi.game.PrisonerCount;
import com.kevintweber.kimpachi.game.Turn;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Chinese rules
 *
 * https://senseis.xmp.net/?ChineseRules
 */
public final class ChineseRules implements Rules {

    @Override
    public boolean isMoveValid(@NonNull Turn turn) {
        // TODO Suicide is forbidden.
        return true;
    }

    @Override
    public RuleSet getRuleSet() {
        return RuleSet.Chinese;
    }

    /**
     * Chinese rules use area scoring
     *
     * In area scoring, your score is:
     * the number of empty points which only your stones surround
     * plus the number of your stones on the board
     */
    @Override
    public Optional<Score> getScore(
            @NonNull BigDecimal komi,
            @NonNull Board board,
            @NonNull PrisonerCount totalPrisoners) {
        return Optional.empty();
    }

    @Override
    public String toSgf() {
        return "RU[Chinese]";
    }
}
