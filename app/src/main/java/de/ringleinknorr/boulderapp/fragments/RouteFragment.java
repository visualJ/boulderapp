package de.ringleinknorr.boulderapp.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import androidx.navigation.fragment.NavHostFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.ringleinknorr.boulderapp.R;
import de.ringleinknorr.boulderapp.dependencyinjection.DIViewModelFactory;
import de.ringleinknorr.boulderapp.models.Gym;
import de.ringleinknorr.boulderapp.models.RouteLevel;
import de.ringleinknorr.boulderapp.models.Session;
import de.ringleinknorr.boulderapp.repositories.ImageRepository;
import de.ringleinknorr.boulderapp.services.StatisticsService;
import de.ringleinknorr.boulderapp.util.ColorUtil;
import de.ringleinknorr.boulderapp.views.GymSectorImageView;
import de.ringleinknorr.boulderapp.views.PlaceholderRecyclerView;
import de.ringleinknorr.boulderapp.views.SessionListAdapter;

public class RouteFragment extends InjectableFragment {

    public static final String ARG_ROUTE_ID = "routeId";

    @BindView(R.id.route_level)
    TextView routeLevel;

    @BindView(R.id.route_image)
    ImageView routeImage;

    @BindView(R.id.gym_name)
    TextView gymName;

    @BindView(R.id.gymSectorCanvasView2)
    GymSectorImageView gymSectorImageView;

    @BindView(R.id.session_list)
    PlaceholderRecyclerView sessionList;

    @Inject
    DIViewModelFactory<RouteViewModel> viewModelFactory;

    @Inject
    StatisticsService statisticsService;

    @Inject
    ImageRepository imageRepository;

    private RouteViewModel viewModel;
    private SessionListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_route, container, false);
        ButterKnife.bind(this, view);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RouteViewModel.class);
        viewModel.init(Objects.requireNonNull(getArguments()).getLong(ARG_ROUTE_ID));
        gymSectorImageView.setClickable(false);
        adapter = new SessionListAdapter(getContext(), new ArrayList<>(), Locale.getDefault(), (position, item, view1) -> navigateToSession(item), (session, callback) ->
                statisticsService.getPreviousMonthTrend(session).observe(this, callback::call));
        sessionList.setAdapter(adapter);

        viewModel.getRoute().observe(this, route -> {
            RouteLevel level = route.getRouteLevel().getTarget();
            Gym gym = route.getGym().getTarget();

            routeLevel.setText(String.valueOf(level.getLevelName()));
            routeLevel.setBackgroundColor(level.getLevelColor());
            routeLevel.setTextColor(ColorUtil.getReadableTextColor(level.getLevelColor()));
            gymName.setText(gym.getName());
            imageRepository.loadImageWithThumbnailPlaceholder(route.getImageId(), routeImage);
            imageRepository.loadImage(gym.getImageId(), gymSectorImageView);
            gymSectorImageView.setGym(gym);
            gymSectorImageView.setSelectedSector(route.getGymSector().getTarget());

            List<Session> sessions = route.getSessions();
            Collections.sort(sessions, (s1, s2) -> s1.getDate().compareTo(s2.getDate()));
            adapter.setItems(sessions);
        });

        setTitle(getString(R.string.route_title));

        return view;
    }

    private void navigateToSession(Session session) {
        Bundle bundle = new Bundle();
        bundle.putLong(SessionFragment.ARG_SESSION_ID, session.getId());
        NavHostFragment.findNavController(this).navigate(R.id.selectSession, bundle);
    }
}
