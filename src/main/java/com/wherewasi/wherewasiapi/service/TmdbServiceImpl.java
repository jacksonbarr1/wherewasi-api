package com.wherewasi.wherewasiapi.service;

import com.wherewasi.wherewasiapi.client.TmdbApiClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TmdbServiceImpl implements TmdbService {
    private final TmdbApiClient tmdbApiClient;
}
