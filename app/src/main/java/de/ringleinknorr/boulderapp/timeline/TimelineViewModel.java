package de.ringleinknorr.boulderapp.timeline;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimelineViewModel extends ViewModel {

    private List<Session> sessions;

    public TimelineViewModel() {
        if (sessions == null) {
            this.sessions = new ArrayList<>();
            sessions.add(new Session(0, new Date(2018,3,15)));
            sessions.add(new Session(1, new Date(2018,4,19)));
        }
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }
}
