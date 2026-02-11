package com.aitrip.backend.service;

import com.aitrip.backend.model.Itinerary;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

@Service
public class AiTripService {

    @Value("${openrouter.api-key}")
    private String apiKey;

    private static final String OPENROUTER_URL = "https://openrouter.ai/api/v1/chat/completions";

    public Itinerary generateTrip(String destination, int days, String budget, String style) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            String prompt = String.format(
                "Generate a realistic %d-day itinerary for %s. Style: %s. Budget: %s. " +
                "JSON ONLY. No markdown. " +
                "Structure: { \"destination\": \"%s\", " +
                "\"hotels\": [ { \"name\": \"...\", \"price\": \"₹2500\", \"rating\": \"4.2\", \"address\": \"...\", \"type\": \"Stay\" } ], " +
                "\"days\": [ { \"day\": 1, \"activities\": [ " +
                "{ \"time\": \"08:00 AM\", \"name\": \"[Temple Name]\", \"description\": \"...\", \"cost\": 0, \"fee_reason\": \"Free Darshan\", \"type\": \"Place\" }, " +
                "{ \"time\": \"01:00 PM\", \"name\": \"Lunch at [Restaurant]\", \"description\": \"...\", \"cost\": 350, \"fee_reason\": \"Thali Cost\", \"type\": \"Food\" } " +
                "] } ] } " +
                "RULES: " +
                "1. RELIGIOUS PLACES (Temples, Mandirs, Mosques, Churches) MUST HAVE COST: 0. " +
                "2. 'fee_reason' for Temples must be 'Free Darshan'. " +
                "3. Food costs must be realistic (₹200-₹600).", 
                days, destination, style, budget, destination
            );

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "google/gemini-2.0-flash-001"); 
            requestBody.put("messages", List.of(Map.of("role", "user", "content", prompt)));
            requestBody.put("temperature", 0.7);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(OPENROUTER_URL, entity, String.class);
            
            return parseOpenRouterResponse(response.getBody(), destination);
        } catch (Exception e) {
            e.printStackTrace();
            return new Itinerary(); 
        }
    }

    private Itinerary parseOpenRouterResponse(String response, String destination) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            var root = mapper.readTree(response);
            
            String text = "";
            if (root.has("choices") && root.path("choices").isArray() && !root.path("choices").isEmpty()) {
                 text = root.path("choices").get(0).path("message").path("content").asText();
            } else {
                 return new Itinerary(); 
            }

            text = text.replaceAll("```json", "").replaceAll("```", "").trim();
            int firstBracket = text.indexOf("{");
            int lastBracket = text.lastIndexOf("}");
            if (firstBracket != -1) text = text.substring(firstBracket, lastBracket + 1);

            Itinerary itinerary = mapper.readValue(text, Itinerary.class);

            // 🟢 FORCE RESET: Wipe costs for Religious Places
            if (itinerary.getDays() != null) {
                for (Itinerary.Day day : itinerary.getDays()) {
                    if (day.getActivities() != null) {
                        for (Itinerary.Activity act : day.getActivities()) {
                            String nameLower = act.getName().toLowerCase();
                            // Check for common religious terms
                            if (nameLower.contains("temple") || nameLower.contains("mandir") || 
                                nameLower.contains("gurudwara") || nameLower.contains("mosque") || 
                                nameLower.contains("church") || nameLower.contains("dargah") ||
                                nameLower.contains("shrine") || nameLower.contains("math") || nameLower.contains("samadhi")) {
                                
                                act.setCost(0); 
                                act.setFeeReason("Free Darshan"); 
                            }
                        }
                    }
                }
            }
            return itinerary;

        } catch (Exception e) { 
            e.printStackTrace();
            return new Itinerary(); 
        }
    }
}