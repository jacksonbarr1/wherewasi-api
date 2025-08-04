package com.wherewasi.wherewasiapi.client;

import com.wherewasi.wherewasiapi.client.dto.TmdbChangesResponse;
import com.wherewasi.wherewasiapi.client.dto.TmdbGenreListResponse;
import com.wherewasi.wherewasiapi.client.dto.TmdbSearchResponse;
import com.wherewasi.wherewasiapi.model.Show;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.net.URI;
import java.time.Duration;
import java.util.Optional;


@Component
public class TmdbApiClient {

    private static final Logger logger = LoggerFactory.getLogger(TmdbApiClient.class);
    private static final String TMDB_API_BASE_URL = "https://api.themoviedb.org/3";
    private final RestClient restClient;
    private final Bucket apiRequestBucket;

    public TmdbApiClient(RestClient tmdbRestClient) {
        this.restClient = tmdbRestClient;

        Bandwidth limit = Bandwidth.simple(50, Duration.ofSeconds(1));

        this.apiRequestBucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    public Optional<TmdbSearchResponse> searchTvShows(String query, int currentPage) {
        URI uri = URI.create(String.format("%s/search/tv?query=%s&page=%d", TMDB_API_BASE_URL, query, currentPage));
        return executeApiCall(uri, TmdbSearchResponse.class, "TV Show Search", query, currentPage);
    }

    public Optional<TmdbGenreListResponse> getTvGenreList() {
        URI uri = URI.create(String.format("%s/genre/tv/list", TMDB_API_BASE_URL));
        return executeApiCall(uri, TmdbGenreListResponse.class, "TV Genre List");
    }

    public Optional<Show> getShowById(String id) {
        URI uri = URI.create(String.format("%s/tv/%s", TMDB_API_BASE_URL, id));
        Optional<Show> responseOptional = executeApiCall(uri, Show.class, "TV Show Details", id);

        if (responseOptional.isPresent()) {
            Show show = responseOptional.get();
            return Optional.of(show);
        }

        return Optional.empty();
    }

    public Optional<TmdbChangesResponse> getChangesById(String id) {
        URI uri = URI.create(String.format("%s/tv/%d/changes", TMDB_API_BASE_URL, id));
        Optional<TmdbChangesResponse> responseOptional = executeApiCall(uri, TmdbChangesResponse.class,
                "TV Show Changes", id);

        if (responseOptional.isPresent()) {
            TmdbChangesResponse response = responseOptional.get();
            if (response.getChanges() == null || response.getChanges().isEmpty()) {
                return Optional.empty();
            }
        }

        return responseOptional;
    }

    private <T> Optional<T> executeApiCall(URI uri, Class<T> responseType, String description, Object... identifiers) {
        try {
            apiRequestBucket.asBlocking().consume(1);

            logger.info("Making API call to {} for {} with identifiers: {}",
                    uri, description, identifiers);

            T responseBody = restClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(HttpStatus.NOT_FOUND::equals, (req, res) -> {
                        throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Not Found for " + description + ": "
                                + identifiers[0]);
                    })
                    .onStatus(HttpStatus.TOO_MANY_REQUESTS::equals, (req, res) -> {
                        throw new HttpClientErrorException(HttpStatus.TOO_MANY_REQUESTS, "Too Many Requests for " + description + ": " + identifiers[0]);
                    })
                    .body(responseType);

            if (responseBody != null) {
                return Optional.of(responseBody);
            } else {
                logger.warn("API call for {} returned null body. Identifiers: {}", description, identifiers);
                return Optional.empty();
            }
        } catch (HttpClientErrorException.NotFound e) {
            logger.warn("API call for {} returned 404 Not Found. Identifiers: {}", description, identifiers);
            return Optional.empty();
        } catch (HttpClientErrorException.TooManyRequests e) {
            logger.warn("API call for {} returned 429 Too Many Requests. Identifiers: {}", description, identifiers);
            return Optional.empty();
        } catch (RestClientException e) {
            logger.error("API call for {} failed with error: {}. Identifiers: {}", description, e.getMessage(), identifiers);
            return Optional.empty();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("API call for {} was interrupted while waiting for rate limit token. Identifiers: {}", description, identifiers);
            return Optional.empty();
        } catch (Exception e) {
            logger.error("API call for {} failed with unexpected error: {}. Identifiers: {}", description, e.getMessage(), identifiers);
            return Optional.empty();
        }
    }
}
