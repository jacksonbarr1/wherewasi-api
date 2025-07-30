package com.wherewasi.wherewasiapi.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TmdbSearchResult {
    private Integer id;
    private String name;
    private String overview;
    @JsonProperty("genre_ids")
    private List<Integer> genreIds;
    @JsonProperty("first_air_date")
    private String firstAirDate;
    @JsonProperty("vote_average")
    private Float voteAverage;
    @JsonProperty("vote_count")
    private Integer voteCount;
    private Float popularity;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("origin_country")
    private List<String> originCountry;
    @JsonProperty("original_language")
    private String originLanguage;
}
