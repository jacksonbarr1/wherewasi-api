package com.wherewasi.wherewasiapi.repository;

import com.wherewasi.wherewasiapi.model.UserShowProgress;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserShowProgressRepository extends MongoRepository<UserShowProgress, String> {
    Optional<UserShowProgress> findByUserIdAndShowId(String userId, String showId);
}
