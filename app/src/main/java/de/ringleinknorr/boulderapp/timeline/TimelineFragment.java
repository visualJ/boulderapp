package de.ringleinknorr.boulderapp.timeline;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;
import de.ringleinknorr.boulderapp.R;
import de.ringleinknorr.boulderapp.ViewModelFactory;

public class TimelineFragment extends Fragment {

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
        viewModel.getSessions().observe(this, sessions -> adapter.setSessions(sessions));

        sessionList.setHasFixedSize(true);
        sessionList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SessionListAdapter(new ArrayList<>(), Locale.getDefault());
        sessionList.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @OnClick(R.id.add_fab)
    public void onAddFAB() {
        NewSessionFragment newSessionFragment = new NewSessionFragment();
        newSessionFragment.setOnResultListener((gym, date) -> {
            Session session = new Session(new Date(2018, (int) (Math.random() * 11), (int) (Math.random() * 28)), gym);
            viewModel.addSession(session);
        });
        newSessionFragment.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), newSessionFragment.getTag());
    }

    @OnClick(R.id.remove_button)
    public void onRemoveButton() {
        viewModel.removeSessions();
    }
}
