package com.wherewasi.wherewasiapi.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbChangesResponse {
    private List<TmdbChange> changes;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TmdbChange {
        private String key;
        private List<TmdbChangeItem> items;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TmdbChangeItem {
        private String id;
        private String action;
        private String time;
        private String iso6391;
        private String iso31661;
    }
}
