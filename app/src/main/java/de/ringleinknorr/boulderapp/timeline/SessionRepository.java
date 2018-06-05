package de.ringleinknorr.boulderapp.timeline;

import android.arch.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

public class SessionRepository {

    private SessionDB db;

    @Inject
    public SessionRepository(SessionDB db) {
        this.db = db;
    }

    public Session getSession(long sessionId) {
        return db.getSession(sessionId);
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
