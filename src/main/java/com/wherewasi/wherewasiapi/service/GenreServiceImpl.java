package com.wherewasi.wherewasiapi.service;

import com.wherewasi.wherewasiapi.client.TmdbApiClient;
import com.wherewasi.wherewasiapi.client.dto.TmdbGenre;
import com.wherewasi.wherewasiapi.client.dto.TmdbGenreListResponse;
import com.wherewasi.wherewasiapi.model.Show;
import com.wherewasi.wherewasiapi.util.CacheConstants;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final TmdbApiClient tmdbApiClient;
    private ApplicationContext applicationContext;

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(GenreServiceImpl.class);

    public Show.Genre getGenreById(Integer id) {
        return applicationContext.getBean(GenreServiceImpl.class).getAndCacheAllGenres().get(String.valueOf(id));
    }

    @Cacheable(value = CacheConstants.CACHE_NAME_GENRES, key = "'allGenres'")
    public Map<String, Show.Genre> getAndCacheAllGenres() {
        logger.info("Fetching all TMDB TV genres from TMDB API.");
        Optional<TmdbGenreListResponse> responseOptional = tmdbApiClient.getTvGenreList();

        if (responseOptional.isPresent() && responseOptional.get().getGenres() != null) {
            Map<String, Show.Genre> genres = toShowGenreMap(responseOptional.get().getGenres());
            logger.info("Fetched and caching {} genres from TMDB API.", genres.size());
            return genres;
        } else {
            logger.error("Failed to fetch genres from TMDB API or no genres found.");
            return Collections.emptyMap();
        }
    }

    private Map<String, Show.Genre> toShowGenreMap(List<TmdbGenre> tmdbGenres) {
        return tmdbGenres.stream()
                .map(tmdbGenre -> Show.Genre.builder()
                        .id(tmdbGenre.getId())
                        .name(tmdbGenre.getName())
                        .build()
                )
                .collect(Collectors.toConcurrentMap(genre -> String.valueOf(genre.getId()), genre -> genre));
    }


}
