package com.kevintweber.kimpachi.utilities;

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
        this.value = value.trim();
    }
}
