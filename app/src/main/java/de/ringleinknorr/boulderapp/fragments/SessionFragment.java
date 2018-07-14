package de.ringleinknorr.boulderapp.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import androidx.navigation.fragment.NavHostFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.ringleinknorr.boulderapp.R;
import de.ringleinknorr.boulderapp.dependencyinjection.DIViewModelFactory;
import de.ringleinknorr.boulderapp.models.Route;
import de.ringleinknorr.boulderapp.models.SessionRoute;
import de.ringleinknorr.boulderapp.repositories.ImageRepository;
import de.ringleinknorr.boulderapp.repositories.SessionRepository;
import de.ringleinknorr.boulderapp.repositories.SessionRouteRepository;
import de.ringleinknorr.boulderapp.services.StatisticsService;
import de.ringleinknorr.boulderapp.views.PlaceholderRecyclerView;
import de.ringleinknorr.boulderapp.views.SessionCardView;
import de.ringleinknorr.boulderapp.views.SessionRouteCardViewTransition;
import de.ringleinknorr.boulderapp.views.SessionRouteListAdapter;
import de.ringleinknorr.boulderapp.views.SessionsStatisticsView;
import de.ringleinknorr.boulderapp.views.SwypeItemTouchHelper;
import io.objectbox.relation.ToMany;

public class SessionFragment extends InjectableFragment {

    public static final String ARG_SESSION_ID = "sessionId";

    @BindView(R.id.session_card)
    SessionCardView sessionCard;

    @BindView(R.id.session_route_list)
    PlaceholderRecyclerView sessionRouteList;

    @BindView(R.id.route_transition_image)
    ImageView routeTransitionImage;

    @BindView(R.id.icon_statistic_expander)
    ImageView statisticExpander;

    @BindView(R.id.session_statistics)
    SessionsStatisticsView sessionStatisticView;

    @Inject
    ImageRepository imageRepository;
    @Inject
    SessionRouteRepository sessionRouteRepository;
    @Inject
    SessionRepository sessionRepository;
    @Inject
    DIViewModelFactory<SessionViewModel> viewModelFactory;
    @Inject
    StatisticsService statisticsService;

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
            statisticsService.getMonthlyTrendForSession(session).observe(this, sessionCard::setSessionTrend);
            ToMany<SessionRoute> routes = session.getRoutes();
            Collections.sort(routes);
            adapter.setItems(routes);
        });

        sessionRouteList.setHasFixedSize(true);
        adapter = new SessionRouteListAdapter(new ArrayList<>(), imageRepository::loadThumbnail, (result, route) -> {
            route.setResult(result);
            sessionRouteRepository.putSessionRoute(route);
            sessionRepository.putSession(route.getSession().getTarget());
        });
        sessionRouteList.setAdapter(adapter);
        adapter.setOnItemClickListener((position, item, view1) -> new SessionRouteCardViewTransition(view1, routeTransitionImage, () -> navigateToRoute(item.getRoute().getTarget()), sessionRouteList).start());
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

    private void navigateToRoute(Route route) {
        // fetch and cache the full size image in the background while showing the transition
        imageRepository.fetchImage(route.getImageId());
        Bundle bundle = new Bundle();
        bundle.putLong(RouteFragment.ARG_ROUTE_ID, route.getId());
        NavHostFragment.findNavController(this).navigate(R.id.showRoute, bundle);
    }

    @OnClick(R.id.add_fab)
    public void onAddFAB() {
        Bundle bundle = new Bundle();
        bundle.putLong(RouteSearchFragment.KEY_SESSION_ID, viewModel.getSessionId());
        NavHostFragment.findNavController(this).navigate(R.id.addRoute, bundle);
    }

    @OnClick(R.id.icon_statistic_expander)
    public void onExpandSessionStatistics() {
        sessionStatisticView.setVisibility(View.VISIBLE);
        statisticExpander.setVisibility(View.GONE);
    }

    @OnClick(R.id.close_statistics)
    public void onCloseSessionStatistics() {
        sessionStatisticView.setVisibility(View.GONE);
        statisticExpander.setVisibility(View.VISIBLE);
    }

}
