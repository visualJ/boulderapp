package de.ringleinknorr.boulderapp.repositories;

import android.arch.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.ringleinknorr.boulderapp.models.Session;

@Singleton
public class SessionRepository {

    private SessionDB db;

    @Inject
    public SessionRepository(SessionDB db) {
        this.db = db;
    }

    public LiveData<Session> getSession(long sessionId) {
        return db.getSession(sessionId);
    }

    public LiveData<List<Session>> getAllSessions() {
        return db.getAllSessions();
    }

    public long putSession(Session session) {
        return db.addSession(session);
    }

    public LiveData<List<Session>> getSessionsInMonth(Date date) {
        return db.getSessionsInMonth(date);
    }

    public void removeAllSessions() {
        db.removeAllSessions();
    }

    public void removeSession(Session session) {
        db.removeSession(session);
    }
}
