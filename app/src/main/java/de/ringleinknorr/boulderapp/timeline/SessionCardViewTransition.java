package de.ringleinknorr.boulderapp.timeline;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

import de.ringleinknorr.boulderapp.ViewTransition;

public class SessionCardViewTransition extends ViewTransition<SessionCardView, SessionCardView> {

    public SessionCardViewTransition(SessionCardView source, SessionCardView destination, OnTransitionFinishedListener onTransitionFinishedListener, View fade) {
        super(onTransitionFinishedListener, source, destination, fade);
    }

    @Override
    protected void prepareAnimation(Rect dest, Rect src) {
        final Point offset = new Point();

        destination.getCardView().getGlobalVisibleRect(dest, offset);
        source.getCardView().getGlobalVisibleRect(src);
        src.offset(-offset.x, -offset.y);
        dest.offset(-offset.x, -offset.y);
        src.set(src.left - 2, src.top - 2, src.right - 2, src.bottom - 2);

        destination.setData(source);
        destination.getCardView().setUseCompatPadding(false);
    }

}
