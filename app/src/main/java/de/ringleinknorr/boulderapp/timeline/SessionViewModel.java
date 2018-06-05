package de.ringleinknorr.boulderapp.timeline;

import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

public class SessionViewModel extends ViewModel {

    private SessionRepository sessionRepository;

    private Long sessionId;
    private Session session;

    @Inject
    public SessionViewModel(SessionRepository repository) {
        sessionRepository = repository;
    }

    public void init(long sessionId) {
        if (this.sessionId == null) {
            setSessionId(sessionId);
        }
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
        this.session = sessionRepository.getSession(sessionId);
    }

    public Session getSession() {
        return session;
    }
}
