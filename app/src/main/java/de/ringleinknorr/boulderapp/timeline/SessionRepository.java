package de.ringleinknorr.boulderapp.timeline;

import android.arch.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

public class SessionRepository {

    private SessionDB db;

    public SessionRepository(SessionDB db) {
        this.db = db;
    }

    public LiveData<List<Session>> getAllSessions() {
        return db.getAllSessions();
    }

    public Session addSession() {
        Session session = new Session(new Date(2018, (int) (Math.random() * 11), (int) (Math.random() * 28)));
        db.addSession(session);
        return session;
    }

    public void removeAllSessions() {
        db.removeAllSessions();
    }

}
