package de.ringleinknorr.boulderapp.timeline;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import androidx.navigation.fragment.NavHostFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;
import de.ringleinknorr.boulderapp.R;
import de.ringleinknorr.boulderapp.ViewModelFactory;
import de.ringleinknorr.boulderapp.routes.RouteSearchFragment;

public class SessionFragment extends Fragment {

    public static final String ARG_SESSION_ID = "sessionId";

    @BindView(R.id.session_card)
    SessionCardView sessionCard;

    @BindView(R.id.session_route_list)
    RecyclerView sessionRouteList;

    @Inject
    ViewModelFactory<SessionViewModel> viewModelFactory;

    private SessionViewModel viewModel;
    private SessionRouteListAdapter adapter;

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
            sessionCard.setRoutes(session.getRoutes().size());
            adapter.setRoutes(session.getRoutes());
        });

        sessionRouteList.setHasFixedSize(true);
        sessionRouteList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SessionRouteListAdapter(new ArrayList<>());
        sessionRouteList.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @OnClick(R.id.add_fab)
    public void onAddFAB() {
        Bundle bundle = new Bundle();
        bundle.putLong(RouteSearchFragment.KEY_SESSION_ID, viewModel.getSessionId());
        NavHostFragment.findNavController(this).navigate(R.id.addRoute, bundle);
    }
}
