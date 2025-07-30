package com.wherewasi.wherewasiapi.service;

import com.wherewasi.wherewasiapi.AbstractIT;
import com.wherewasi.wherewasiapi.dto.response.ShowMetadataDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.wherewasi.wherewasiapi.util.RedisKeyGenerator.getTvShowSearchKey;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ShowServiceIT extends AbstractIT {

    @Autowired
    private ShowService showService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private TmdbService tmdbService;

    @BeforeEach
    void setUp() {
        redisTemplate.getConnectionFactory().getConnection().serverCommands().flushAll();
    }


    @Test
    void whenSearchQueryIsCached_thenReturnCachedResults() {
        String query = "Test Query";
        String cacheKey = getTvShowSearchKey(query);

        List<ShowMetadataDTO> expectedResults = Arrays.asList(
                ShowMetadataDTO.builder().id(1).name("Test Show 1").build(),
                ShowMetadataDTO.builder().id(2).name("Test Show 2").build()
        );

        redisTemplate.opsForValue().set(cacheKey, expectedResults, 60, TimeUnit.MINUTES);

        List<ShowMetadataDTO> actualResults = showService.searchShows(query);

        assertThat(actualResults).isNotNull();
        assertThat(actualResults).isEqualTo(expectedResults);
    }
}
