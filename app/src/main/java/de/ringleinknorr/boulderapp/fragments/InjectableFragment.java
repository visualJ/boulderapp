package de.ringleinknorr.boulderapp.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.Objects;

import dagger.android.support.AndroidSupportInjection;

/**
 * A fragment base class that injects dependencies.
 */
public abstract class InjectableFragment extends Fragment {
    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    protected void setTitle(String title) {
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle(title);
    }
}
