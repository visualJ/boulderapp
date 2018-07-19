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

/**
 * Contains methods for acquiring various statistic data
 */
@Singleton
public class StatisticsService {

    private SessionRepository sessionRepository;

    @Inject
    public StatisticsService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    /**
     * Return the ratio of the average count of completed routes per session from the previous month of the given session to the number of completed routes in the given session.
     *
     * @param session The session for which the trend should be returned
     * @return The trend towards the previous session of the previous month as LiveData Double
     */
    public MediatorLiveData<Double> getPreviousMonthTrend(Session session) {
        LiveData<List<Session>> previousMonthSessions = sessionRepository.getSessionsFromPreviousMonth(session.getDate());
        MediatorLiveData<Double> trendLiveData = new MediatorLiveData<>();
        trendLiveData.addSource(previousMonthSessions, sessions -> {
            List<Session> sessionInMonthList = previousMonthSessions.getValue();
            int completedRoutes = session.getSuccessfulSessionRoutes().size();
            int completedRoutesInMonth = 0;
            double trend = 0;
            double averageCompletedRoutesPreviousMonth;
            if (sessionInMonthList != null) {
                for (Session sessionTemp : sessionInMonthList) {
                    if (sessionTemp.getId() != session.getId())
                        completedRoutesInMonth += sessionTemp.getSuccessfulSessionRoutes().size();
                }
                averageCompletedRoutesPreviousMonth = sessionInMonthList.size() == 0 ? 0 : completedRoutesInMonth / (double) (sessionInMonthList.size());
                trend = averageCompletedRoutesPreviousMonth == 0 ? completedRoutes : (completedRoutes - averageCompletedRoutesPreviousMonth);
            }
            trendLiveData.postValue(trend);
        });
        return trendLiveData;
    }

    /**
     * Return the ratio of completed routes of the given session to the number of completed routes of the previous session.
     *
     * @param session The session for which the trend should be returned
     * @return The trend towards the previous session as LiveData Double
     */
    public MediatorLiveData<Double> getPreviousSessionTrend(Session session) {
        LiveData<Session> previousSessionLiveData = sessionRepository.getPreviousSession(session);
        MediatorLiveData<Double> trendLiveData = new MediatorLiveData<>();
        trendLiveData.addSource(previousSessionLiveData, previousSession -> {
            if (previousSession != null) {
                int previousSessionCompletedRoutes = previousSession.getSuccessfulSessionRoutes().size();
                int completedRoutes = session.getSuccessfulSessionRoutes().size();
                double trend = previousSessionCompletedRoutes == 0 ? completedRoutes : (completedRoutes - previousSessionCompletedRoutes);
                trendLiveData.postValue(trend);
            }
        });

        return trendLiveData;
    }

    /**
     * Return the sessions previous to the given session.
     *
     * @param session The session for which the previous sessions should be returned
     * @param count   The maximal number of previous sessions that should be considered
     * @return A list containing sessions previous to the given session as well as the given session itself
     */
    public MediatorLiveData<List<Session>> getPreviousSessionsGraphData(Session session, int count) {
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
