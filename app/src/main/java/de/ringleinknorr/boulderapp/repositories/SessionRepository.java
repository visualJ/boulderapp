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

    /**
     * Retrieve a specific session.
     *
     * @param sessionId The session id.
     * @return The session or null, if it does not exist.
     */
    public LiveData<Session> getSession(long sessionId) {
        return db.getSession(sessionId);
    }

    /**
     * Retrieve all sessions.
     * @return A live list of all sessions.
     */
    public LiveData<List<Session>> getAllSessions() {
        return db.getAllSessions();
    }

    /**
     * Add a session into the db or update its values.
     * @param session The session to put.
     * @return The session id.
     */
    public long putSession(Session session) {
        return db.addSession(session);
    }

    /**
     * Return sessions from the month previous to the given date.
     * @param date The date that should be used as reference.
     * @return A live list of sessions that are dated in the month before the given date.
     */
    public LiveData<List<Session>> getSessionsFromPreviousMonth(Date date) {
        return db.getSessionsFromPreviousMonth(date);
    }

    /**
     * Return sessions previous to the given session.
     * @param session The session that should be used as reference.
     * @param count The maximal number of session that should be returned.
     * @return A live list of sessions that are previous to the given session.
     */
    public LiveData<List<Session>> getPreviousSessions(Session session, int count) {
        return db.getPreviousSessions(session, count);
    }

    /**
     * Retrieve the session directly before a given session.
     * @param session The session to find the predecessor for.
     * @return The live session predecessor. If it does not exist, nothing is posted to the live data.
     */
    public LiveData<Session> getPreviousSession(Session session) {
        return db.getPreviousSession(session);
    }

    /**
     * Remove all sessions.
     */
    public void removeAllSessions() {
        db.removeAllSessions();
    }

    /**
     * Remove the specified session.
     * @param session The session to remove.
     */
    public void removeSession(Session session) {
        db.removeSession(session);
    }
}
