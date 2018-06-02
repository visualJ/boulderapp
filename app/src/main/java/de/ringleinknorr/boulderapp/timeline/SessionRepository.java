package de.ringleinknorr.boulderapp.timeline;

import android.arch.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class SessionRepository {

    private SessionDB db;

    @Inject
    public SessionRepository(SessionDB db) {
        this.db = db;
    }

    public LiveData<List<Session>> getAllSessions() {
        return db.getAllSessions();
    }

    public Session addSession(Session session) {
        db.addSession(session);
        return session;
    }

    public void removeAllSessions() {
        db.removeAllSessions();
    }

}
