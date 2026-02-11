package com.aitrip.backend.repository;

import com.aitrip.backend.model.Trip;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface TripRepository extends MongoRepository<Trip, String> {
    List<Trip> findByUserId(String userId);
}