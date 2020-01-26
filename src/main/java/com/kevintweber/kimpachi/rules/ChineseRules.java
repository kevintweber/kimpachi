package com.kevintweber.kimpachi.rules;

import com.kevintweber.kimpachi.board.Board;
import com.kevintweber.kimpachi.board.Move;
import com.kevintweber.kimpachi.game.Configuration;
import lombok.NonNull;

public class ChineseRules extends AbstractRules {

    @Override
    public RuleSet getRuleSet() {
        return RuleSet.ChineseRules;
    }

    @Override
    void handleSpecializedRules(
            @NonNull Configuration configuration,
            @NonNull Board board,
            @NonNull Move move) {
        // Do nothing ... for now.
    }
}
