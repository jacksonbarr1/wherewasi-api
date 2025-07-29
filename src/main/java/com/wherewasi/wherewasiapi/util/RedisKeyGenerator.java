package com.wherewasi.wherewasiapi.util;


import java.util.regex.Pattern;

import static com.wherewasi.wherewasiapi.util.QueryNormalizer.normalizeQuery;

public final class RedisKeyGenerator {

    private RedisKeyGenerator() {}

    private static final String APP_NAMESPACE = "wherewasi";
    private static final String DELIMITER = ":";


    public static String getTvShowSearchKey(String query) {
        // wherewasi:search_results:tv_shows:{normalized_query}
        String normalizedQuery = normalizeQuery(query);
        return APP_NAMESPACE + DELIMITER + "search_results" + DELIMITER + "tv_shows" + DELIMITER + normalizedQuery;
    }
}
