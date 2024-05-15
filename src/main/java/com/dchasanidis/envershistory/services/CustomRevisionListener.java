package com.dchasanidis.envershistory.services;

import com.dchasanidis.envershistory.entities.envers.MyRevision;
import org.hibernate.envers.RevisionListener;
import org.springframework.stereotype.Component;

@Component
public class CustomRevisionListener implements RevisionListener {

    @Override
    public void newRevision(final Object revisionEntity) {
        final String principalName = getDummyUsername();
        final MyRevision myRevision = (MyRevision) revisionEntity;
        myRevision.setUsername(principalName);
    }

    // assume we have a proper authentication/authorization system
    private static String getDummyUsername() {
        return "Dummy_username";
    }
}