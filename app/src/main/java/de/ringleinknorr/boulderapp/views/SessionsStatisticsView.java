package de.ringleinknorr.boulderapp.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import javax.annotation.Nullable;

import butterknife.ButterKnife;
import de.ringleinknorr.boulderapp.R;

public class SessionsStatisticsView extends ConstraintLayout {

    public SessionsStatisticsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_session_statistics, this);
        ButterKnife.bind(this);
        setLayoutParams(new Constraints.LayoutParams(Constraints.LayoutParams.MATCH_PARENT, Constraints.LayoutParams.WRAP_CONTENT));

    }
}
