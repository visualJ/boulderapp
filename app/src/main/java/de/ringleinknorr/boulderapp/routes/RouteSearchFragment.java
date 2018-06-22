package de.ringleinknorr.boulderapp.routes;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

import androidx.navigation.fragment.NavHostFragment;
import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.ringleinknorr.boulderapp.InjectableFragment;
import de.ringleinknorr.boulderapp.R;
import de.ringleinknorr.boulderapp.ViewModelFactory;

public class RouteSearchFragment extends InjectableFragment {

    public static final String KEY_SESSION_ID = "sessionId";

    @BindView(R.id.auto_complete_text_view)
    AutoCompleteTextView autoCompleteTextView;
    @BindView(R.id.route_list)
    RecyclerView routeList;
    @BindView(R.id.add_button)
    FloatingActionButton addButton;
    @BindView(R.id.gymSectorCanvasView2)
    GymSectorImageView gymSectorImage;
    @BindView(R.id.level_list)
    RecyclerView levelList;

    @BindInt(android.R.integer.config_shortAnimTime)
    int mShortAnimationDuration;

    @Inject
    ViewModelFactory<RouteSearchViewModel> viewModelFactory;
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
        routeListAdapter = new RouteListAdapter(new ArrayList<>(), imageRepository::loadImage);
        routeLevelListAdapter = new RouteLevelListAdapter(new ArrayList<>());
        gymAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                android.R.layout.simple_dropdown_item_1line, new ArrayList<>());

        viewModel.getSelectedGym().observe(this, gym -> {
            Bitmap bmImg = BitmapFactory.decodeResource(getResources(), R.drawable.map_gym_1);
            viewModel.setGimSectorImage(bmImg);
            gymSectorImage.setImageBitmap(bmImg);
            gymSectorImage.setGym(gym);
            routeLevelListAdapter.setItems(gym.getRouteLevels());
        });

        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(KEY_SESSION_ID)) {
            onCreateForResult(arguments);
        } else {
            routeListAdapter.setSelectable(false);
        }

        gymSectorImage.setAdjustViewBounds(true);
        gymSectorImage.setOnSectorSelectedListener(sector -> onSearchButton());

        viewModel.getRoutes().observe(this, routes -> routeListAdapter.setItems(routes));
        routeList.setHasFixedSize(false);
        routeList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        routeList.setAdapter(routeListAdapter);
        levelList.setHasFixedSize(false);
        levelList.setLayoutManager(new LinearLayoutManager(getContext()));
        levelList.setAdapter(routeLevelListAdapter);

        /*
        rangeBar.setOnRangeBarChangeListener((rangeBar, leftPinIndex, rightPinIndex, leftPinValue, rightPinValue) -> {
            onSearchButton();
        });*/

        autoCompleteTextView.setOnItemClickListener((parent, view1, position, id) -> {
            viewModel.setSelectedGym(gymRepository.getGymWithName(autoCompleteTextView.getText().toString()));
            onSearchButton();
        });
        autoCompleteTextView.setAdapter(gymAdapter);
        gymRepository.getAllGymNames().observe(this, names -> {
            gymAdapter.clear();
            if (names != null) {
                gymAdapter.addAll(names);
            }
        });

        setTitle(forResult ? "Routen hinzufügen" : "Routen Suche");

        return view;
    }

    protected void onCreateForResult(Bundle arguments) {
        forResult = true;
        viewModel.setSessionId(arguments.getLong(KEY_SESSION_ID));
        viewModel.getSession().observe(this, session -> {
            autoCompleteTextView.setText(session.getGym().getTarget().getName());
            autoCompleteTextView.setEnabled(false);
            viewModel.setSelectedGym(session.getGym().getTarget());
            viewModel.setSelectedGym(gymRepository.getGymWithName(autoCompleteTextView.getText().toString()));
            onSearchButton();
        });
        routeListAdapter.setOnSelectionChangedListener(selectedPositions -> {
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
        //int minLevel = rangeBar.getLeftIndex();
        //int maxLevel = rangeBar.getRightIndex();
        int minLevel = 1;
        int maxLevel = 6;
        String gymName = String.valueOf(autoCompleteTextView.getText());
        GymSector selectedSector = gymSectorImage.getSelectedSector();
        viewModel.queryRoutes(new RouteSearchParameter(minLevel, maxLevel, gymName, selectedSector != null ? selectedSector.getId() : null));
        Log.i("afs", "onSearchButton: ");
    }

    @OnClick(R.id.add_button)
    public void onAddButton() {
        viewModel.addRoutesToSession(routeListAdapter.getSelectedPositions());
        NavHostFragment.findNavController(this).popBackStack();
    }

}
