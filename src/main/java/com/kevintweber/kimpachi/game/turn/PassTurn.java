package com.kevintweber.kimpachi.game.turn;

import com.kevintweber.kimpachi.board.Color;
import com.kevintweber.kimpachi.board.Position;
import com.kevintweber.kimpachi.game.Prisoners;

public class PassTurn implements Turn {

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public Prisoners getPrisoners() {
        return Prisoners.empty();
    }

    @Override
    public Position getPosition() {
        return null;
    }

    @Override
    public boolean isPass() {
        return true;
    }
    
}
