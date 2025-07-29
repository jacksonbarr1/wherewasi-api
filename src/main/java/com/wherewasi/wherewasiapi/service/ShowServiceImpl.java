package com.wherewasi.wherewasiapi.service;

import com.wherewasi.wherewasiapi.dto.ShowMetadataDTO;
import com.wherewasi.wherewasiapi.model.Show;
import com.wherewasi.wherewasiapi.repository.ShowRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.wherewasi.wherewasiapi.util.RedisKeyGenerator.getTvShowSearchKey;


@Service
@AllArgsConstructor
public class ShowServiceImpl implements ShowService {
    private final TmdbService tmdbService;
    private final ShowRepository showRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ShowServiceImpl.class);

    private static final long SEARCH_RESULTS_CACHE_TTL_MINUTES = 60;


    @Override
    public Show getShowById(Integer id) {
        return null;
    }

    @Override
    public List<ShowMetadataDTO> searchShows(String query) {
        String cacheKey = getTvShowSearchKey(query);

        // Check Redis for cached query
        List<ShowMetadataDTO> cachedResults = (List<ShowMetadataDTO>) redisTemplate.opsForValue().get(cacheKey);
        if (cachedResults != null) {
            logger.info("Cache hit for search query: {}", query);
            return cachedResults;
        }

        logger.info("Cache miss for search query: {}", query);

        // Retrieve search results from TMDB API
        List<ShowMetadataDTO> results = tmdbService.searchTvShows(query);

        // Cache results
        if (results != null && !results.isEmpty()) {
            redisTemplate.opsForValue().set(cacheKey, results, SEARCH_RESULTS_CACHE_TTL_MINUTES, TimeUnit.MINUTES);
        }

        return results;
    }

    @Override
    public List<ShowMetadataDTO> getPopularShows() {
        return null;
    }
}
