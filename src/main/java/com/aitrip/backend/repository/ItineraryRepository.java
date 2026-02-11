package com.aitrip.backend.repository;

import com.aitrip.backend.model.Itinerary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItineraryRepository extends MongoRepository<Itinerary, String> {
    // 🟢 Changed JpaRepository -> MongoRepository
    // 🟢 Changed Long -> String (because MongoDB IDs are strings)
}
