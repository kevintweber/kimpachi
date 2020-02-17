package com.kevintweber.kimpachi.board.influence.algorithm;

import com.kevintweber.kimpachi.board.Point;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.math.MathContext;

@Data
public final class Linear implements InfluenceAlgorithm {

    private final BigDecimal factor;

    public Linear(@NonNull BigDecimal factor) {
        if (factor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Linear algorithm factor must not be less than or equal to zero: " + factor.toPlainString());
        }

        if (factor.compareTo(BigDecimal.ONE) >= 0) {
            throw new IllegalArgumentException("Linear algorithm factor must not be greater than or equal to one: " + factor.toPlainString());
        }

        this.factor = factor;
    }

    @Override
    public int getInfluence(
            @NonNull Point source,
            @NonNull Point target) {
        int xDistance = Math.abs(source.getX() - target.getX());
        int yDistance = Math.abs(source.getY() - target.getY());
        if (xDistance == 0 && yDistance == 0) {
            return 100;
        }

        // distance = sqrt(x^2 + y^2)
        BigDecimal xSquared = new BigDecimal(xDistance).pow(2);
        BigDecimal ySquared = new BigDecimal(yDistance).pow(2);
        BigDecimal distance = xSquared.add(ySquared).sqrt(MathContext.DECIMAL32);

        return (int) (Math.pow(factor.doubleValue(), distance.doubleValue()) * 100.0);
    }
}
