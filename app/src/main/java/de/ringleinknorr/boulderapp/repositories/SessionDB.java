package de.ringleinknorr.boulderapp.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.ringleinknorr.boulderapp.models.Session;
import de.ringleinknorr.boulderapp.models.Session_;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.ObjectBoxLiveData;
import io.objectbox.query.QueryBuilder;

@Singleton
public class SessionDB {

    private Box<Session> box;

    @Inject
    public SessionDB(BoxStore boxStore) {
        this.box = boxStore.boxFor(Session.class);
    }

    public LiveData<List<Session>> getAllSessions() {
        return new ObjectBoxLiveData<>(box.query().order(Session_.date, QueryBuilder.DESCENDING).build());
    }

    public LiveData<Session> getSession(long sessionId) {
        MediatorLiveData<Session> liveData = new MediatorLiveData<>();
        LiveData<List<Session>> query = new ObjectBoxLiveData<>(box.query().equal(Session_.id, sessionId).build());
        liveData.addSource(query, list -> liveData.postValue(list != null && list.size() > 0 ? list.get(0) : null));
        return liveData;
    }

    public LiveData<Session> getPreviousSession(Session session) {
        MediatorLiveData<Session> previousSession = new MediatorLiveData<>();
        List<Session> orderedSessions = box.query().order(Session_.date).build().find();

        int index = orderedSessions.indexOf(session);
        if (index >= 1) {
            previousSession.postValue(orderedSessions.get(index - 1));
        }

        return previousSession;
    }

    public LiveData<List<Session>> getPreviousSessions(Session session, int count) {
        MediatorLiveData<List<Session>> previousSessionsLiveData = new MediatorLiveData<>();
        List<Session> orderedSessions = box.query().order(Session_.date).build().find();
        List<Session> previousSessions = new ArrayList<>();

        int index = orderedSessions.indexOf(session);
        for (int i = 1; i <= count; i++) {
            if (i <= index) {
                previousSessions.add(orderedSessions.get(index - i));
            }
        }
        previousSessionsLiveData.postValue(previousSessions);
        return previousSessionsLiveData;
    }

    public LiveData<List<Session>> getSessionsFromPreviousMonth(Date date) {
        QueryBuilder<Session> builder = box.query();

        builder.filter((session) -> {
            Calendar cal = Calendar.getInstance();
            cal.setTime(session.getDate());
            int monthTemp = cal.get(Calendar.MONTH);
            int yearTemp = cal.get(Calendar.YEAR);
            cal.setTime(date);
            int month = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);

            if (month == Calendar.JANUARY) {
                return monthTemp == Calendar.DECEMBER && yearTemp == year - 1;
            } else {
                return monthTemp == month - 1 && yearTemp == year;
            }

        });

        return new ObjectBoxLiveData<>(builder.build());
    }

    public long addSession(Session session) {
        return box.put(session);
    }

    public void removeAllSessions() {
        box.removeAll();
    }

    public void removeSession(Session session) {
        box.remove(session);
    }
}
