package de.ringleinknorr.boulderapp.views;

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

/**
 * Displays basic information about a session, like date and gym name.
 * Can be used as a standalone card (round corners, shadow) or docked by setting app:docked='true/false' in the xml.
 */
public class SessionCardView extends ConstraintLayout {

    @BindView(R.id.day_text)
    TextView dayText;
    @BindView(R.id.month_text)
    TextView monthText;
    @BindView(R.id.gym_text)
    TextView gymText;
    @BindView(R.id.routes_text)
    TextView routesText;
    @BindView(R.id.successful_routes)
    TextView successfulRoutes;
    @BindView(R.id.workouts_text)
    TextView workoutsText;
    @BindView(R.id.session_trend_icon)
    ImageView trendIcon;
    @BindView(R.id.session_trend_value)
    TextView trendValueTextView;
    @BindView(R.id.card_view)
    CardView cardView;

    public SessionCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_sessioncard, this);
        ButterKnife.bind(this);
        setLayoutParams(new Constraints.LayoutParams(Constraints.LayoutParams.MATCH_PARENT, Constraints.LayoutParams.WRAP_CONTENT));

        // get attributes from xml
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

    public SessionCardView(Context context) {
        this(context, null);
    }

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

    public void setSuccessfulRoutes(int routes) {
        successfulRoutes.setText(String.valueOf(routes));
    }

    public TextView getWorkoutsText() {
        return workoutsText;
    }

    public CardView getCardView() {
        return cardView;
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

    public void setSessionTrend(double trend) {
        if (trend < 0) {
            trendIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_trending_down_black_24dp));
        } else if (trend == 0) {
            trendIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_trending_flat_black_24dp));
        } else {
            trendIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_trending_up_black_24dp));
        }
        trendIcon.setVisibility(VISIBLE);
        trendValueTextView.setText(getResources().getString(R.string.trend_value_placeholder, (trend)));
    }

    public void setWorkouts(int workouts) {
        workoutsText.setText(String.valueOf(workouts));
    }

    public void setData(SessionCardView other) {
        dayText.setText(other.dayText.getText());
        monthText.setText(other.monthText.getText());
        routesText.setText(other.routesText.getText());
        successfulRoutes.setText(other.successfulRoutes.getText());
        gymText.setText(other.gymText.getText());
    }
}
