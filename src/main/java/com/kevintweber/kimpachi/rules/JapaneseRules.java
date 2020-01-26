package com.kevintweber.kimpachi.rules;

import com.kevintweber.kimpachi.board.Board;
import com.kevintweber.kimpachi.board.Move;
import lombok.NonNull;

public final class JapaneseRules implements Rules {

    @Override
    public boolean isMoveValid(
            @NonNull Move move,
            @NonNull Board board) {
        return true;
    }

    @Override
    public RuleSet getRuleSet() {
        return RuleSet.JapaneseRules;
    }


    @Override
    public String toSgf() {
        return "RU[Japanese]\n";
    }
}
