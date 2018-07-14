package de.ringleinknorr.boulderapp.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.util.Log;

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
        liveData.addSource(query, list -> liveData.postValue(list.get(0)) );
        return liveData;
    }

    public LiveData<Session> getPreviousSession(Session session) {
        MediatorLiveData<Session> previousSession = new MediatorLiveData<>();
        List<Session> orderedSession = box.query().order(Session_.date).build().find();

        int index = orderedSession.indexOf(session);
        if (index >= 1){
            previousSession.postValue( orderedSession.get(index-1) );
        }

        return previousSession;
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

            if(month == Calendar.JANUARY){
                return monthTemp == Calendar.DECEMBER && yearTemp == year-1;
            }else{
                return monthTemp == monthTemp-1 && yearTemp == year;
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
