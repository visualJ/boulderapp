package de.ringleinknorr.boulderapp.routes;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.ringleinknorr.boulderapp.InjectableFragment;
import de.ringleinknorr.boulderapp.R;
import de.ringleinknorr.boulderapp.ViewModelFactory;

public class RouteFragment extends InjectableFragment {

    public static final String ARG_ROUTE_ID = "routeId";

    @BindView(R.id.route_level)
    TextView routeLevel;

    @BindView(R.id.route_image)
    ImageView routeImage;

    @Inject
    ViewModelFactory<RouteViewModel> viewModelFactory;

    @Inject
    ImageRepository imageRepository;

    private RouteViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_route, container, false);
        ButterKnife.bind(this, view);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RouteViewModel.class);
        viewModel.init(Objects.requireNonNull(getArguments()).getLong(ARG_ROUTE_ID));

        viewModel.getRoute().observe(this, route -> {
            routeLevel.setText(String.valueOf(route.getRouteLevel().getTarget().getLevelName()));
            imageRepository.loadImage(route.getImageId(), routeImage);
        });

        setTitle("Route");

        return view;
    }
}
