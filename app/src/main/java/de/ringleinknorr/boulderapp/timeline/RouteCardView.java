package de.ringleinknorr.boulderapp.timeline;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.ringleinknorr.boulderapp.R;

public class RouteCardView extends ConstraintLayout {

    @BindView(R.id.gym_text)
    TextView gymText;

    @BindView(R.id.image)
    ImageView image;

    public RouteCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_routecard, this);
        ButterKnife.bind(this);
        setLayoutParams(new Constraints.LayoutParams(-1, -2));
    }
}
