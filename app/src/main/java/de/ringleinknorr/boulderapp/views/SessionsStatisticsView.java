package de.ringleinknorr.boulderapp.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.ringleinknorr.boulderapp.R;

public class SessionsStatisticsView extends ConstraintLayout {

    @BindView(R.id.session_statistics_month_trend)
    TextView sessionStatisticsMonthTrend;
    @BindView(R.id.session_statistics_previous_session_trend)
    TextView sessionStatisticsPreviousSessionTrend;

    public SessionsStatisticsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_session_statistics, this);
        ButterKnife.bind(this);
        setLayoutParams(new Constraints.LayoutParams(Constraints.LayoutParams.MATCH_PARENT, Constraints.LayoutParams.WRAP_CONTENT));

    }

    public void setMonthlyTrend(double trend) {
        sessionStatisticsMonthTrend.setText(getResources().getString(R.string.trend_value, (trend)));
    }

    public void setPreviousSessionTrend(double trend){
        sessionStatisticsPreviousSessionTrend.setText(getResources().getString(R.string.trend_value, (trend)));
    }

}
