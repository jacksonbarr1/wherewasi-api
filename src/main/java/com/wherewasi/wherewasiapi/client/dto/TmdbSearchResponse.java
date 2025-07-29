package com.wherewasi.wherewasiapi.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TmdbSearchResponse {
    int page;
    @JsonProperty("total_pages")
    int totalPages;
    @JsonProperty("total_results")
    int totalResults;
    List<TmdbSearchResult> results;
}
