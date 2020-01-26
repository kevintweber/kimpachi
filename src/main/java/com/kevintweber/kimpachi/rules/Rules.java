package com.kevintweber.kimpachi.rules;

import com.kevintweber.kimpachi.board.Board;
import com.kevintweber.kimpachi.board.Move;
import com.kevintweber.kimpachi.game.Configuration;
import com.kevintweber.kimpachi.game.turn.Turn;
import lombok.NonNull;
import org.springframework.lang.Nullable;

import java.util.Set;

public interface Rules {

    Turn move(
            @NonNull Configuration configuration,
            @NonNull Set<Integer> boardHashes,
            @NonNull Board board,
            @Nullable Move move
    );

    RuleSet getRuleSet();

}
