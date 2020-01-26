package com.kevintweber.kimpachi.game.turn;

import com.kevintweber.kimpachi.board.Color;
import com.kevintweber.kimpachi.board.Position;
import com.kevintweber.kimpachi.game.Prisoners;

public interface Turn {

    Color getColor();

    Prisoners getPrisoners();

    Position getPosition();

    boolean isPass();

}
