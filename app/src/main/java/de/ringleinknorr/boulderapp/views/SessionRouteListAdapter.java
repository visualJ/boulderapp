package de.ringleinknorr.boulderapp.views;

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import de.ringleinknorr.boulderapp.models.SessionRoute;

public class SessionRouteListAdapter extends ItemListAdapter<SessionRoute, SessionRouteCardView> {

    private static final int EXTRA_BOTTOM_PADDING = 240;
    private OnResultChangedListener onResultChangedListener;

    public SessionRouteListAdapter(List<SessionRoute> routeList, @NonNull ImageProvider imageProvider, @NonNull OnResultChangedListener onResultChangedListener) {
        super(routeList);
        this.onResultChangedListener = onResultChangedListener;
        setImageProvider(imageProvider);
    }

    @Override
    public SessionRouteCardView onCreateView(@NonNull ViewGroup parent, int viewType) {
        return new SessionRouteCardView(parent.getContext());
    }

    @Override
    public void onBindView(@NonNull SessionRouteCardView view, int position, SessionRoute route) {
        String routeLevel = route.getRoute().getTarget().getRouteLevel().getTarget().getLevelName();
        view.getRouteLevelText().setText(routeLevel);
        view.setColor(route.getRoute().getTarget().getRouteLevel().getTarget().getLevelColor());
        ImageView routeImageView = view.getImage();
        getImageProvider().getImage(route.getRoute().getTarget().getImageId(), routeImageView);
        view.getResult().setResult(route.getResult());
        view.getResult().setOnResultChangedListener(result -> onResultChangedListener.onResultChanged(result, route));
        if (position == getItemCount() - 1) {
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), EXTRA_BOTTOM_PADDING);
        } else {
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), 0);
        }
    }

    public interface OnResultChangedListener {
        void onResultChanged(SessionRoute.Result result, SessionRoute route);
    }

}
