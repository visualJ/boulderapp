package de.ringleinknorr.boulderapp.routes;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.appyvet.materialrangebar.RangeBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;
import de.ringleinknorr.boulderapp.R;
import de.ringleinknorr.boulderapp.ViewModelFactory;

public class RouteSearchFragment extends Fragment {
    @BindView(R.id.auto_complete_text_view)
    AutoCompleteTextView
            autoCompleteTextView;
    @BindView(R.id.gym_sector_view)
    ImageView gymSectorImageView;
    @BindView(R.id.rangeBar)
    RangeBar rangeBar;
    @BindView(R.id.route_list)
    RecyclerView routeList;
    @BindView(R.id.route_search_scrollview)
    ScrollView routeSearchScrollview;
    @BindView(R.id.route_search_results_view)
    ConstraintLayout routeSearchResultView;
    LiveData<List<Route>> routes;
    RouteListAdapter routeListAdapter;

    @Inject
    ViewModelFactory<RouteSearchViewModel> viewModelFactory;

    private RouteSearchViewModel viewModel;
    private ArrayAdapter<String> gymAdapter;
    private int mShortAnimationDuration;

    @Inject
    GymRepository gymRepository;

    public RouteSearchFragment() {
        // Required empty public constructor
    }

    @SuppressLint({"ClickableViewAccessibility"})
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_route_search, container, false);
        ButterKnife.bind(this, view);

        routeSearchResultView.setVisibility(View.GONE);
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        routeListAdapter = new RouteListAdapter(new ArrayList<>());
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RouteSearchViewModel.class);
        viewModel.getRoutes().observe(this, routes -> routeListAdapter.setRoutes(routes));

        routeList.setHasFixedSize(false);
        routeList.setLayoutManager(new LinearLayoutManager(getContext()));
        routeList.setAdapter(routeListAdapter);

        gymAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                android.R.layout.simple_dropdown_item_1line, new ArrayList<>());

        AutoCompleteTextView textView = autoCompleteTextView;
        textView.setAdapter(gymAdapter);
        gymRepository.getAllGymNames().observe(this, names -> {
            gymAdapter.clear();
            if (names != null) {
                gymAdapter.addAll(names);
            }
        });

        gymSectorImageView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                // Select sector
            }
            return true;
        });

        return view;
    }

    @OnClick(R.id.add_button)
    public void onAddButton() {
        int minLevel = rangeBar.getLeftIndex();
        int maxLevel = rangeBar.getRightIndex();
        switchToSearchResults();
        String gymName = String.valueOf(autoCompleteTextView.getText());
        viewModel.queryRoutes(new RouteSearchParameter(minLevel, maxLevel, gymName));
    }

    @OnClick(R.id.switch_to_route_search)
    public void onSwitchView() {
        switchToRouteSearch();
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    private void switchToSearchResults() {
        routeSearchResultView.setAlpha(0f);
        routeSearchResultView.setVisibility(View.VISIBLE);

        routeSearchResultView.animate()
                .alpha(1f)
                .setDuration(mShortAnimationDuration)
                .setListener(null);
        routeSearchScrollview.animate()
                .alpha(0f)
                .setDuration(mShortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        routeSearchScrollview.setVisibility(View.GONE);
                    }
                });
    }

    private void switchToRouteSearch() {
        routeSearchScrollview.setAlpha(0f);
        routeSearchScrollview.setVisibility(View.VISIBLE);

        routeSearchScrollview.animate()
                .alpha(1f)
                .setDuration(mShortAnimationDuration)
                .setListener(null);
        routeSearchResultView.animate()
                .alpha(0f)
                .setDuration(mShortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        routeSearchResultView.setVisibility(View.GONE);
                    }
                });
    }

    private static final Integer[][] COORDS = new Integer[][]{
            {0, 0, 100, 100}
    };
}
