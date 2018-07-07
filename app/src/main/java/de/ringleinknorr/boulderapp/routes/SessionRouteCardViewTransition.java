package de.ringleinknorr.boulderapp.routes;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;
import android.widget.ImageView;

import de.ringleinknorr.boulderapp.ViewTransition;
import de.ringleinknorr.boulderapp.timeline.SessionRouteCardView;

public class SessionRouteCardViewTransition extends ViewTransition<SessionRouteCardView, ImageView, View> {

    public SessionRouteCardViewTransition(SessionRouteCardView source, ImageView destination, OnTransitionFinishedListener onTransitionFinishedListener, View fade) {
        super(onTransitionFinishedListener, source, destination, fade);
    }

    @Override
    protected void prepareAnimation(Rect dest, Rect src) {
        final Point offset = new Point();
        destination.setImageDrawable(source.getImage().getDrawable());

        destination.getGlobalVisibleRect(dest, offset);
        source.getImage().getGlobalVisibleRect(src);
        src.offset(-offset.x, -offset.y);
        dest.offset(-offset.x, -offset.y);
    }

}
