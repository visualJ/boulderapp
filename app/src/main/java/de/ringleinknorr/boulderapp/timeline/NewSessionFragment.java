package de.ringleinknorr.boulderapp.timeline;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.ringleinknorr.boulderapp.R;

public class NewSessionFragment extends BottomSheetDialogFragment {

    @BindView(R.id.gym_text)
    TextView gymText;

    @BindView(R.id.date_text)
    TextView dateText;

    @BindView(R.id.add_button)
    Button addButton;

    @BindView(R.id.cancel_button)
    Button cancelButton;

    private OnResultListener onResultListener;

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

    public OnResultListener getOnResultListener() {
        return onResultListener;
    }

    public void setOnResultListener(OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.fragment_new_session, null);
        dialog.setContentView(contentView, null);
        ButterKnife.bind(this, contentView);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
    }

    @OnClick(R.id.cancel_button)
    public void onCancelButton() {
        dismiss();
    }

    @OnClick(R.id.add_button)
    public void onAddButton() {
        onResultListener.onResult(0, new Date());
        dismiss();
    }

    interface OnResultListener {
        void onResult(long gymId, Date date);
    }

    static class Factory {

        @Inject
        public Factory() {
        }

        public NewSessionFragment create() {
            return new NewSessionFragment();
        }
    }

}
