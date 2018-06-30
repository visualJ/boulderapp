package de.ringleinknorr.boulderapp.timeline;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.ringleinknorr.boulderapp.ColorUtil;
import de.ringleinknorr.boulderapp.R;

public class SessionRouteCardView extends ConstraintLayout {
    @BindView(R.id.route_level_text)
    TextView routeLevelText;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.result)
    RouteResultView result;

    public SessionRouteCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_sessionroutecard, this);
        ButterKnife.bind(this);
        setLayoutParams(new Constraints.LayoutParams(Constraints.LayoutParams.MATCH_PARENT, Constraints.LayoutParams.WRAP_CONTENT));
    }

    public SessionRouteCardView(Context context) {
        this(context, null);
    }

    public void setColor(int color) {
        routeLevelText.setBackground(new ColorDrawable(color));
        routeLevelText.setTextColor(ColorUtil.getReadableTextColor(color));
    }

    public RouteResultView getResult() {
        return result;
    }

    public TextView getRouteLevelText() {
        return routeLevelText;
    }

    public ImageView getImage() {
        return image;
    }


}
