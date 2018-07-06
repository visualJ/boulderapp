package de.ringleinknorr.boulderapp.timeline;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.ringleinknorr.boulderapp.R;

public class SessionCardView extends ConstraintLayout {

    @BindView(R.id.dayText)
    TextView dayText;
    @BindView(R.id.monthText)
    TextView monthText;
    @BindView(R.id.gym_text)
    TextView gymText;
    @BindView(R.id.routesText)
    TextView routesText;
    @BindView(R.id.successful_routes)
    TextView successfulRoutes;
    @BindView(R.id.workoutsText)
    TextView workoutsText;
    @BindView(R.id.sessionTrendIcon)
    ImageView trendIcon;

    public TextView getDayText() {
        return dayText;
    }

    public TextView getMonthText() {
        return monthText;
    }

    public TextView getGymText() {
        return gymText;
    }

    public TextView getRoutesText() {
        return routesText;
    }

    public TextView getSuccessfulRoutes() {
        return successfulRoutes;
    }

    public TextView getWorkoutsText() {
        return workoutsText;
    }

    public CardView getCardView() {
        return cardView;
    }

    @BindView(R.id.card_view)

    CardView cardView;

    public SessionCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_sessioncard, this);
        ButterKnife.bind(this);
        setLayoutParams(new Constraints.LayoutParams(-1,-2));
        TypedArray attributes = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SessionCardView,
                0, 0);
        try {
            boolean docked = attributes.getBoolean(R.styleable.SessionCardView_docked, false);
            if (docked) {
                cardView.setRadius(0);
                cardView.setUseCompatPadding(false);
            }
        } finally {
            attributes.recycle();
        }
    }

    public void setDate(Date date, Locale locale) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        dayText.setText(day);
        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, locale);
        monthText.setText(String.valueOf(month.toUpperCase()));
    }

    public void setGym(String gym) {
        gymText.setText(gym);
    }

    public void setRoutes(int routes) {
        routesText.setText(String.valueOf(routes));
    }

    public void setSuccessfulRoutes(int routes) {
        successfulRoutes.setText(String.valueOf(routes));
    }

    public void setSessionTrend(double trend) {
        if (trend < 0) {
            trendIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_trending_down_black_24dp));
        } else {
            trendIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_trending_up_black_24dp));
        }
    }

    public void setWorkouts(int workouts) {
        workoutsText.setText(String.valueOf(workouts));
    }

    public SessionCardView(Context context) {
        this(context, null);
    }

    public void setData(SessionCardView other) {
        dayText.setText(other.dayText.getText());
        monthText.setText(other.monthText.getText());
        routesText.setText(other.routesText.getText());
        successfulRoutes.setText(other.successfulRoutes.getText());
        gymText.setText(other.gymText.getText());
    }
}
