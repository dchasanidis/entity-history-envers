package com.dchasanidis.envershistory.entities.model;

import com.dchasanidis.envershistory.entities.envers.Identifiable;
import jakarta.persistence.*;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Entity
@Audited
@EntityListeners(AuditingEntityListener.class)
public class UserEntity implements Identifiable<UUID> {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;


    @Override
    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserEntity id(UUID id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public UserEntity email(final String email) {
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public UserEntity name(final String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public UserEntity address(final String address) {
        this.address = address;
        return this;
    }
}
