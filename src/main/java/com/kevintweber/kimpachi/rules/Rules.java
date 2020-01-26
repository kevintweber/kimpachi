package com.kevintweber.kimpachi.rules;

import com.kevintweber.kimpachi.board.Board;
import com.kevintweber.kimpachi.board.Move;
import lombok.NonNull;

public interface Rules {

    boolean isMoveValid(Move move, @NonNull Board board);

    RuleSet getRuleSet();

}
