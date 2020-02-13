package com.kevintweber.kimpachi.sgf;

import lombok.Data;
import lombok.NonNull;

@Data
public final class SgfToken {

    private final String key;
    private final String value;

    public SgfToken(
            @NonNull String key,
            @NonNull String value) {
        this.key = key.toUpperCase().trim();
        if (this.key.equals("B") || this.key.equals("W")) {
            this.value = value.trim().toUpperCase();
        } else {
            this.value = value.trim();
        }
    }
}
