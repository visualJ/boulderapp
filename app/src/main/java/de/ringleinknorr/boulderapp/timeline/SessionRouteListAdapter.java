package de.ringleinknorr.boulderapp.timeline;

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import de.ringleinknorr.boulderapp.ItemListAdapter;

public class SessionRouteListAdapter extends ItemListAdapter<SessionRoute, SessionRouteCardView> {

    public SessionRouteListAdapter(List<SessionRoute> routeList, @NonNull ImageProvider imageProvider) {
        super(routeList);
        setImageProvider(imageProvider);
    }

    @Override
    public SessionRouteCardView onCreateView(@NonNull ViewGroup parent, int viewType) {
        return new SessionRouteCardView(parent.getContext());
    }

    @Override
    public void onBindView(@NonNull SessionRouteCardView view, int position, SessionRoute route) {
        String routeLevel = route.getRoute().getTarget().getLevel().name();
        view.getRouteLevelText().setText(routeLevel);
        ImageView routeImageView = view.getImage();
        getImageProvider().getImage(route.getRoute().getTarget().getImageId(), routeImageView);
    }
}
