package de.ringleinknorr.boulderapp.views;

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import de.ringleinknorr.boulderapp.models.Route;

public class RouteListAdapter extends SelectableItemListAdapter<Route, RouteCardView> {

    public RouteListAdapter(List<Route> routeList, @NonNull ImageProvider imageProvider) {
        super(routeList);
        setImageProvider(imageProvider);
    }

    @Override
    public void onBindView(RouteCardView routeCardView, int position, Route route) {
        String routeLevel = route.getRouteLevel().getTarget().getLevelName();
        routeCardView.getRouteLevelText().setText(routeLevel);
        ImageView routeImageView = routeCardView.getImage();
        getImageProvider().getImage(route.getImageId(), routeImageView);
        routeCardView.setColor(route.getRouteLevel().getTarget().getLevelColor());
        super.onBindView(routeCardView, position, route);
    }

    @Override
    public RouteCardView onCreateView(@NonNull ViewGroup parent, int viewType) {
        return new RouteCardView(parent.getContext());
    }

}
