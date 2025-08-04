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
import java.util.Optional;


@Service
@AllArgsConstructor
public class ShowServiceImpl implements ShowService {
    private final ShowRepository showRepository;
    private final TmdbService tmdbService;
    private final ShowMapper showMapper;

    @Override
    @Cacheable(value = CacheConstants.CACHE_NAME_SHOW_DETAILS, key = "#id")
    public ShowDetailsDTO getShowDetailsById(String id) {
        Optional<Show> showOptional = showRepository.findById(id);
        Show show;

        if (showOptional.isPresent() && !shouldRefetchShow(showOptional.get())) {
            // Show is already in Mongo and does not need refetching
            show = showOptional.get();
        } else {
            show = tmdbService.getShowById(id);
        }

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

    private boolean shouldRefetchShow(Show show) {
        return true;
    }
}
