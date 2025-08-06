package com.wherewasi.wherewasiapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserShowProgressResponse {
    private String showId;
    private Map<Integer, List<Integer>> watchedEpisodesBySeason;
}
