package com.dchasanidis.envershistory.entities.model;

import com.dchasanidis.envershistory.entities.envers.AuditedEntity;
import jakarta.persistence.*;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Entity
@Audited
@EntityListeners(AuditingEntityListener.class)
public class UserEntity implements AuditedEntity<UUID> {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Embedded
    private Address address;

    @Override
    public String getIdAsString() {
        return getId().toString();
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(final String email) {
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserEntity setName(final String name) {
        this.name = name;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public UserEntity setAddress(final Address address) {
        this.address = address;
        return this;
    }
}
