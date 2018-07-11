package de.ringleinknorr.boulderapp.services;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

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

    public MediatorLiveData<Double> getMonthlyTrendForSession(Session session) {
        LiveData<List<Session>> sessionsInMonth = sessionRepository.getSessionsInMonth(session.getDate());
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
}
