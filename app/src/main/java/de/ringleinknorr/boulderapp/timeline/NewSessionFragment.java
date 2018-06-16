package de.ringleinknorr.boulderapp.timeline;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.text.format.DateUtils;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.ringleinknorr.boulderapp.InjectableDialogBottomSheetFragment;
import de.ringleinknorr.boulderapp.R;
import de.ringleinknorr.boulderapp.routes.Gym;
import de.ringleinknorr.boulderapp.routes.GymRepository;

public class NewSessionFragment extends InjectableDialogBottomSheetFragment {

    private final String LAST_GYM_KEY = "last_gym";

    @BindView(R.id.gym_text)
    AutoCompleteTextView gymText;

    @BindView(R.id.date_text)
    TextView dateText;

    @BindView(R.id.search_button)
    Button addButton;

    @BindView(R.id.calendar)
    CalendarView calendar;

    @BindInt(android.R.integer.config_shortAnimTime)
    int mShortAnimationDuration;
    @Inject
    GymRepository gymRepository;
    private OnResultListener onResultListener;
    private ArrayAdapter<String> gymAdapter;
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };
    private View contentView;
    private Calendar selectedDate;
    private SharedPreferences preferences;
    private BottomSheetBehavior behavior;

    public OnResultListener getOnResultListener() {
        return onResultListener;
    }

    public void setOnResultListener(OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
    }

    @SuppressLint({"RestrictedApi"})
    @SuppressWarnings("unchecked")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        contentView = View.inflate(getContext(), R.layout.fragment_new_session, null);
        dialog.setContentView(contentView, null);
        ButterKnife.bind(this, contentView);

        preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("prefs", Context.MODE_PRIVATE);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        behavior = (BottomSheetBehavior) params.getBehavior();

        if (behavior != null) {
            behavior.setBottomSheetCallback(mBottomSheetBehaviorCallback);
            behavior.setSkipCollapsed(true);
            behavior.setPeekHeight(Integer.MAX_VALUE);
        }

        gymAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                android.R.layout.simple_dropdown_item_1line, new ArrayList<>());
        gymText.setAdapter(gymAdapter);
        gymRepository.getAllGymNames().observe(this, names -> {
            gymAdapter.clear();
            if (names != null) {
                gymAdapter.addAll(names);
            }
        });
        gymText.setText(preferences.getString(LAST_GYM_KEY, ""));

        calendar.setVisibility(View.GONE);
        calendar.setAlpha(0);
        calendar.setTranslationY(200);
        setDate(Calendar.getInstance());
        calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setDate(cal);
        });
    }

    private void setDate(Calendar cal) {
        selectedDate = cal;
        String date = DateUtils.formatDateTime(getContext(), cal.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE + DateUtils.FORMAT_SHOW_YEAR);
        dateText.setText(date);
    }

    @OnClick(R.id.date_text)
    public void onDateClicked() {
        if (calendar.getVisibility() == View.VISIBLE) {
            hideCalendar();
        } else {
            showCalendar();
        }
        contentView.requestLayout();
    }

    private void showCalendar() {
        calendar.setVisibility(View.VISIBLE);
        calendar.animate()
                .alpha(1)
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(2))
                .setDuration(mShortAnimationDuration)
                .setListener(null);
    }

    private void hideCalendar() {
        calendar.animate()
                .alpha(0)
                .translationY(200)
                .setInterpolator(new AccelerateInterpolator(1))
                .setDuration(mShortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        calendar.setVisibility(View.GONE);
                    }
                });
    }

    @OnClick(R.id.search_button)
    public void onAddButton() {
        Gym gym = gymRepository.getGymWithName(String.valueOf(gymText.getText()));
        if (gym != null) {
            preferences.edit().putString(LAST_GYM_KEY, String.valueOf(gymText.getText())).apply();
            onResultListener.onResult(gym, selectedDate.getTime());
            dismiss();
        } else {
            Toast.makeText(getContext(), R.string.gym_must_be_selected_first, Toast.LENGTH_SHORT).show();
        }
    }

    interface OnResultListener {
        void onResult(Gym gym, Date date);
    }

}
