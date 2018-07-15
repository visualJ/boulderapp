package de.ringleinknorr.boulderapp.fragments;

import android.content.Context;
import android.support.design.widget.BottomSheetDialogFragment;

import dagger.android.support.AndroidSupportInjection;

/**
 * A bottom sheet fragment base class that injects dependencies.
 */
public abstract class InjectableDialogBottomSheetFragment extends BottomSheetDialogFragment {
    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }
}
