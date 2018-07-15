package de.ringleinknorr.boulderapp.fragments;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import de.ringleinknorr.boulderapp.models.Session;
import de.ringleinknorr.boulderapp.repositories.SessionRepository;

/**
 * The view model for {@link SessionFragment}.
 */
public class SessionViewModel extends ViewModel {

    private SessionRepository sessionRepository;

    private Long sessionId;
    private LiveData<Session> session;

    @Inject
    public SessionViewModel(SessionRepository repository) {
        sessionRepository = repository;
    }

    public void init(long sessionId) {
        if (this.sessionId == null) {
            this.sessionId = sessionId;
            this.session = sessionRepository.getSession(sessionId);
        }
    }

    public Long getSessionId() {
        return sessionId;
    }

    public LiveData<Session> getSession() {
        return session;
    }

}
