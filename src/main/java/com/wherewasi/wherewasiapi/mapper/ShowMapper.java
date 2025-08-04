package com.wherewasi.wherewasiapi.mapper;

import com.wherewasi.wherewasiapi.dto.response.ShowDetailsDTO;
import com.wherewasi.wherewasiapi.model.Show;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ShowMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstAirDate", source = "firstAirDate", qualifiedByName = "localDateToString")
    @Mapping(target = "seasonEpisodeCounts", source = "seasons", qualifiedByName = "seasonsToEpisodeCounts")
    ShowDetailsDTO showToShowDetailsDTO(Show show);

    @Named("localDateToString")
    default String localDateToString(LocalDate date) {
        return date != null ? date.format(DateTimeFormatter.ISO_LOCAL_DATE) : null;
    }

    @Named("seasonsToEpisodeCounts")
    default Map<Integer, Integer> seasonsToEpisodeCounts(List<Show.Season> seasons) {
        if (seasons == null) {
            return null;
        }
        return seasons.stream()
                .collect(Collectors.toMap(
                        Show.Season::getSeasonNumber,
                        Show.Season::getEpisodeCount,
                        (existing, replacement) -> existing
                ));
    }
}
