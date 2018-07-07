package de.ringleinknorr.boulderapp.routes;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.ringleinknorr.boulderapp.R;
import de.ringleinknorr.boulderapp.SelectableItemListAdapter;

public class RouteLevelView extends ConstraintLayout implements SelectableItemListAdapter.Selectable {

    @BindView(R.id.level_selection_view)
    CardView levelSelectionCardView;
    private boolean isSelected = false;
    private SelectableItemListAdapter.OnSelectedListener onSelectedListener = selected -> {
    };

    public RouteLevelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_route_level, this);
        ButterKnife.bind(this);
        setLayoutParams(new Constraints.LayoutParams(Constraints.LayoutParams.MATCH_PARENT, Constraints.LayoutParams.WRAP_CONTENT));

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
        updateUI();
        onSelectedListener.onSelected(isSelected);
    }

    private void updateUI() {
        if (isSelected) {
            levelSelectionCardView.setCardElevation(12);
            levelSelectionCardView.setScaleX(1.2f);
            levelSelectionCardView.setScaleY(1.2f);
            levelSelectionCardView.setAlpha(1f);
        } else {
            levelSelectionCardView.setCardElevation(4);
            levelSelectionCardView.setScaleX(1f);
            levelSelectionCardView.setScaleY(1f);
            levelSelectionCardView.setAlpha(0.6f);
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
