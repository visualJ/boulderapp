package de.ringleinknorr.boulderapp.services;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.util.Log;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.ringleinknorr.boulderapp.models.Session;
import de.ringleinknorr.boulderapp.repositories.SessionRepository;

@Singleton
public class StatisticsService {

    private SessionRepository sessionRepository;

    @Inject
    public StatisticsService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public MediatorLiveData<Double> getPreviousMonthTrend(Session session) {
        LiveData<List<Session>> sessionsInMonth = sessionRepository.getSessionsFromPreviousMonth(session.getDate());
        MediatorLiveData<Double> trendLiveData = new MediatorLiveData<>();
        trendLiveData.addSource(sessionsInMonth, sessions -> {
            List<Session> sessionInMonthList = sessionsInMonth.getValue();
            int successfullRoutes = session.getSuccessfulSessionRoutes().size();
            double trend = 0;
            int completedRoutesInMonth = 0;
            if (sessionInMonthList != null) {
                if (sessionInMonthList.size() == 1) {
                    trend = 0;
                } else {
                    for (Session sessionTemp : sessionInMonthList) {
                        if (sessionTemp.getId() != session.getId())
                            completedRoutesInMonth += sessionTemp.getSuccessfulSessionRoutes().size();
                    }
                    double successRateMonth = sessionInMonthList.size() == 0 ? 0 : completedRoutesInMonth / (double) (sessionInMonthList.size() - 1);
                    trend = successRateMonth == 0 ? 100 * successfullRoutes : ((100 / successRateMonth) * successfullRoutes) - 100;
                }
            }
            trendLiveData.postValue(trend);
        });
        return trendLiveData;
    }

    public MediatorLiveData<Double> getPreviousSessionTrend(Session session){
        LiveData<Session> previousSessionLiveData = sessionRepository.getPreviousSession(session);
        MediatorLiveData<Double> trendLiveData = new MediatorLiveData<>();
        trendLiveData.addSource(previousSessionLiveData, previousSession -> {
            if(previousSession != null) {
                int previousSessionCompletedRoutes =  previousSession.getSuccessfulSessionRoutes().size();
                int completedRoutes = session.getSuccessfulSessionRoutes().size();
                double trend = previousSessionCompletedRoutes == 0 ? 100 * completedRoutes : ((100 / previousSessionCompletedRoutes) * completedRoutes) - 100;
                trendLiveData.postValue(trend);
            }
        });

        return trendLiveData;
    }

    public MediatorLiveData<List<Session>> getPreviousSessionsGraphData(Session session, int count){
        LiveData<List<Session>> previousSessionsLivaData = sessionRepository.getPreviousSessions(session, count);
        MediatorLiveData<List<Session>> graphLiveData = new MediatorLiveData<>();

        graphLiveData.addSource(previousSessionsLivaData, previousSessionsList -> {
            Collections.reverse(previousSessionsList);
            previousSessionsList.add(session);
            graphLiveData.postValue(previousSessionsList);
        });

        return graphLiveData;
    }

}
