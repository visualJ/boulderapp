package de.ringleinknorr.boulderapp.routes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import de.ringleinknorr.boulderapp.timeline.Session;
import de.ringleinknorr.boulderapp.timeline.SessionRepository;
import de.ringleinknorr.boulderapp.timeline.SessionRoute;

public class RouteSearchViewModel extends ViewModel {
    private MediatorLiveData<List<Route>> routes;
    private RouteRepository routeRepository;
    private SessionRepository sessionRepository;
    private long sessionId;
    private LiveData<Session> session;
    private MutableLiveData<Gym> selectedGym;
    private List<Integer> selectedRouteLevelPositions;
    private GymSector selectedGymSector;

    @Inject
    public RouteSearchViewModel(RouteRepository routeRepository, SessionRepository sessionRepository) {
        this.routeRepository = routeRepository;
        this.sessionRepository = sessionRepository;
        routes = new MediatorLiveData<>();
        selectedGym = new MutableLiveData<>();
    }

    public LiveData<List<Route>> getRoutes() {
        return routes;
    }

    public void queryRoutes(RouteSearchParameter routeSearchParameter) {
        routes.addSource(routeRepository.queryRoutes(routeSearchParameter), routeList -> routes.postValue(routeList));
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
        this.session = sessionRepository.getSession(sessionId);
    }

    public LiveData<Session> getSession() {
        return session;
    }

    public void addRoutesToSession(List<Integer> positions) {
        List<SessionRoute> sessionRoutes = new ArrayList<>();
        for (int position : positions) {
            sessionRoutes.add(new SessionRoute(routes.getValue().get(position), session.getValue()));
        }
        session.getValue().getRoutes().addAll(sessionRoutes);
        sessionRepository.putSession(session.getValue());
    }

    public LiveData<Gym> getSelectedGym() {
        return selectedGym;
    }

    public void setSelectedGym(Gym selectedGym) {
        this.selectedGym.postValue(selectedGym);
    }

    public GymSector getSelectedGymSector() {
        return selectedGymSector;
    }

    public void setSelectedGymSector(GymSector selectedGymSector) {
        this.selectedGymSector = selectedGymSector;
    }

    public List<Integer> getSelectedRouteLevelPositions() {
        return selectedRouteLevelPositions;
    }

    public void setSelectedRouteLevelPositions(List<Integer> selectedRouteLevelPositions) {
        this.selectedRouteLevelPositions = selectedRouteLevelPositions;
    }
}
