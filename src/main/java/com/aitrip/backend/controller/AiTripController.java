package com.aitrip.backend.controller;

import com.aitrip.backend.model.Itinerary;
import com.aitrip.backend.model.Trip;
import com.aitrip.backend.service.AiTripService;
import com.aitrip.backend.repository.ItineraryRepository;
import com.aitrip.backend.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// 🟢 FIX: Import java.util.List here, at the top
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trips")
@CrossOrigin(origins = "http://localhost:5173")
public class AiTripController {

    @Autowired
    private AiTripService aiTripService;

    @Autowired
    private ItineraryRepository itineraryRepository;

    @Autowired
    private TripRepository tripRepository;

    // 🟢 Generate Trip Endpoint
    @PostMapping("/generate")
    public Itinerary generateTrip(@RequestBody Map<String, Object> request) {
        String destination = (String) request.get("destination");
        int days = Integer.parseInt(request.get("days").toString());
        String budget = (String) request.get("budget");
        String style = (String) request.get("style");
        return aiTripService.generateTrip(destination, days, budget, style);
    }

    // 🟢 Save Trip Endpoint
    @PostMapping("/save")
    public ResponseEntity<String> saveTrip(@RequestBody Trip trip) {
        try {
            // Ensure the Trip model has 'userId', 'destination', and 'itinerary' fields
            tripRepository.save(trip);
            return ResponseEntity.ok("Trip saved successfully! ✅");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error saving trip: " + e.getMessage());
        }
    }

    // 🟢 Get User Trips Endpoint
    @GetMapping("/user/{userId}")
    public List<Trip> getUserTrips(@PathVariable String userId) {
        // This fetches all trips matching the email/userId
        return tripRepository.findByUserId(userId);
    }
}