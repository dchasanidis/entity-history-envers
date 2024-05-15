package com.dchasanidis.envershistory.controllers;

import com.dchasanidis.envershistory.entities.model.UserEntity;
import com.dchasanidis.envershistory.entities.requests.CreateUserRequest;
import com.dchasanidis.envershistory.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/users")
    public ResponseEntity<UserEntity> modifyUser() {
        final UserEntity updated = userRepository.findAll().getFirst()
                .name("Updated Name")
                .email("updated@email.com");
        return ResponseEntity.ok(userRepository.save(updated));
    }
}
