package de.ringleinknorr.boulderapp.routes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import de.ringleinknorr.boulderapp.ItemListAdapter;
import de.ringleinknorr.boulderapp.SelectableItemListAdapter;

public class RouteListAdapter extends SelectableItemListAdapter<Route, RouteCardView> {

    public RouteListAdapter(List<Route> routeList) {
        super(routeList);
    }

    @Override
    public void onBindView(RouteCardView routeCardView, int position, Route route) {
        super.onBindView(routeCardView, position, route);
        String routeLevel = route.getLevel().name();
        routeCardView.getRouteLevelText().setText(routeLevel);
    }

    @Override
    public RouteCardView onCreateView(@NonNull ViewGroup parent, int viewType) {
        return new RouteCardView(parent.getContext());
    }
}
