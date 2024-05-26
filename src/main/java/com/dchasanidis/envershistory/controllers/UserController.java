package com.dchasanidis.envershistory.controllers;

import com.dchasanidis.envershistory.entities.model.UserEntity;
import com.dchasanidis.envershistory.entities.requests.CreateUserRequest;
import com.dchasanidis.envershistory.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
public class UserController {
    private final UserRepository userRepository;

    public UserController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PostMapping("/users")
    public ResponseEntity<UserEntity> createUser(final @RequestBody CreateUserRequest body) {
        return ResponseEntity.ok(userRepository.save(
                new UserEntity()
                        .email(body.email())
                        .name(body.name())
                        .address(body.address()))
        );
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<UserEntity> modifyUser(@PathVariable final UUID userId, final @RequestBody CreateUserRequest body) {
        final Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isPresent()) {
            final UserEntity updated = user.get()
                    .name(body.name())
                    .email(body.email())
                    .address(body.address());
            return ResponseEntity.ok(userRepository.save(updated));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable final String userId) {
        userRepository.deleteById(UUID.fromString(userId));
        return ResponseEntity.accepted().build();
    }
}
