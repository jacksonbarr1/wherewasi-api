package com.wherewasi.wherewasiapi.client.dto;

import lombok.Data;

import java.util.List;

@Data
public class TmdbGenreListResponse {
    private List<TmdbGenre> genres;
}
