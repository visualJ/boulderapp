package de.ringleinknorr.boulderapp;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public abstract class SelectableItemListAdapter<I, V extends View & SelectableItemListAdapter.Selectable> extends ItemListAdapter<I, V> {

    private List<Integer> selectedPositions = new ArrayList<>();

    public SelectableItemListAdapter(List<I> items) {
        super(items);
    }

    public SelectableItemListAdapter(List<I> items, OnItemClickListener<I> onItemClickListener) {
        super(items, onItemClickListener);
    }

    @Override
    public void onBindView(V v, int position, I item) {
        v.setOnSelectedListener(selected -> {
            if (selected) {
                selectedPositions.add(position);
            } else {
                selectedPositions.remove((Integer) position);
            }
        });
        v.setSelected(selectedPositions.contains(position));
    }

    public List<Integer> getSelectedPositions() {
        return selectedPositions;
    }

    public void setSelectedPositions(List<Integer> selectedPositions) {
        this.selectedPositions = selectedPositions;
        notifyDataSetChanged();
    }

    public interface Selectable {
        void setOnSelectedListener(OnSelectedListener listener);

        void setItemSelected(boolean selected);
    }

    public interface OnSelectedListener {
        void onSelected(boolean selected);
    }
}
