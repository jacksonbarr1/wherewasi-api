package com.wherewasi.wherewasiapi.repository;

import com.wherewasi.wherewasiapi.model.Show;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShowRepository extends MongoRepository<Show, String> {
}
