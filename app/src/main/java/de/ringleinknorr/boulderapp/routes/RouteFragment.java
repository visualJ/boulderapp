package de.ringleinknorr.boulderapp.routes;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import javax.inject.Inject;

import androidx.navigation.fragment.NavHostFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.ringleinknorr.boulderapp.ColorUtil;
import de.ringleinknorr.boulderapp.InjectableFragment;
import de.ringleinknorr.boulderapp.R;
import de.ringleinknorr.boulderapp.ViewModelFactory;
import de.ringleinknorr.boulderapp.timeline.Session;
import de.ringleinknorr.boulderapp.timeline.SessionFragment;
import de.ringleinknorr.boulderapp.timeline.SessionListAdapter;
import de.ringleinknorr.boulderapp.timeline.SessionRoute;

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
    RecyclerView sessionList;

    @Inject
    ViewModelFactory<RouteViewModel> viewModelFactory;

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

        adapter = new SessionListAdapter(new ArrayList<>(), Locale.getDefault(), (position, item, view1) -> navigateToSession(item));
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

            Set<Session> sessionSet = new HashSet<>();
            for (SessionRoute sessionRoute : route.getSessionRoutes()) {
                Session session = sessionRoute.getSession().getTarget();
                if (session != null) {
                    sessionSet.add(session);
                }
            }
            adapter.setItems(new ArrayList<>(sessionSet));
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
