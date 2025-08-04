package com.wherewasi.wherewasiapi.service;

import com.wherewasi.wherewasiapi.dto.response.ShowMetadataDTO;
import com.wherewasi.wherewasiapi.model.Show;

import java.util.List;

public interface TmdbService {
    List<ShowMetadataDTO> searchTvShows(String query);
    Show getShowById(String id);
}
