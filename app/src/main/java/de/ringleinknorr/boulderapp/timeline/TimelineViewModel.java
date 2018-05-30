package de.ringleinknorr.boulderapp.timeline;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

public class TimelineViewModel extends ViewModel {

    private LiveData<List<Session>> sessions;
    private SessionRepository sessionRepository;

    public TimelineViewModel() {
        if (sessions == null) {
            sessionRepository = new SessionRepository();
            reloadSessions();
        }
    }

    public LiveData<List<Session>> reloadSessions() {
        sessions = sessionRepository.getAllSessions();
        return sessions;
    }

    public LiveData<List<Session>> getSessions() {
        return sessions;
    }

}
