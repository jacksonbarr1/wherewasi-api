package com.wherewasi.wherewasiapi.repository;

import com.wherewasi.wherewasiapi.model.UserShowProgress;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserShowProgressRepository extends MongoRepository<UserShowProgress, String> {
}
