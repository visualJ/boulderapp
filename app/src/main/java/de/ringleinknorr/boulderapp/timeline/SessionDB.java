package de.ringleinknorr.boulderapp.timeline;

import android.arch.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.ObjectBoxLiveData;
import io.objectbox.query.QueryBuilder;

public class SessionDB {

    private Box<Session> box;

    @Inject
    public SessionDB(BoxStore boxStore) {
        this.box = boxStore.boxFor(Session.class);
    }

    public LiveData<List<Session>> getAllSessions() {
        return new ObjectBoxLiveData<>(box.query().order(Session_.date, QueryBuilder.DESCENDING).build());
    }

    public void addSession(Session session) {
        box.put(session);
    }

    public void removeAllSessions() {
        box.removeAll();
    }
}
