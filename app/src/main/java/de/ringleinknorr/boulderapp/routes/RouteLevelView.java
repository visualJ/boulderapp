package de.ringleinknorr.boulderapp.routes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.ringleinknorr.boulderapp.R;
import de.ringleinknorr.boulderapp.SelectableItemListAdapter;

public class RouteLevelView extends ConstraintLayout implements SelectableItemListAdapter.Selectable {

    @BindView(R.id.level_text)
    TextView routeLevelText;

    @BindColor(R.color.cardview_dark_background)
    int darkGrey;
    @BindColor(R.color.cardview_light_background)
    int lightGrey;
    @BindColor(R.color.colorPrimary)
    int green;

    private boolean isSelected = false;
    private SelectableItemListAdapter.OnSelectedListener onSelectedListener = selected -> {
    };
    private Paint p;

    public RouteLevelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_route_level, this);
        ButterKnife.bind(this);
        setLayoutParams(new Constraints.LayoutParams(Constraints.LayoutParams.MATCH_PARENT, Constraints.LayoutParams.WRAP_CONTENT));
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        int offsetTextViewX = routeLevelText.getLeft();
        int offsetTextViewY  = routeLevelText.getTop();

        if (isSelected) {
            p.setStrokeWidth(8);
            p.setColor(green);
        } else {
            p.setStrokeWidth(4);
            p.setColor(darkGrey);
        }
        canvas.drawRect(offsetTextViewX, offsetTextViewY, routeLevelText.getMeasuredWidth()+offsetTextViewX, routeLevelText.getMeasuredWidth()+offsetTextViewY, p);
    }

    @OnClick(R.id.level_text)
    public void onLevelSelection() {
        isSelected = !isSelected;
        invalidate();
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
