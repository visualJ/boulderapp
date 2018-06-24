package de.ringleinknorr.boulderapp.routes;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.ringleinknorr.boulderapp.R;
import de.ringleinknorr.boulderapp.SelectableItemListAdapter;

public class RouteLevelView extends ConstraintLayout implements SelectableItemListAdapter.Selectable {

    @BindView(R.id.level_text)
    TextView routeLevelText;

    private boolean isSelected = false;
    private SelectableItemListAdapter.OnSelectedListener onSelectedListener = selected -> {
    };

    public RouteLevelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_route_level, this);
        ButterKnife.bind(this);
        setLayoutParams(new Constraints.LayoutParams(Constraints.LayoutParams.MATCH_PARENT, Constraints.LayoutParams.WRAP_CONTENT));
    }

    @OnClick(R.id.level_text)
    public void onLevelSelection(){
        isSelected = !isSelected;
        onSelectedListener.onSelected(isSelected);
    }

    public RouteLevelView(Context context) {
        this(context, null);
    }

    public TextView getRouteLevelSelectionButton() {
        return routeLevelText;
    }

    @Override
    public void setOnSelectedListener(SelectableItemListAdapter.OnSelectedListener listener) {
        onSelectedListener = listener;
    }

    @Override
    public void setItemSelected(boolean selected) {

    }


    @Override
    public void setSelectable(boolean selectable) {

    }
}
