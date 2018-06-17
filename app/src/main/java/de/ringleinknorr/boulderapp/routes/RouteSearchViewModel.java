package de.ringleinknorr.boulderapp.routes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

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
    private ImageRepository imageRepository;
    private long sessionId;
    private LiveData<Session> session;
    private Bitmap gimSectorImage;
    private Gym selectedGym;

    @Inject
    public RouteSearchViewModel(RouteRepository routeRepository, SessionRepository sessionRepository, ImageRepository imageRepository) {
        this.routeRepository = routeRepository;
        this.sessionRepository = sessionRepository;
        this.imageRepository = imageRepository;
        routes = new MediatorLiveData<>();
    }

    public LiveData<List<Route>> getRoutes() {
        return routes;
    }

    public void queryRoutes(RouteSearchParameter routeSearchParameter) {
        routes.addSource(routeRepository.queryRoutes(routeSearchParameter), routeList -> routes.postValue(routeList));
    }

    public Bitmap getRouteImageForId(String routeImageId){
        return imageRepository.getImageForId(routeImageId);
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

    public Bitmap getGymSectorImage() {
        return gimSectorImage;
    }

    public void setGimSectorImage(Bitmap gimSectorImage) {
        this.gimSectorImage = gimSectorImage;
    }

    public Gym getSelectedGym() {
        return selectedGym;
    }

    public void setSelectedGym(Gym selectedGym) {
        this.selectedGym = selectedGym;
    }
}
