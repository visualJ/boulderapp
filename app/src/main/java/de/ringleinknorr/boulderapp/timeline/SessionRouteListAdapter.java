package de.ringleinknorr.boulderapp.timeline;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
        LiveData<Bitmap> liveDate = getImageProvider().getImage(route.getRoute().getTarget().getImageId());
        Observer<Bitmap> observer = new Observer<Bitmap>() {
            @Override
            public void onChanged(@Nullable Bitmap bitmap) {
                routeImageView.setImageBitmap(bitmap);
                liveDate.removeObserver(this);
            }
        };
        liveDate.observeForever(observer);
    }
}
