package com.wherewasi.wherewasiapi.service;

import com.wherewasi.wherewasiapi.dto.response.ShowMetadataDTO;
import com.wherewasi.wherewasiapi.model.Show;

import java.util.List;
import java.util.Optional;

public interface TmdbService {
    List<ShowMetadataDTO> searchTvShows(String query);

    boolean shouldRefetchShow(String id);

    Optional<Show> getShowById(String id);
}
