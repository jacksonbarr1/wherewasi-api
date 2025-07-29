package com.wherewasi.wherewasiapi.service;

import com.wherewasi.wherewasiapi.client.TmdbApiClient;
import com.wherewasi.wherewasiapi.client.dto.TmdbGenre;
import com.wherewasi.wherewasiapi.client.dto.TmdbGenreListResponse;
import com.wherewasi.wherewasiapi.model.Show;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final TmdbApiClient tmdbApiClient;
    private final RedisTemplate<String, Object> redisTemplate;

    private final Map<Integer, Show.Genre> genreMap = new ConcurrentHashMap<>();

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(GenreServiceImpl.class);

    private static final String GENRE_MAP_REDIS_KEY = "wherewasi:genres:map";
    private static final int GENRE_MAP_CACHE_TTL_DAYS = 7;

    @PostConstruct
    public void init() {
        loadAndCacheGenres();
    }

    public Show.Genre getGenreById(Integer id) {
        if (genreMap.isEmpty()) {
            loadAndCacheGenres();
        }
        return genreMap.get(id);
    }

    private void loadAndCacheGenres() {
        logger.info("Attempting to load TMDB TV genres from Redis cache.");
        Map<Integer, Show.Genre> cachedGenreMap = (Map<Integer, Show.Genre>) redisTemplate.opsForValue().get(GENRE_MAP_REDIS_KEY);

        if (cachedGenreMap != null && !cachedGenreMap.isEmpty()) {
            genreMap.putAll(cachedGenreMap);
            logger.info("Loaded {} genres from Redis cache.", genreMap.size());
        } else {
            logger.info("Genre map not found in Redis cache, fetching from TMDB API.");
            Optional<TmdbGenreListResponse> responseOptional = tmdbApiClient.getTvGenreList();
            if (responseOptional.isPresent() && responseOptional.get().getGenres() != null) {
                genreMap.clear();
                TmdbGenreListResponse response = responseOptional.get();

                List<Show.Genre> mappedGenres = toShowGenreList(response.getGenres());
                mappedGenres.forEach(genre -> genreMap.put(genre.getId(), genre));
                logger.info("Fetched {} genres from TMDB API.", genreMap.size());
                redisTemplate.opsForValue().set(GENRE_MAP_REDIS_KEY, genreMap, GENRE_MAP_CACHE_TTL_DAYS, TimeUnit.DAYS);
                logger.info("Cached genre map in Redis for {} days.", GENRE_MAP_CACHE_TTL_DAYS);
            } else {
                logger.error("Failed to fetch genres from TMDB API or no genres found.");
            }
        }
    }

    private List<Show.Genre> toShowGenreList(List<TmdbGenre> tmdbGenres) {
        return tmdbGenres.stream()
                .map(tmdbGenre -> Show.Genre.builder()
                        .id(tmdbGenre.getId())
                        .name(tmdbGenre.getName())
                        .build())
                .toList();
    }


}
