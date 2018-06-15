package de.ringleinknorr.boulderapp.timeline;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;
import de.ringleinknorr.boulderapp.InjectableFragment;
import de.ringleinknorr.boulderapp.R;
import de.ringleinknorr.boulderapp.ViewModelFactory;

public class TimelineFragment extends InjectableFragment {

    @BindView(R.id.session_list)
    RecyclerView sessionList;

    @Inject
    ViewModelFactory<TimelineViewModel> viewModelFactory;

    private TimelineViewModel viewModel;
    private SessionListAdapter adapter;

    public TimelineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        ButterKnife.bind(this, view);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TimelineViewModel.class);
        viewModel.getSessions().observe(this, sessions -> adapter.setItems(sessions));

        sessionList.setHasFixedSize(true);
        sessionList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SessionListAdapter(new ArrayList<>(), Locale.getDefault(), this::onSessionSelected);
        sessionList.setAdapter(adapter);

        setTitle("Timeline");

        return view;
    }

    @OnClick(R.id.add_fab)
    public void onAddFAB() {
        NewSessionFragment newSessionFragment = new NewSessionFragment();
        newSessionFragment.setOnResultListener((gym, date) -> {
            Session session = new Session(date, gym);
            viewModel.addSession(session);
        });
        newSessionFragment.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), newSessionFragment.getTag());
    }

    private void onSessionSelected(int position, Session session) {
        Bundle bundle = new Bundle();
        bundle.putLong(SessionFragment.ARG_SESSION_ID, session.getId());
        NavHostFragment.findNavController(this).navigate(R.id.selectSession, bundle);
    }
}
