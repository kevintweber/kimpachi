package com.kevintweber.kimpachi.rules;

import com.kevintweber.kimpachi.board.Board;
import com.kevintweber.kimpachi.board.Move;
import lombok.NonNull;

public final class ChineseRules implements Rules {

    @Override
    public boolean isMoveValid(
            Move move,
            @NonNull Board board) {
        return true;
    }

    @Override
    public RuleSet getRuleSet() {
        return RuleSet.ChineseRules;
    }
}
