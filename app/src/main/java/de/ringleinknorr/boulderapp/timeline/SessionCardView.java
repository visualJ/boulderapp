package de.ringleinknorr.boulderapp.timeline;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.ringleinknorr.boulderapp.R;

public class SessionCardView extends ConstraintLayout {

    @BindView(R.id.dayText)
    TextView dayText;
    @BindView(R.id.monthText)
    TextView monthText;
    @BindView(R.id.gymText)
    TextView gymText;
    @BindView(R.id.routesText)
    TextView routesText;
    @BindView(R.id.workoutsText)
    TextView workoutsText;

    public SessionCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_sessioncard, this);
        ButterKnife.bind(this);
    }

    public SessionCardView(Context context) {
        this(context, null);
    }
}
