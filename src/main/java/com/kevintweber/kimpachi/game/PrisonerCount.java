package com.kevintweber.kimpachi.game;

import lombok.Data;

@Data
public final class PrisonerCount {

    private final int blackPrisonerCount;
    private final int whitePrisonerCount;

    public PrisonerCount(int blackPrisonerCount, int whitePrisonerCount) {
        if (blackPrisonerCount < 0) {
            throw new IllegalArgumentException("Black prisoners must be greater than or equal to zero.");
        }

        if (whitePrisonerCount < 0) {
            throw new IllegalArgumentException("White prisoners must be greater than or equal to zero.");
        }

        this.blackPrisonerCount = blackPrisonerCount;
        this.whitePrisonerCount = whitePrisonerCount;
    }

    public PrisonerCount add(PrisonerCount previousPrisonerCount) {
        int newBlackPrisonerCount = blackPrisonerCount + previousPrisonerCount.getBlackPrisonerCount();
        int newWhitePrisonerCount = whitePrisonerCount + previousPrisonerCount.getWhitePrisonerCount();

        return new PrisonerCount(newBlackPrisonerCount, newWhitePrisonerCount);
    }
}
