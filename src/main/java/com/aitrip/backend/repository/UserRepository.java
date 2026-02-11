package com.aitrip.backend.repository;

import com.aitrip.backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    // Finds a user by email (Used for Login)
    Optional<User> findByEmail(String email);

    // Checks if an email already exists (Used for Registration)
    boolean existsByEmail(String email);
}