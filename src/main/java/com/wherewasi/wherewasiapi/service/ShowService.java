package com.wherewasi.wherewasiapi.service;

import com.wherewasi.wherewasiapi.dto.response.ShowMetadataDTO;
import com.wherewasi.wherewasiapi.model.Show;

import java.util.List;

public interface ShowService {
    Show getShowById(Integer id);

    List<ShowMetadataDTO> searchShows(String query);

    List<ShowMetadataDTO> getPopularShows();
}
