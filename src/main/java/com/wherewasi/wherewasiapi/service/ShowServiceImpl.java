package com.wherewasi.wherewasiapi.service;

import com.wherewasi.wherewasiapi.dto.response.ShowMetadataDTO;
import com.wherewasi.wherewasiapi.model.Show;
import com.wherewasi.wherewasiapi.repository.ShowRepository;
import com.wherewasi.wherewasiapi.util.CacheConstants;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.wherewasi.wherewasiapi.util.RedisKeyGenerator.getTvShowSearchKey;


@Service
@AllArgsConstructor
public class ShowServiceImpl implements ShowService {
    private final TmdbService tmdbService;

    @Override
    public Show getShowById(Integer id) {
        return null;
    }

    @Override
    @Cacheable(value = CacheConstants.CACHE_NAME_SHOW_SEARCH, key = "#query")
    public List<ShowMetadataDTO> searchShows(String query) {
        return tmdbService.searchTvShows(query);
    }

    @Override
    public List<ShowMetadataDTO> getPopularShows() {
        return null;
    }
}
