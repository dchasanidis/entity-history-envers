package com.dchasanidis.envershistory.controllers;

import com.dchasanidis.envershistory.entities.envers.RevisionDetails;
import com.dchasanidis.envershistory.entities.envers.RevisionEntry;
import com.dchasanidis.envershistory.entities.model.UserEntity;
import com.dchasanidis.envershistory.services.RevisionDetailsService;
import com.dchasanidis.envershistory.services.RevisionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class HistoryController {
    private final RevisionService revisionService;
    private final RevisionDetailsService<UserEntity> revisionDetailsService;

    public HistoryController(final RevisionService revisionService, final RevisionDetailsService<UserEntity> revisionDetailsService) {
        this.revisionService = revisionService;
        this.revisionDetailsService = revisionDetailsService;
    }

    @GetMapping("/users/{userId}/revisions")
    public ResponseEntity<List<RevisionEntry>> getUserHistory(@PathVariable final UUID userId) {
        return ResponseEntity.ok(revisionService.getRevisions(userId));
    }

    @GetMapping("/users/{userId}/revisions/{revisionId}")
    public ResponseEntity<RevisionDetails> getUserHistory(@PathVariable final UUID userId, @PathVariable final Long revisionId) {
        return ResponseEntity.ok(revisionDetailsService.getRevisionDetails(UserEntity.class, userId, revisionId));
    }
}
