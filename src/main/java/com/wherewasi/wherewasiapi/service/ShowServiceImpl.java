package com.wherewasi.wherewasiapi.service;

import com.wherewasi.wherewasiapi.dto.response.ShowMetadataDTO;
import com.wherewasi.wherewasiapi.model.Show;
import com.wherewasi.wherewasiapi.repository.ShowRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class ShowServiceImpl implements ShowService {
    private final TmdbService tmdbService;
    private final ShowRepository showRepository;
    private final RedisTemplate<String, Object> redisTemplate;


    @Override
    public Show getShowById(Integer id) {
        return null;
    }

    @Override
    public List<ShowMetadataDTO> searchShows(String query) {
        return null;
    }

    @Override
    public List<ShowMetadataDTO> getPopularShows() {
        return null;
    }
}
