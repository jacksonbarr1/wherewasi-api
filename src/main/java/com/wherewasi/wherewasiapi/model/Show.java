package com.wherewasi.wherewasiapi.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "shows")
public class Show {
    // All `id` fields map 1:1 to the TMDB ID
    @MongoId
    private ObjectId id;
    private Boolean adult;
    private String homepage;
    private String name;
    private String overview;
    private LocalDate firstAirDate;
    private LocalDate lastAirDate;
    private String posterPath;
    private String backdropPath;
    private Float popularity;
    private Float voteAverage;
    private Integer voteCount;
    private Boolean isInProduction;
    private String status;
    private String tagline;
    private Integer numberOfEpisodes;
    private Integer numberOfSeasons;

    private LocalDateTime lastFetchedAt;

    private List<Season> seasons;
    private List<Genre> genres;
    private List<Creator> creators;
    private List<Network> networks;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Season {
        private int id;
        private String name;
        private int seasonNumber;
        private int episodeCount;
        private LocalDate airDate;
        private String posterPath;
        private String overview;

        private List<Episode> episodes;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Episode {
        private int id;
        private int episodeNumber;
        private LocalDate airDate;
        private String name;
        private String overview;
        private float voteAverage;
        private int voteCount;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Genre {
        private int id;
        private String name;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Creator {
        private int id;
        private String name;
        private String profilePath;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Network {
        private int id;
        private String name;
        private String logoPath;
        private String originCountry;
    }
}
