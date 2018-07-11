package de.ringleinknorr.boulderapp.views;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;

import java.util.List;

import de.ringleinknorr.boulderapp.models.RouteLevel;

public class RouteLevelListAdapter extends SelectableItemListAdapter<RouteLevel, RouteLevelView> {

    public RouteLevelListAdapter(List<RouteLevel> items) {
        super(items);
    }

    @Override
    public RouteLevelView onCreateView(@NonNull ViewGroup parent, int viewType) {
        return new RouteLevelView(parent.getContext());
    }

    @Override
    public void onBindView(RouteLevelView routeLevelView, int position, RouteLevel item) {
        super.onBindView(routeLevelView, position, item);
        CardView levelSelectionCardView = routeLevelView.getLevelSelectionCardView();
        levelSelectionCardView.setCardBackgroundColor((item.getLevelColor()));
    }
}
