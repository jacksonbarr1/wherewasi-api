package com.wherewasi.wherewasiapi.dto;

import com.wherewasi.wherewasiapi.model.Show;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowMetadataDTO {
    private Integer id;
    private String name;
    private List<Show.Genre> genres;
    private String firstAirDate;
    private Float voteAverage;
    private Integer voteCount;
    private Float popularity;
    private String posterPath;
}
