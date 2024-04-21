package com.dchasanidis.envershistory.controllers;

import com.dchasanidis.envershistory.entities.model.Address;
import com.dchasanidis.envershistory.entities.model.UserEntity;
import com.dchasanidis.envershistory.entities.requests.CreateUserRequest;
import com.dchasanidis.envershistory.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    public ResponseEntity<UserEntity> createUser(@RequestBody CreateUserRequest body) {
        return ResponseEntity.ok(userRepository.save(new UserEntity()
                .setEmail(body.email())
                .setName(body.name())
                .setAddress(new Address(body.address().getRoad(), body.address().getNumber(), body.address().getPostalCode())))
        );
    }
}
