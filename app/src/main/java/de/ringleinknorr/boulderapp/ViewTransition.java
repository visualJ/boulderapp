package de.ringleinknorr.boulderapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.util.Arrays;
import java.util.List;

public abstract class ViewTransition<S extends View, D extends View> {
    protected S source;
    protected D destination;
    protected OnTransitionFinishedListener onTransitionFinishedListener;
    protected List<View> fade;

    public ViewTransition(OnTransitionFinishedListener onTransitionFinishedListener, S source, D destination, View... fade) {
        this.onTransitionFinishedListener = onTransitionFinishedListener;
        this.source = source;
        this.destination = destination;
        this.fade = Arrays.asList(fade);
    }

    protected abstract void prepareAnimation(Rect dest, Rect src);

    public void start() {
        final Rect dest = new Rect();
        final Rect src = new Rect();

        prepareAnimation(dest, src);

        final float scaleX = src.width() / (float) dest.width();
        final float scaleY = src.height() / (float) dest.height();

        destination.setPivotX(0f);
        destination.setPivotY(0f);
        source.setVisibility(View.INVISIBLE);
        destination.setVisibility(View.VISIBLE);

        AnimatorSet set = new AnimatorSet();
        AnimatorSet.Builder builder = set.play(ObjectAnimator.ofFloat(destination, View.X, src.left, dest.left))
                .with(ObjectAnimator.ofFloat(destination, View.Y, src.top, dest.top))
                .with(ObjectAnimator.ofFloat(destination, View.SCALE_X, scaleX, 1f))
                .with(ObjectAnimator.ofFloat(destination, View.SCALE_Y, scaleY, 1f));
        for (View fadeView : fade) {
            builder.with(ObjectAnimator.ofFloat(fadeView, View.ALPHA, 1f, 0f));
        }
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
