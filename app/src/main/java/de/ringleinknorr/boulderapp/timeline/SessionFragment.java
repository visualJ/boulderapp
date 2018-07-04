package de.ringleinknorr.boulderapp.timeline;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
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
import de.ringleinknorr.boulderapp.InjectableFragment;
import de.ringleinknorr.boulderapp.R;
import de.ringleinknorr.boulderapp.ViewModelFactory;
import de.ringleinknorr.boulderapp.routes.ImageRepository;
import de.ringleinknorr.boulderapp.routes.RouteSearchFragment;

public class SessionFragment extends InjectableFragment {

    public static final String ARG_SESSION_ID = "sessionId";

    @BindView(R.id.session_card)
    SessionCardView sessionCard;

    @BindView(R.id.session_route_list)
    RecyclerView sessionRouteList;

    @Inject
    ImageRepository imageRepository;
    @Inject
    SessionRouteRepository sessionRouteRepository;
    @Inject
    SessionRepository sessionRepository;
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
            sessionCard.setSuccessfulRoutes(session.getSuccessfulSessionRoutes().size());
            adapter.setItems(session.getRoutes());
        });

        sessionRouteList.setHasFixedSize(true);
        adapter = new SessionRouteListAdapter(new ArrayList<>(), imageRepository::loadImage, (result, route) -> {
            route.setResult(result);
            sessionRouteRepository.putSessionRoute(route);
            sessionRepository.putSession(route.getSession().getTarget());
        });
        sessionRouteList.setAdapter(adapter);
        SwypeItemTouchHelper swypeItemTouchHelper = new SwypeItemTouchHelper(position -> {
            SessionRoute route = adapter.getItems().get(position);
            viewModel.getSession().getValue().getRoutes().remove(route);
            sessionRepository.putSession(viewModel.getSession().getValue());
            Snackbar.make(Objects.requireNonNull(getView()), R.string.remove_route_snackbar, Snackbar.LENGTH_LONG).setAction(R.string.remove_route_snackbar_undo, view1 -> {
                viewModel.getSession().getValue().getRoutes().add(route);
                sessionRouteRepository.putSessionRoute(route);
                sessionRepository.putSession(viewModel.getSession().getValue());
            }).show();
        });
        swypeItemTouchHelper.attachToRecyclerView(sessionRouteList);

        setTitle(getString(R.string.session_title));

        return view;
    }

    @OnClick(R.id.add_fab)
    public void onAddFAB() {
        Bundle bundle = new Bundle();
        bundle.putLong(RouteSearchFragment.KEY_SESSION_ID, viewModel.getSessionId());
        NavHostFragment.findNavController(this).navigate(R.id.addRoute, bundle);
    }
}
