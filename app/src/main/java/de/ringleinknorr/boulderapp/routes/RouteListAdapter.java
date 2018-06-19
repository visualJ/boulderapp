package de.ringleinknorr.boulderapp.routes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

        LiveData<Bitmap> liveDate = getImageProvider().getImage(route.getImageId());

        Observer<Bitmap> observer = new Observer<Bitmap>() {
            @Override
            public void onChanged(@Nullable Bitmap bitmap) {
                routeImageView.setImageBitmap(bitmap);
                liveDate.removeObserver(this);
            }
        };
        liveDate.observeForever(observer);
    }

    @Override
    public RouteCardView onCreateView(@NonNull ViewGroup parent, int viewType) {
        return new RouteCardView(parent.getContext());
    }

}
