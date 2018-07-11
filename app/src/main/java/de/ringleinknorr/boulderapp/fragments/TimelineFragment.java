package de.ringleinknorr.boulderapp.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import androidx.navigation.fragment.NavHostFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.ringleinknorr.boulderapp.R;
import de.ringleinknorr.boulderapp.dependencyinjection.DIViewModelFactory;
import de.ringleinknorr.boulderapp.models.Session;
import de.ringleinknorr.boulderapp.repositories.SessionRepository;
import de.ringleinknorr.boulderapp.repositories.SessionRouteRepository;
import de.ringleinknorr.boulderapp.views.PlaceholderRecyclerView;
import de.ringleinknorr.boulderapp.views.SessionCardView;
import de.ringleinknorr.boulderapp.views.SessionCardViewTransition;
import de.ringleinknorr.boulderapp.views.SessionListAdapter;
import de.ringleinknorr.boulderapp.views.SwypeItemTouchHelper;

public class TimelineFragment extends InjectableFragment {

    @BindView(R.id.session_list)
    PlaceholderRecyclerView sessionList;

    @BindView(R.id.transition_session_card)
    SessionCardView transitionSessionCard;

    @Inject
    DIViewModelFactory<TimelineViewModel> viewModelFactory;

    @Inject
    SessionRepository sessionRepository;

    @Inject
    SessionRouteRepository sessionRouteRepository;

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
        adapter = new SessionListAdapter(getContext(), new ArrayList<>(), Locale.getDefault(), (position, item, targetView) -> new SessionCardViewTransition(targetView, transitionSessionCard, () -> navigateToSession(item), sessionList).start());
        sessionList.setAdapter(adapter);
        SwypeItemTouchHelper swypeItemTouchHelper = new SwypeItemTouchHelper(position -> {
            Session session = adapter.getItems().get(position);
            sessionRepository.removeSession(session);
            Snackbar.make(Objects.requireNonNull(getView()), R.string.session_removed_snackbar, Snackbar.LENGTH_LONG).setAction(R.string.session_removed_snackbar_undo, view1 -> {
                sessionRouteRepository.putSessionRoutes(session.getRoutes());
                sessionRepository.putSession(session);
            }).show();
        });
        swypeItemTouchHelper.attachToRecyclerView(sessionList);

        setTitle(getString(R.string.timeline_title));

        return view;
    }

    @OnClick(R.id.add_fab)
    public void onAddFAB() {
        NewSessionFragment newSessionFragment = new NewSessionFragment();
        newSessionFragment.setOnResultListener((gym, date) -> {
            Session session = new Session(date, gym);
            viewModel.addSession(session);
            navigateToSession(session);
        });
        newSessionFragment.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), newSessionFragment.getTag());
    }

    private void navigateToSession(Session session) {
        Bundle bundle = new Bundle();
        bundle.putLong(SessionFragment.ARG_SESSION_ID, session.getId());
        NavHostFragment.findNavController(this).navigate(R.id.selectSession, bundle);
    }
}
