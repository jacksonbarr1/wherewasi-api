package com.wherewasi.wherewasiapi.service;

import com.wherewasi.wherewasiapi.client.TmdbApiClient;
import com.wherewasi.wherewasiapi.client.dto.TmdbChangesResponse;
import com.wherewasi.wherewasiapi.model.Show;
import com.wherewasi.wherewasiapi.repository.ShowRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TmdbServiceTest {
    @Mock
    private ShowRepository showRepository;

    @Mock
    private TmdbApiClient tmdbApiClient;

    @InjectMocks
    private TmdbServiceImpl tmdbService;

    @Test
    void shouldReturnTrueWhenShowNotInDatabase() {
        String id = "1";
        when(showRepository.findById(id)).thenReturn(Optional.empty());

        boolean result = tmdbService.shouldRefetchShow(id);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenShowIsEnded() {
        String id = "2";
        Show show = new Show();
        show.setStatus("Ended");
        when(showRepository.findById(id)).thenReturn(Optional.of(show));

        boolean result = tmdbService.shouldRefetchShow(id);

        assertFalse(result);
    }

    @Test
    void shouldReturnTrueWhenShowIsOngoingAndLastFetchedAtIsNull() {
        String id = "3";
        Show show = new Show();
        show.setStatus("Ongoing");
        show.setLastFetchedAt(null);
        when(showRepository.findById(id)).thenReturn(Optional.of(show));

        boolean result = tmdbService.shouldRefetchShow(id);

        assertTrue(result);
    }

    @Test
    void shouldReturnTrueWhenShowIsOngoingAndLastFetchedAtIsStale() {
        String id = "4";
        Show show = new Show();
        show.setStatus("Ongoing");
        show.setLastFetchedAt(LocalDateTime.now().minusHours(37));
        when(showRepository.findById(id)).thenReturn(Optional.of(show));

        boolean result = tmdbService.shouldRefetchShow(id);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenShowIsOngoingAndLastFetchedAtIsFresh() {
        String id = "5";
        Show show = new Show();
        show.setStatus("Ongoing");
        show.setLastFetchedAt(LocalDateTime.now().minusHours(12));
        when(showRepository.findById(id)).thenReturn(Optional.of(show));

        boolean result = tmdbService.shouldRefetchShow(id);

        assertFalse(result);
    }

    @Test
    void shouldReturnTrueWhenChangesArePresent() {
        String id = "6";
        Show show = new Show();
        show.setStatus("Ongoing");
        show.setLastFetchedAt(LocalDateTime.now().minusHours(1)); // Simulating a recent fetch to not return true early
        when(showRepository.findById(id)).thenReturn(Optional.of(show));
        when(tmdbApiClient.getChangesById(id)).thenReturn(Optional.of(new TmdbChangesResponse()));

        boolean result = tmdbService.shouldRefetchShow(id);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenChangesAreEmpty() {
        String id = "7";
        Show show = new Show();
        show.setStatus("Ongoing");
        show.setLastFetchedAt(LocalDateTime.now().minusHours(1));
        when(showRepository.findById(id)).thenReturn(Optional.of(show));
        when(tmdbApiClient.getChangesById(id)).thenReturn(Optional.empty());

        boolean result = tmdbService.shouldRefetchShow(id);

        assertFalse(result);
    }
}
