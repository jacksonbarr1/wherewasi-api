package com.wherewasi.wherewasiapi.service;

import com.wherewasi.wherewasiapi.client.TmdbApiClient;
import com.wherewasi.wherewasiapi.client.dto.TmdbChangesResponse;
import com.wherewasi.wherewasiapi.client.dto.TmdbSearchResponse;
import com.wherewasi.wherewasiapi.client.dto.TmdbSearchResult;
import com.wherewasi.wherewasiapi.dto.response.ShowMetadataDTO;
import com.wherewasi.wherewasiapi.model.Show;
import com.wherewasi.wherewasiapi.repository.ShowRepository;
import com.wherewasi.wherewasiapi.util.CacheConstants;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.wherewasi.wherewasiapi.util.QueryNormalizer.normalizeQuery;

@Service
@AllArgsConstructor
public class TmdbServiceImpl implements TmdbService {
    private static final int DESIRED_SEARCH_SUGGESTIONS_COUNT = 5;
    private static final int MAX_SEARCH_PAGES_TO_FETCH = 3;
    private final TmdbApiClient tmdbApiClient;
    private final GenreService genreService;
    private final ShowRepository showRepository;

    public List<ShowMetadataDTO> searchTvShows(String query) {
        String normalizedQuery = normalizeQuery(query);

        List<TmdbSearchResult> rawResults = new ArrayList<>();

        int currentPage = 1;
        int totalPagesAvailable = 1;

        while (rawResults.stream().filter(this::isEnglishLanguage).count() < DESIRED_SEARCH_SUGGESTIONS_COUNT &&
                currentPage <= MAX_SEARCH_PAGES_TO_FETCH &&
                currentPage <= totalPagesAvailable) {
            Optional<TmdbSearchResponse> searchResponseOptional = tmdbApiClient.searchTvShows(normalizedQuery, currentPage);

            if (searchResponseOptional.isEmpty() || searchResponseOptional.get().getResults() == null
                    || searchResponseOptional.get().getResults().isEmpty()) {
                // No results found or an error occurred
                break;
            }

            TmdbSearchResponse searchResponse = searchResponseOptional.get();

            rawResults.addAll(searchResponse.getResults());
            totalPagesAvailable = searchResponse.getTotalPages();
            currentPage++;
        }

        // TODO: Sort by popularity
        return rawResults.stream()
                .filter(this::isEnglishLanguage)
                .map(this::mapToShowMetadataDTO)
                .limit(DESIRED_SEARCH_SUGGESTIONS_COUNT)
                .collect(Collectors.toList());
    }

    @Cacheable(value = CacheConstants.CACHE_NAME_SHOW, key = "#id")
    public Optional<Show> getShowById(String id) {
        return tmdbApiClient.getShowById(id);
    }

    public boolean shouldRefetchShow(String id) {
        Optional<Show> showOptional = showRepository.findById(id);

        if (showOptional.isEmpty()) {
            return true;
        }

        Show show = showOptional.get();

        LocalDateTime freshnessThreshold = LocalDateTime.now().minusHours(36);

        if (show.getStatus().equalsIgnoreCase("ended")) {
            return false;
        }

        if (show.getLastFetchedAt() == null || show.getLastFetchedAt().isBefore(freshnessThreshold)) {
            return true;
        }

        Optional<TmdbChangesResponse> changesOptional = tmdbApiClient.getChangesById(id);

        return changesOptional.isPresent();
    }

    private boolean isEnglishLanguage(TmdbSearchResult dto) {
        return dto.getOriginLanguage() != null && dto.getOriginLanguage().equalsIgnoreCase("en");
    }

    private ShowMetadataDTO mapToShowMetadataDTO(TmdbSearchResult tmdbResult) {
        List<Show.Genre> mappedGenres = new ArrayList<>();
        if (tmdbResult.getGenreIds() != null) {
            tmdbResult.getGenreIds().forEach(genreId -> {
                Show.Genre genre = genreService.getGenreById(genreId);
                if (genre != null) {
                    mappedGenres.add(genre);
                }
            });
        }

        return ShowMetadataDTO.builder()
                .id(tmdbResult.getId())
                .name(tmdbResult.getName())
                .genres(mappedGenres)
                .firstAirDate(tmdbResult.getFirstAirDate())
                .voteAverage(tmdbResult.getVoteAverage())
                .voteCount(tmdbResult.getVoteCount())
                .popularity(tmdbResult.getPopularity())
                .posterPath(tmdbResult.getPosterPath())
                .build();
    }
}
