package de.ringleinknorr.boulderapp.timeline;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SessionRepository {

    public LiveData<List<Session>> getAllSessions() {
        MutableLiveData<List<Session>> data = new MutableLiveData<>();
        List<Session> sessions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            sessions.add(new Session(i, new Date(2018, (int) (Math.random() * 11), (int) (Math.random() * 28))));
        }
        data.postValue(sessions);
        return data;
    }

}
