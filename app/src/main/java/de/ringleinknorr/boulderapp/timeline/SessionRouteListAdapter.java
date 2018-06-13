package de.ringleinknorr.boulderapp.timeline;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import java.util.List;

import de.ringleinknorr.boulderapp.ItemListAdapter;
import de.ringleinknorr.boulderapp.routes.RouteCardView;

public class SessionRouteListAdapter extends ItemListAdapter<SessionRoute, RouteCardView> {

    public SessionRouteListAdapter(List<SessionRoute> routeList) {
        super(routeList);
    }

    @Override
    public RouteCardView onCreateView(@NonNull ViewGroup parent, int viewType) {
        return new RouteCardView(parent.getContext());
    }

    @Override
    public void onBindView(@NonNull RouteCardView view, int position) {
        String routeLevel = getItems().get(position).getRoute().getTarget().getLevel().name();
        view.getRouteLevelText().setText(routeLevel);
    }
}
