package de.ringleinknorr.boulderapp.routes;

import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.ringleinknorr.boulderapp.SelectableItemListAdapter;

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
