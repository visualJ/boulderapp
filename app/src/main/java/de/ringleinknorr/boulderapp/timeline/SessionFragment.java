package de.ringleinknorr.boulderapp.timeline;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import de.ringleinknorr.boulderapp.R;
import de.ringleinknorr.boulderapp.ViewModelFactory;

public class SessionFragment extends Fragment {

    public static final String ARG_SESSION_ID = "sessionId";

    @BindView(R.id.session_text)
    TextView sessionText;

    @BindView(R.id.session_card)
    SessionCardView sessionCard;

    @Inject
    ViewModelFactory<SessionViewModel> viewModelFactory;

    private SessionViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_session, container, false);
        ButterKnife.bind(this, view);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SessionViewModel.class);
        viewModel.init(Objects.requireNonNull(getArguments()).getLong(ARG_SESSION_ID));
        viewModel.getSession().observe(this, session -> {
            sessionCard.setDate(session.getDate(), Locale.getDefault());
            sessionCard.setGym(session.getGym().getTarget().getName());
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }
}
