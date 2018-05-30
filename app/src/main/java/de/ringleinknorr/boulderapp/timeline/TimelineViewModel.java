package de.ringleinknorr.boulderapp.timeline;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

public class TimelineViewModel extends ViewModel {

    private LiveData<List<Session>> sessions;
    private SessionRepository sessionRepository;

    public TimelineViewModel() {
    }

    public void init(SessionRepository sessionRepository) {
        if (sessions == null) {
            this.sessionRepository = sessionRepository;
            sessions = sessionRepository.getAllSessions();
        }
    }

    public LiveData<List<Session>> getSessions() {
        return sessions;
    }

    public void addSession() {
        sessionRepository.addSession();
    }

    public void removeSessions() {
        sessionRepository.removeAllSessions();
    }

}
