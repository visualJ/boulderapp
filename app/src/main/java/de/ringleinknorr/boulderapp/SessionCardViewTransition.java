package de.ringleinknorr.boulderapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import de.ringleinknorr.boulderapp.timeline.SessionCardView;

public class SessionCardViewTransition {

    private SessionCardView source;
    private SessionCardView destination;
    private OnTransitionFinishedListener onTransitionFinishedListener;
    private View fade;

    public SessionCardViewTransition(SessionCardView source, SessionCardView destination, OnTransitionFinishedListener onTransitionFinishedListener, View fade) {
        this.source = source;
        this.destination = destination;
        this.onTransitionFinishedListener = onTransitionFinishedListener;
        this.fade = fade;
    }

    public void start() {
        final Rect dest = new Rect();
        final Rect src = new Rect();
        final Point offset = new Point();

        destination.getCardView().getGlobalVisibleRect(dest, offset);
        source.getCardView().getGlobalVisibleRect(src);
        src.offset(-offset.x, -offset.y);
        dest.offset(-offset.x, -offset.y);

        final float scaleX = src.width() / (float) dest.width();
        final float scaleY = src.height() / (float) dest.height();
        final float radius = source.getCardView().getRadius();
        Log.i(getClass().getSimpleName(), "start: " + scaleX);

        destination.setData(source);
        destination.setPivotX(0f);
        destination.setPivotY(0f);
        destination.getCardView().setUseCompatPadding(false);

        source.setVisibility(View.INVISIBLE);
        destination.setVisibility(View.VISIBLE);

        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(destination, View.X,
                        src.left, dest.left))
                .with(ObjectAnimator.ofFloat(destination, View.Y,
                        src.top, dest.top))
                .with(ObjectAnimator.ofFloat(destination, View.SCALE_X,
                        scaleX, 1f))
                .with(ObjectAnimator.ofFloat(destination, View.SCALE_Y,
                        scaleY, 1f))
                .with(ObjectAnimator.ofFloat(fade, View.ALPHA, 1f, 0f));
        set.setDuration(400).setInterpolator(new DecelerateInterpolator(3f));
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                onTransitionFinishedListener.onTransitionFinished();
            }
        });
        set.start();

    }

    public interface OnTransitionFinishedListener {
        void onTransitionFinished();
    }

}
