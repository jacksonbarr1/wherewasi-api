package com.wherewasi.wherewasiapi.service;

import com.wherewasi.wherewasiapi.dto.response.ShowDetailsDTO;
import com.wherewasi.wherewasiapi.dto.response.ShowMetadataDTO;
import com.wherewasi.wherewasiapi.mapper.ShowMapper;
import com.wherewasi.wherewasiapi.model.Show;
import com.wherewasi.wherewasiapi.repository.ShowRepository;
import com.wherewasi.wherewasiapi.util.CacheConstants;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class ShowServiceImpl implements ShowService {
    private final ShowRepository showRepository;
    private final TmdbService tmdbService;
    private final ShowMapper showMapper;

    @Override
    @Cacheable(value = CacheConstants.CACHE_NAME_SHOW_DETAILS, key = "#id")
    public ShowDetailsDTO getShowDetailsById(String id) {
        Show show = getShowFromDbOrApi(id);
        return showMapper.showToShowDetailsDTO(show);
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

    private Show getShowFromDbOrApi(String id) {
        if (showRepository.existsById(id) && !tmdbService.shouldRefetchShow(id)) {
            return showRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Show not found in database: " + id));
        }
        return tmdbService.getShowById(id);
    }
}
