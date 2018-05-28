package de.ringleinknorr.boulderapp.timeline;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.ringleinknorr.boulderapp.R;

public class TimelineFragment extends Fragment {

    @BindView(R.id.sessionList)
    RecyclerView sessionList;
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
        viewModel = ViewModelProviders.of(this).get(TimelineViewModel.class);
        sessionList.setHasFixedSize(true);
        sessionList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SessionListAdapter(viewModel.getSessions(), Locale.getDefault());
        sessionList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }
}
