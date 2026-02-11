package com.aitrip.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Itinerary {
    private String destination;
    private List<Hotel> hotels;
    private List<Day> days;

    // Getters and Setters
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public List<Hotel> getHotels() { return hotels; }
    public void setHotels(List<Hotel> hotels) { this.hotels = hotels; }
    public List<Day> getDays() { return days; }
    public void setDays(List<Day> days) { this.days = days; }

    // --- Inner Classes ---

    public static class Hotel {
        private String name;
        private String price;
        private String rating;
        private String address;
        private String type;

        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getPrice() { return price; }
        public void setPrice(String price) { this.price = price; }
        public String getRating() { return rating; }
        public void setRating(String rating) { this.rating = rating; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
    }

    public static class Day {
        private int day;
        private List<Activity> activities;

        // Getters and Setters
        public int getDay() { return day; }
        public void setDay(int day) { this.day = day; }
        public List<Activity> getActivities() { return activities; }
        public void setActivities(List<Activity> activities) { this.activities = activities; }
    }

    public static class Activity {
        private String time;
        private String name;
        private String description;
        private double cost; // Ensure this is double or int
        private String type;

        @JsonProperty("fee_reason") // 🟢 MAPS JSON 'fee_reason' to Java 'feeReason'
        private String feeReason;

        // Getters and Setters
        public String getTime() { return time; }
        public void setTime(String time) { this.time = time; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public double getCost() { return cost; }
        public void setCost(double cost) { this.cost = cost; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        
        public String getFeeReason() { return feeReason; }
        public void setFeeReason(String feeReason) { this.feeReason = feeReason; }
    }
}