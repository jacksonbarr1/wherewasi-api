package com.wherewasi.wherewasiapi.service;

import com.wherewasi.wherewasiapi.dto.ShowMetadataDTO;

import java.util.List;

public interface TmdbService {
    List<ShowMetadataDTO> searchTvShows(String query);
}
