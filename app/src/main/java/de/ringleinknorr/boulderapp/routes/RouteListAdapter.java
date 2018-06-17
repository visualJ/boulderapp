package de.ringleinknorr.boulderapp.routes;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import de.ringleinknorr.boulderapp.SelectableItemListAdapter;

public class RouteListAdapter extends SelectableItemListAdapter<Route, RouteCardView> {
    private RouteSearchViewModel viewModel;
    public RouteListAdapter(List<Route> routeList, RouteSearchViewModel viewModel) {
        super(routeList);
        this.viewModel = viewModel;
    }

    @Override
    public void onBindView(RouteCardView routeCardView, int position, Route route) {
        super.onBindView(routeCardView, position, route);
        String routeLevel = route.getLevel().name();
        routeCardView.getRouteLevelText().setText(routeLevel);

        ImageView routeImageView = routeCardView.getImage();
        routeImageView.setImageBitmap(viewModel.getRouteImageForId(route.getImageId()));

    }

    @Override
    public RouteCardView onCreateView(@NonNull ViewGroup parent, int viewType) {
        return new RouteCardView(parent.getContext());
    }
}
