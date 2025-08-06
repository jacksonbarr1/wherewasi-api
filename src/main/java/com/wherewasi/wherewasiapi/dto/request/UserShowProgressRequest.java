package com.wherewasi.wherewasiapi.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserShowProgressRequest {
    @NotBlank
    private String showId;

    @Valid
    @NotNull
    @NotEmpty
    private Map<@Positive  Integer, @NotEmpty List< @Positive Integer>> watchedEpisodesBySeason;
}
