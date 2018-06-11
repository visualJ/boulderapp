package de.ringleinknorr.boulderapp.timeline;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import de.ringleinknorr.boulderapp.routes.Gym;
import de.ringleinknorr.boulderapp.routes.Route;

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

    public void addRoute() {
        session.getValue().getRoutes().add(new SessionRoute(new Route(Route.Level.LEICHT, new Gym("Wiesbadener Nordwand")), session.getValue()));
        sessionRepository.putSession(session.getValue());
    }
}
