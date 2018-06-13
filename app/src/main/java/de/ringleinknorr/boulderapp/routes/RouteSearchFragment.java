package de.ringleinknorr.boulderapp.routes;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.appyvet.materialrangebar.RangeBar;

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
    @BindView(R.id.add_button)
    Button addButton;

    @BindInt(android.R.integer.config_shortAnimTime)
    int mShortAnimationDuration;

    @Inject
    ViewModelFactory<RouteSearchViewModel> viewModelFactory;
    @Inject
    GymRepository gymRepository;

    private RouteListAdapter routeListAdapter;
    private RouteSearchViewModel viewModel;
    private ArrayAdapter<String> gymAdapter;

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

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(KEY_SESSION_ID)) {
            addButton.setVisibility(View.VISIBLE);
            viewModel.setSessionId(bundle.getLong(KEY_SESSION_ID));
            viewModel.getSession().observe(this, session -> {
                autoCompleteTextView.setText(session.getGym().getTarget().getName());
                autoCompleteTextView.setEnabled(false);
            });
        }

        routeSearchResultView.setVisibility(View.GONE);

        routeListAdapter = new RouteListAdapter(new ArrayList<>());
        viewModel.getRoutes().observe(this, routes -> routeListAdapter.setItems(routes));

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

        return view;
    }

    @OnClick(R.id.search_button)
    public void onSearchButton() {
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

    @OnClick(R.id.add_button)
    public void onAddButton() {
        viewModel.addRoutesToSession();
        NavHostFragment.findNavController(this).popBackStack();
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
