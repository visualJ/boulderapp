package de.ringleinknorr.boulderapp.routes;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.ringleinknorr.boulderapp.R;
import de.ringleinknorr.boulderapp.SelectableItemListAdapter;

public class RouteCardView extends ConstraintLayout implements SelectableItemListAdapter.Selectable {
    @BindView(R.id.route_level_text)
    TextView routeLevelText;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.selected_icon)
    ImageView selectedIcon;

    @BindInt(android.R.integer.config_shortAnimTime)
    int mShortAnimationDuration;

    private boolean itemSelected = false;
    private SelectableItemListAdapter.OnSelectedListener onSelectedListener = selected -> {
    };

    public RouteCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_routecard, this);
        ButterKnife.bind(this);
        setLayoutParams(new Constraints.LayoutParams(-1, -2));
        selectedIcon.setAlpha(0.2f);
        selectedIcon.setScaleX(0.8f);
        selectedIcon.setScaleY(0.8f);
        image.setOnClickListener(view -> setItemSelected(!itemSelected));
    }

    public RouteCardView(Context context) {
        this(context, null);
    }

    public TextView getRouteLevelText() {
        return routeLevelText;
    }

    public void setRouteLevelText(String level) {
        this.routeLevelText.setText(level);
    }

    public ImageView getImage() {
        return image;
    }


    @Override
    public void setOnSelectedListener(SelectableItemListAdapter.OnSelectedListener listener) {
        onSelectedListener = listener;
    }

    @Override
    public void setItemSelected(boolean selected) {
        this.itemSelected = selected;
        onSelectedListener.onSelected(selected);
        if (selected) {
            selectedIcon.animate()
                    .setDuration(mShortAnimationDuration)
                    .setInterpolator(new OvershootInterpolator(5))
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f);
        } else {
            selectedIcon.animate()
                    .setDuration(mShortAnimationDuration)
                    .setInterpolator(new AccelerateInterpolator(0.1f))
                    .alpha(0.2f)
                    .scaleX(0.8f)
                    .scaleY(0.8f);
        }
    }

    public boolean isItemSelected() {
        return itemSelected;
    }

}
