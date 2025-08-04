package com.wherewasi.wherewasiapi.service;

import com.wherewasi.wherewasiapi.dto.response.ShowDetailsDTO;
import com.wherewasi.wherewasiapi.dto.response.ShowMetadataDTO;

import java.util.List;

public interface ShowService {
    ShowDetailsDTO getShowDetailsById(String id);

    List<ShowMetadataDTO> searchShows(String query);

    List<ShowMetadataDTO> getPopularShows();
}
