package de.ringleinknorr.boulderapp.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.util.Arrays;
import java.util.List;

/**
 * A generic transition for views. A destination view is transformed from the bounds
 * of a source view to its original bounds. At the beginning of the transition, the source
 * view is hidden. During the animation, some additional view may be faded.
 *
 * @param <S> The source view type. This is where the transition starts.
 * @param <D> The desstination view type. This is transformed during the transition.
 */
public abstract class ViewTransition<S extends View, D extends View> {
    protected S source;
    protected D destination;
    protected OnTransitionFinishedListener onTransitionFinishedListener;
    protected List<View> fade;

    /**
     * Create a new Transition
     * @param onTransitionFinishedListener The listener to be called when the transition animation finishes
     * @param source The source view to start the animation from.
     * @param destination The destination view to transform during the animation.
     * @param fade Optional views to fade during the transition.
     */
    public ViewTransition(OnTransitionFinishedListener onTransitionFinishedListener, S source, D destination, View... fade) {
        this.onTransitionFinishedListener = onTransitionFinishedListener;
        this.source = source;
        this.destination = destination;
        this.fade = Arrays.asList(fade);
    }

    /**
     * Prepare the animation by at least setting source and target bounds. Additionally any other
     * preparations like setting view data can be made here.
     * @param dest
     * @param src
     */
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
