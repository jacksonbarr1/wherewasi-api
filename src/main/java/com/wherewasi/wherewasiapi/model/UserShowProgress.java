package com.wherewasi.wherewasiapi.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_show_progress")
@CompoundIndex(name = "user_show_idx", def = "{'userId': 1, 'showId': 1}", unique = true)
public class UserShowProgress {

    @MongoId
    private String id;

    private String userId;
    private String showId;

    private Map<Integer, List<Integer>> watchedEpisodesBySeason;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime lastUpdatedAt;
}
