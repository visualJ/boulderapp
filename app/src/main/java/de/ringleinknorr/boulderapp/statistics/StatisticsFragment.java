package de.ringleinknorr.boulderapp.statistics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import de.ringleinknorr.boulderapp.InjectableFragment;
import de.ringleinknorr.boulderapp.R;

public class StatisticsFragment extends InjectableFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}
