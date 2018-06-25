package de.ringleinknorr.boulderapp.timeline;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;

import butterknife.BindDrawable;
import butterknife.ButterKnife;
import de.ringleinknorr.boulderapp.R;

public class RouteResultView extends android.support.v7.widget.AppCompatImageButton {

    private SessionRoute.Result result;
    private OnResultChangedListener onResultChangedListener = result -> {
    };

    @BindDrawable(R.drawable.ic_checkmark)
    Drawable iconSuccess;
    @BindDrawable(R.drawable.ic_cross)
    Drawable iconFailure;

    public RouteResultView(Context context) {
        this(context, null);
    }

    public RouteResultView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ButterKnife.bind(this);
        setImageDrawable(iconFailure);
        setBackground(null);
        setScaleType(ScaleType.FIT_CENTER);
        setTint();
        setResult(SessionRoute.Result.FAILURE);
        setOnClickListener(view -> {
            setResult(result == SessionRoute.Result.SUCCESS ? SessionRoute.Result.FAILURE : SessionRoute.Result.SUCCESS);
            onResultChangedListener.onResultChanged(result);
        });
    }

    private void setTint() {
        ColorStateList colorSelector = ResourcesCompat.getColorStateList(getResources(), R.color.colorAccent, getContext().getTheme());
        DrawableCompat.setTintList(iconSuccess, colorSelector);
        colorSelector = ResourcesCompat.getColorStateList(getResources(), R.color.colorRed, getContext().getTheme());
        DrawableCompat.setTintList(iconFailure, colorSelector);
    }

    public SessionRoute.Result getResult() {
        return result;
    }

    public void setResult(SessionRoute.Result result) {
        this.result = result;
        updateUI();
    }

    private void updateUI() {
        switch (result) {
            case SUCCESS:
                setAlpha(1f);
                setImageDrawable(iconSuccess);
                break;
            case FAILURE:
            default:
                setAlpha(.5f);
                setImageDrawable(iconFailure);
        }
    }

    public OnResultChangedListener getOnResultChangedListener() {
        return onResultChangedListener;
    }

    public void setOnResultChangedListener(@NonNull OnResultChangedListener onResultChangedListener) {
        this.onResultChangedListener = onResultChangedListener;
    }

    public interface OnResultChangedListener {
        void onResultChanged(SessionRoute.Result result);
    }

}
