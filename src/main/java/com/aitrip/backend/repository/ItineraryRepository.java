package com.aitrip.backend.repository;

import com.aitrip.backend.model.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository; // Use MongoRepository for MongoDB
import org.springframework.stereotype.Repository;

@Repository
public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {
}