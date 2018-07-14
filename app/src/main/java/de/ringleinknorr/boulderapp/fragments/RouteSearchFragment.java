package de.ringleinknorr.boulderapp.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import androidx.navigation.fragment.NavHostFragment;
import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.ringleinknorr.boulderapp.R;
import de.ringleinknorr.boulderapp.dependencyinjection.DIViewModelFactory;
import de.ringleinknorr.boulderapp.models.GymSector;
import de.ringleinknorr.boulderapp.models.Route;
import de.ringleinknorr.boulderapp.models.RouteLevel;
import de.ringleinknorr.boulderapp.models.RouteSearchParameter;
import de.ringleinknorr.boulderapp.repositories.GymRepository;
import de.ringleinknorr.boulderapp.repositories.ImageRepository;
import de.ringleinknorr.boulderapp.views.GymSectorImageView;
import de.ringleinknorr.boulderapp.views.PlaceholderRecyclerView;
import de.ringleinknorr.boulderapp.views.RouteCardViewTransition;
import de.ringleinknorr.boulderapp.views.RouteLevelListAdapter;
import de.ringleinknorr.boulderapp.views.RouteListAdapter;

public class RouteSearchFragment extends InjectableFragment {

    public static final String KEY_SESSION_ID = "sessionId";

    @BindView(R.id.auto_complete_text_view)
    AutoCompleteTextView autoCompleteTextView;
    @BindView(R.id.route_list)
    PlaceholderRecyclerView routeList;
    @BindView(R.id.add_button)
    FloatingActionButton addButton;
    @BindView(R.id.gymSectorCanvasView2)
    GymSectorImageView gymSectorImage;
    @BindView(R.id.level_list)
    PlaceholderRecyclerView levelList;
    @BindView(R.id.route_transition_image)
    ImageView routeTransitionImage;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;

    @BindInt(android.R.integer.config_shortAnimTime)
    int mShortAnimationDuration;

    @Inject
    DIViewModelFactory<RouteSearchViewModel> viewModelFactory;
    @Inject
    GymRepository gymRepository;
    @Inject
    ImageRepository imageRepository;

    private RouteListAdapter routeListAdapter;
    private RouteLevelListAdapter routeLevelListAdapter;
    private RouteSearchViewModel viewModel;
    private ArrayAdapter<String> gymAdapter;
    private boolean forResult = false;

    public RouteSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_route_search, container, false);
        ButterKnife.bind(this, view);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RouteSearchViewModel.class);
        routeListAdapter = new RouteListAdapter(new ArrayList<>(), imageRepository::loadThumbnail);
        routeLevelListAdapter = new RouteLevelListAdapter(new ArrayList<>());
        gymAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                android.R.layout.simple_dropdown_item_1line, new ArrayList<>());

        viewModel.getSelectedGym().observe(this, gym -> {
            imageRepository.loadImage(gym.getImageId(), gymSectorImage);
            gymSectorImage.setGym(gym);
            if (viewModel.getSelectedGymSector() != null) {
                gymSectorImage.setSelectedSector(viewModel.getSelectedGymSector());
            }

            List<RouteLevel> routeLevels = gym.getRouteLevels();
            Collections.sort(routeLevels, (routeLevel1, routeLevel2) -> routeLevel1.getLevelNumber() - routeLevel2.getLevelNumber());

            routeLevelListAdapter.setItems(routeLevels);
            if (viewModel.getSelectedRouteLevelPositions() != null) {
                routeLevelListAdapter.setSelectedPositions(viewModel.getSelectedRouteLevelPositions());
            }

            onSearchButton();
        });

        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(KEY_SESSION_ID)) {
            onCreateForResult(arguments);
        } else {
            routeListAdapter.setSelectable(false);
        }

        routeListAdapter.setOnItemClickListener((position, route, view1) -> new RouteCardViewTransition(view1, routeTransitionImage, () -> navigateToRoute(route), routeList, appBarLayout).start());

        gymSectorImage.setOnSectorSelectedListener(sector -> {
            viewModel.setSelectedGymSector(sector);
            routeListAdapter.setSelectedPositions(new ArrayList<>());
            onSearchButton();
        });

        routeList.setHasFixedSize(false);
        routeList.setAdapter(routeListAdapter);
        viewModel.getRoutes().observe(this, routeListAdapter::setItems);
        levelList.setHasFixedSize(false);
        levelList.setAdapter(routeLevelListAdapter);

        routeLevelListAdapter.setOnSelectionChangedListener((selectedPositions, itemsChanged) -> {
            if (!itemsChanged) {
                viewModel.setSelectedRouteLevelPositions(selectedPositions);
                routeListAdapter.setSelectedPositions(new ArrayList<>());
                onSearchButton();
            }
        });

        autoCompleteTextView.setOnItemClickListener((parent, view1, position, id) -> {
            viewModel.setSelectedGym(gymRepository.getGymWithName(autoCompleteTextView.getText().toString()));
        });
        autoCompleteTextView.setAdapter(gymAdapter);
        gymRepository.getAllGymNames().observe(this, names -> {
            gymAdapter.clear();
            if (names != null) {
                gymAdapter.addAll(names);
            }
        });

        setTitle(forResult ? getString(R.string.route_search_title_add) : getString(R.string.route_search_title));

        return view;
    }

    private void navigateToRoute(Route route) {
        // fetch and cache the full size image in the background while showing the transition
        imageRepository.fetchImage(route.getImageId());
        Bundle bundle = new Bundle();
        bundle.putLong(RouteFragment.ARG_ROUTE_ID, route.getId());
        NavHostFragment.findNavController(this).navigate(R.id.showRoute, bundle);
    }

    protected void onCreateForResult(Bundle arguments) {
        forResult = true;
        viewModel.setSessionId(arguments.getLong(KEY_SESSION_ID));
        viewModel.getSession().observe(this, session -> {
            autoCompleteTextView.setText(session.getGym().getTarget().getName());
            autoCompleteTextView.setEnabled(false);
            viewModel.setSelectedGym(session.getGym().getTarget());
        });
        routeListAdapter.setOnSelectionChangedListener((selectedPositions, itemsChanged) -> {
            if (selectedPositions.size() > 0) {
                if (addButton.getVisibility() != View.VISIBLE) {
                    addButton.setAlpha(0.5f);
                    addButton.setScaleX(0.5f);
                    addButton.setScaleY(0.5f);
                    addButton.setVisibility(View.VISIBLE);
                    addButton.animate().alpha(1).scaleX(1).scaleY(1).setInterpolator(new OvershootInterpolator(2)).setDuration(100).setListener(null);
                }
            } else {
                addButton.animate().alpha(0).scaleX(0.5f).scaleY(0.5f).setInterpolator(new AccelerateInterpolator(2)).setDuration(100).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        addButton.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    public void onSearchButton() {
        String gymName = String.valueOf(autoCompleteTextView.getText());
        GymSector selectedSector = viewModel.getSelectedGymSector();
        viewModel.queryRoutes(new RouteSearchParameter(gymRepository.getGymWithName(gymName).getId(), selectedSector != null ? selectedSector.getId() : null, routeLevelListAdapter.getSelectedItems()));
    }

    @OnClick(R.id.add_button)
    public void onAddButton() {
        viewModel.addRoutesToSession(routeListAdapter.getSelectedPositions());
        NavHostFragment.findNavController(this).popBackStack();
    }

}
