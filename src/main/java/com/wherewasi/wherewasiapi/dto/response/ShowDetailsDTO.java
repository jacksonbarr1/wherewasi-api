package com.wherewasi.wherewasiapi.dto.response;

import com.wherewasi.wherewasiapi.model.Show;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * DTO for show-specific page after user selects a preview from the homepage or search results.
 * Does not contain episode specific information, only data needed to display the show.
 */

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowDetailsDTO {
    private String id;
    private String homepage;
    private String name;
    private String overview;
    private String firstAirDate;
    private String posterPath;
    private String backdropPath;
    private Float popularity;
    private Float voteAverage;
    private Integer voteCount;
    private Integer numberOfSeasons;
    private Map<Integer, Integer> seasonEpisodeCounts;

    private List<Show.Creator> creators;
    private List<Show.Genre> genres;
    private List<Show.Network> networks;
}
