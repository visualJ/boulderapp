package de.ringleinknorr.boulderapp.routes;

import android.content.Context;
import android.graphics.Paint;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

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
        } else {
            levelSelectionCardView.setCardElevation(4);
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
