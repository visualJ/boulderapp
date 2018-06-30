package de.ringleinknorr.boulderapp.timeline;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
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
        setResult(SessionRoute.Result.FAILURE);
        setOnClickListener(view -> {
            setResult(result == SessionRoute.Result.SUCCESS ? SessionRoute.Result.FAILURE : SessionRoute.Result.SUCCESS);
            onResultChangedListener.onResultChanged(result);
        });
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
                setImageDrawable(iconSuccess);
                break;
            case FAILURE:
            default:
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
