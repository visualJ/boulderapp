package de.ringleinknorr.boulderapp.timeline;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;
import de.ringleinknorr.boulderapp.R;
import de.ringleinknorr.boulderapp.ViewModelFactory;

public class TimelineFragment extends Fragment {

    @BindView(R.id.sessionList)
    RecyclerView sessionList;

    @Inject
    ViewModelFactory<TimelineViewModel> viewModelFactory;

    private TimelineViewModel viewModel;
    private SessionListAdapter adapter;

    public TimelineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        ButterKnife.bind(this, view);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TimelineViewModel.class);
        viewModel.init();
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

    @OnClick(R.id.addButton)
    public void onAddButton() {
        viewModel.addSession();
    }

    @OnClick(R.id.removeButton)
    public void onRemoveButton() {
        viewModel.removeSessions();
    }
}
