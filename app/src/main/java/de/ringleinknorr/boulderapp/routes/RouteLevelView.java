package de.ringleinknorr.boulderapp.routes;

import android.content.Context;
import android.graphics.Paint;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.ringleinknorr.boulderapp.R;
import de.ringleinknorr.boulderapp.SelectableItemListAdapter;

public class RouteLevelView extends ConstraintLayout implements SelectableItemListAdapter.Selectable {

    @BindView(R.id.level_text)
    TextView routeLevelText;
    @BindView(R.id.border_layout)
    ConstraintLayout roundedBorder;

    private boolean isSelected = false;
    private SelectableItemListAdapter.OnSelectedListener onSelectedListener = selected -> {
    };
    private Paint p;

    public RouteLevelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_route_level, this);
        ButterKnife.bind(this);
        setLayoutParams(new Constraints.LayoutParams(Constraints.LayoutParams.MATCH_PARENT, Constraints.LayoutParams.WRAP_CONTENT));
        //this.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorLightGrey)));
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.STROKE);
    }

    @OnClick(R.id.level_text)
    public void onLevelSelection() {
        isSelected = !isSelected;
        updateUI();
        onSelectedListener.onSelected(isSelected);
    }

    private void updateUI() {
        if(isSelected){
            roundedBorder.setBackgroundResource(R.drawable.background_layout_dark);
        }else {
            roundedBorder.setBackgroundResource(R.drawable.background_layout_light);
        }
        invalidate();
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
        this.isSelected = selected;
        updateUI();
    }


    @Override
    public void setSelectable(boolean selectable) {

    }
}
