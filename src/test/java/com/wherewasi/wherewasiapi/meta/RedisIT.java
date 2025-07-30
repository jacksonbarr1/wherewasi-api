package com.wherewasi.wherewasiapi.meta;

import com.wherewasi.wherewasiapi.AbstractIT;
import com.wherewasi.wherewasiapi.dto.response.ShowMetadataDTO;
import com.wherewasi.wherewasiapi.model.Show;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RedisIT extends AbstractIT {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void whenStoreShowMetadataDTOValue_thenShouldReturnCorrectValue() {
        ShowMetadataDTO dto = ShowMetadataDTO.builder()
                .id(1)
                .name("Test Show")
                .genres(
                        List.of(
                                Show.Genre.builder()
                                        .id(2)
                                        .name("Comedy")
                                        .build(),
                                Show.Genre.builder()
                                        .id(3)
                                        .name("Drama")
                                        .build()

                        )
                )
                .firstAirDate("2023-01-01")
                .voteAverage(8.5f)
                .voteCount(100)
                .popularity(10.1f)
                .posterPath("/path/to/poster.jpg")
                .build();

        String cacheKey = "show_metadata:" + dto.getId();

        redisTemplate.opsForValue().set(cacheKey, dto);

        ShowMetadataDTO cachedDto = (ShowMetadataDTO) redisTemplate.opsForValue().get(cacheKey);

        assertThat(cachedDto).isNotNull();
        assertThat(cachedDto.getId()).isEqualTo(dto.getId());
        assertThat(cachedDto.getName()).isEqualTo(dto.getName());
        assertThat(cachedDto.getGenres().size()).isEqualTo(2);
        assertThat(cachedDto.getGenres().get(0).getName()).isEqualTo("Comedy");
        assertThat(cachedDto.getGenres().get(1).getName()).isEqualTo("Drama");
        assertThat(cachedDto.getFirstAirDate()).isEqualTo(dto.getFirstAirDate());
        assertThat(cachedDto.getVoteAverage()).isEqualTo(dto.getVoteAverage());
        assertThat(cachedDto.getVoteCount()).isEqualTo(dto.getVoteCount());
        assertThat(cachedDto.getPopularity()).isEqualTo(dto.getPopularity());
        assertThat(cachedDto.getPosterPath()).isEqualTo(dto.getPosterPath());
    }
}
