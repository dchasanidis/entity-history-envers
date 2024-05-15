package com.dchasanidis.envershistory.entities.envers;

import com.dchasanidis.envershistory.services.CustomRevisionListener;
import jakarta.persistence.*;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

@Entity
@Table(name = "revinfo")
@RevisionEntity(CustomRevisionListener.class)
public class MyRevision {

    @Id
    @GeneratedValue
    @RevisionNumber
    private long rev;

    @RevisionTimestamp
    @Column(name = "revtstmp")
    private long revtstmp;

    @Column(name = "username")
    private String username;


    public long getRev() {
        return rev;
    }

    public MyRevision setRev(final long rev) {
        this.rev = rev;
        return this;
    }

    public long getRevtstmp() {
        return revtstmp;
    }

    public MyRevision setRevtstmp(final long revtstmp) {
        this.revtstmp = revtstmp;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public MyRevision setUsername(final String username) {
        this.username = username;
        return this;
    }
}