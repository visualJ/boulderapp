package de.ringleinknorr.boulderapp.routes;

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import de.ringleinknorr.boulderapp.SelectableItemListAdapter;

public class RouteListAdapter extends SelectableItemListAdapter<Route, RouteCardView> {

    public RouteListAdapter(List<Route> routeList, @NonNull ImageProvider imageProvider) {
        super(routeList);
        setImageProvider(imageProvider);
    }

    @Override
    public void onBindView(RouteCardView routeCardView, int position, Route route) {
        super.onBindView(routeCardView, position, route);
        String routeLevel = route.getLevel().name();
        routeCardView.getRouteLevelText().setText(routeLevel);
        ImageView routeImageView = routeCardView.getImage();
        getImageProvider().getImage(route.getImageId(), routeImageView);
    }

    @Override
    public RouteCardView onCreateView(@NonNull ViewGroup parent, int viewType) {
        return new RouteCardView(parent.getContext());
    }

}
