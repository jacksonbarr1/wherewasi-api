package com.wherewasi.wherewasiapi.util;

import java.util.regex.Pattern;

public final class QueryNormalizer {

    private QueryNormalizer() {}

    private static final Pattern MULTIPLE_SPACES_PATTERN = Pattern.compile("\\s+");

    public static String normalizeQuery(String query) {
        if (query == null) {
            return "";
        }

        String trimmedAndLowercased = query.trim().toLowerCase();
        return MULTIPLE_SPACES_PATTERN.matcher(trimmedAndLowercased).replaceAll(" ");
    }
}
