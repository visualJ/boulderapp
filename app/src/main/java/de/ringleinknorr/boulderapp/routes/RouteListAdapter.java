package de.ringleinknorr.boulderapp.routes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import de.ringleinknorr.boulderapp.ItemListAdapter;

public class RouteListAdapter extends ItemListAdapter<Route, RouteCardView> {

    public RouteListAdapter(List<Route> routeList) {
        super(routeList);
    }

    @Override
    public void onBindView(RouteCardView routeCardView, int position, Route route) {
        String routeLevel = route.getLevel().name();
        routeCardView.getRouteLevelText().setText(routeLevel);
    }

    @Override
    public RouteCardView onCreateView(@NonNull ViewGroup parent, int viewType) {
        return new RouteCardView(parent.getContext());
    }
}
