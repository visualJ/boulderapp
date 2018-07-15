package de.ringleinknorr.boulderapp.views;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

/**
 * A transition animation between a {@link RouteCardView} and the route detail screen.
 */
public class RouteCardViewTransition extends ViewTransition<RouteCardView, ImageView> {

    public RouteCardViewTransition(RouteCardView source, ImageView destination, OnTransitionFinishedListener onTransitionFinishedListener, View... fade) {
        super(onTransitionFinishedListener, source, destination, fade);
    }

    @Override
    protected void prepareAnimation(Rect dest, Rect src) {
        Drawable drawable = source.getImage().getDrawable();
        destination.setImageDrawable(drawable);

        // GlobalVisibleRect can't be used here, since the image views height is not determined yet.
        // Instead, use the image aspect ratio to compute the height.
        float ratio = drawable.getIntrinsicHeight() / (float) drawable.getIntrinsicWidth();
        int height = (int) (destination.getMeasuredWidth() * ratio);
        int[] pos = new int[2];
        destination.getLocationInWindow(pos);

        destination.getGlobalVisibleRect(dest);
        source.getImage().getGlobalVisibleRect(src);
        src.offset(-pos[0], -pos[1]);
        dest.set(0, 0, destination.getMeasuredWidth(), height);
    }

}
