package de.ringleinknorr.boulderapp.timeline;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import java.util.List;

import de.ringleinknorr.boulderapp.ItemListAdapter;
import de.ringleinknorr.boulderapp.routes.RouteCardView;

public class SessionRouteListAdapter extends ItemListAdapter<SessionRoute, SessionRouteCardView> {

    public SessionRouteListAdapter(List<SessionRoute> routeList) {
        super(routeList);
    }

    @Override
    public SessionRouteCardView onCreateView(@NonNull ViewGroup parent, int viewType) {
        return new SessionRouteCardView(parent.getContext());
    }

    @Override
    public void onBindView(@NonNull SessionRouteCardView view, int position, SessionRoute route) {
        String routeLevel = route.getRoute().getTarget().getLevel().name();
        view.getRouteLevelText().setText(routeLevel);
    }
}
