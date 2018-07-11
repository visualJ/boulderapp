package de.ringleinknorr.boulderapp.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.ringleinknorr.boulderapp.R;

public class RouteLevelView extends ConstraintLayout implements SelectableItemListAdapter.Selectable {

    public static final int SELECTED_ELEVATION = 12;
    public static final float SELECTED_SCALE = 1.2f;
    public static final float DESELECTED_SCALE = 1f;
    public static final float SELECTED_ALPHA = 1f;
    public static final int DESELECTED_ELEVATION = 4;
    public static final float DESELECTED_ALPHA = 0.6f;

    @BindView(R.id.level_selection_view)
    CardView levelSelectionCardView;

    @BindInt(android.R.integer.config_shortAnimTime)
    int mShortAnimationDuration;

    private boolean isSelected = false;
    private SelectableItemListAdapter.OnSelectedListener onSelectedListener = selected -> {
    };
    private OvershootInterpolator overshootInterpolator = new OvershootInterpolator(5f);
    private DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator(2f);

    public RouteLevelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_route_level, this);
        ButterKnife.bind(this);
        setLayoutParams(new Constraints.LayoutParams(Constraints.LayoutParams.WRAP_CONTENT, Constraints.LayoutParams.WRAP_CONTENT));

    }

    public RouteLevelView(Context context) {
        this(context, null);
    }

    public CardView getLevelSelectionCardView() {
        return levelSelectionCardView;
    }

    @OnClick(R.id.level_selection_view)
    public void onLevelSelection() {
        isSelected = !isSelected;
        animateUI();
        onSelectedListener.onSelected(isSelected);
    }

    private void animateUI() {
        if (isSelected) {
            levelSelectionCardView.setCardElevation(SELECTED_ELEVATION);
            levelSelectionCardView.animate().scaleX(SELECTED_SCALE).scaleY(SELECTED_SCALE).alpha(SELECTED_ALPHA).setDuration(mShortAnimationDuration).setInterpolator(overshootInterpolator);
        } else {
            levelSelectionCardView.setCardElevation(DESELECTED_ELEVATION);
            levelSelectionCardView.animate().scaleX(DESELECTED_SCALE).scaleY(DESELECTED_SCALE).alpha(DESELECTED_ALPHA).setDuration(mShortAnimationDuration).setInterpolator(decelerateInterpolator);
        }
    }

    private void updateUI() {
        if (isSelected) {
            levelSelectionCardView.setCardElevation(SELECTED_ELEVATION);
            levelSelectionCardView.setScaleX(SELECTED_SCALE);
            levelSelectionCardView.setScaleY(SELECTED_SCALE);
            levelSelectionCardView.setAlpha(SELECTED_ALPHA);
        } else {
            levelSelectionCardView.setCardElevation(DESELECTED_ELEVATION);
            levelSelectionCardView.setScaleX(DESELECTED_SCALE);
            levelSelectionCardView.setScaleY(DESELECTED_SCALE);
            levelSelectionCardView.setAlpha(DESELECTED_ALPHA);
        }
        invalidate();
    }

    @Override
    public void setOnSelectedListener(SelectableItemListAdapter.OnSelectedListener listener) {
        onSelectedListener = listener;
    }

    @Override
    public void setItemSelected(boolean selected) {
        this.isSelected = selected;
        updateUI();
    }


    @Override
    public void setSelectable(boolean selectable) {

    }
}
