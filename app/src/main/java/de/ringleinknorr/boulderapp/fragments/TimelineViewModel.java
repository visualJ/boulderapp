package de.ringleinknorr.boulderapp.fragments;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import de.ringleinknorr.boulderapp.models.Session;
import de.ringleinknorr.boulderapp.repositories.SessionRepository;

/**
 * The view model for {@link TimelineFragment}.
 */
public class TimelineViewModel extends ViewModel {

    private LiveData<List<Session>> sessions;
    private SessionRepository sessionRepository;

    @Inject public TimelineViewModel(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
        sessions = sessionRepository.getAllSessions();
    }

    public LiveData<List<Session>> getSessions() {
        return sessions;
    }

    public void addSession(Session session) {
        sessionRepository.putSession(session);
    }

}
