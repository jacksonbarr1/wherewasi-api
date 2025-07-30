package com.wherewasi.wherewasiapi.service;

import com.wherewasi.wherewasiapi.model.Show;

public interface GenreService {
    Show.Genre getGenreById(Integer id);
}
