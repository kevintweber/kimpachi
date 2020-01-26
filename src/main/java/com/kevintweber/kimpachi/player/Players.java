package com.kevintweber.kimpachi.player;

import lombok.Data;
import lombok.NonNull;

@Data
public final class Players {

    private final String blackName;
    private final Rank blackRank;
    private final String whiteName;
    private final Rank whiteRank;

    private Players(
            String blackName,
            Rank blackRank,
            String whiteName,
            Rank whiteRank) {
        this.blackName = blackName;
        this.blackRank = blackRank;
        this.whiteName = whiteName;
        this.whiteRank = whiteRank;
    }

    public String toSgf() {
        String result = "";
        if (blackName != null || blackRank != null) {
            if (blackName != null) {
                result += "PB[" + blackName + "]";
            }

            if (blackName != null) {
                result += "BR[" + blackRank.toString() + "]";
            }

            result += "\n";
        }

        if (whiteName != null || whiteRank != null) {
            if (whiteName != null) {
                result += "PW[" + whiteName + "]";
            }

            if (whiteName != null) {
                result += "WR[" + whiteRank.toString() + "]";
            }

            result += "\n";
        }

        return result;
    }

    public static class Builder {

        private String builderBlackName;
        private Rank builderBlackRank;
        private String builderWhiteName;
        private Rank builderWhiteRank;

        public Builder withBlackName(@NonNull String blackName) {
            builderBlackName = blackName.trim();

            return this;
        }

        public Builder withBlackRank(@NonNull Rank blackRank) {
            builderBlackRank = blackRank;

            return this;
        }

        public Builder withWhiteName(@NonNull String whiteName) {
            builderWhiteName = whiteName.trim();

            return this;
        }

        public Builder withWhiteRank(@NonNull Rank whiteRank) {
            builderWhiteRank = whiteRank;

            return this;
        }

        public Players build() {
            return new Players(
                    builderBlackName,
                    builderBlackRank,
                    builderWhiteName,
                    builderWhiteRank
            );
        }
    }
}
