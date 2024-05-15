package com.dchasanidis.envershistory.entities.envers;

import java.util.List;
import java.util.UUID;

public record RevisionEntry(
        Long revisionNumber,
        UUID entityId,
        String entityName,
        RevisionType operationType,
        List<String> properties
) {
}
