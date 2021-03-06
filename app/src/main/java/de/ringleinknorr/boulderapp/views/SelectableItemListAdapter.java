package de.ringleinknorr.boulderapp.views;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link ItemListAdapter} that can also handle item selection.
 * This features internal selection state handling and a selection listener via {@link OnSelectionChangedListener}.
 *
 * @param <I> The item type to use in the adapter.
 * @param <V> The view type that displays the item data, needs to implement {@link Selectable}.
 */
public abstract class SelectableItemListAdapter<I, V extends View & SelectableItemListAdapter.Selectable> extends ItemListAdapter<I, V> {

    private List<Integer> selectedPositions = new ArrayList<>();
    private OnSelectionChangedListener onSelectionChangedListener = (selectedPositions1, itemsChanged) -> {
    };
    private boolean selectable = true;

    public SelectableItemListAdapter(List<I> items) {
        super(items);
    }

    public SelectableItemListAdapter(List<I> items, OnItemClickListener<I, V> onItemClickListener) {
        super(items, onItemClickListener);
    }

    @Override
    public void onBindView(V v, int position, I item) {
        v.setSelectable(selectable);
        if (selectable) {
            v.setOnSelectedListener(selected -> {
                if (selected) {
                    selectedPositions.add(position);
                } else {
                    selectedPositions.remove((Integer) position);
                }
                onSelectionChangedListener.onSelectionChanged(selectedPositions, false);
            });
            v.setItemSelected(selectedPositions.contains(position));
        }
    }

    public List<I> getSelectedItems() {
        List<I> selectedItems = new ArrayList<>();
        for (int pos : selectedPositions) {
            selectedItems.add(this.getItems().get(pos));
        }
        return selectedItems;
    }

    public List<Integer> getSelectedPositions() {
        return selectedPositions;
    }

    public void setSelectedPositions(List<Integer> selectedPositions) {
        this.selectedPositions = selectedPositions;
        notifyDataSetChanged();
    }

    public boolean isSelectable() {
        return selectable;
    }

    @Override
    public void setItems(List<I> items) {
        if (items.equals(getItems())) {
            selectedPositions.clear();
        }

        onSelectionChangedListener.onSelectionChanged(selectedPositions, true);
        super.setItems(items);
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
        notifyDataSetChanged();
    }

    public OnSelectionChangedListener getOnSelectionChangedListener() {
        return onSelectionChangedListener;
    }

    public void setOnSelectionChangedListener(@NonNull OnSelectionChangedListener onSelectionChangedListener) {
        this.onSelectionChangedListener = onSelectionChangedListener;
    }

    public interface Selectable {
        void setOnSelectedListener(OnSelectedListener listener);

        void setItemSelected(boolean selected);

        void setSelectable(boolean selectable);
    }

    public interface OnSelectedListener {
        void onSelected(boolean selected);
    }

    public interface OnSelectionChangedListener {
        void onSelectionChanged(List<Integer> selectedPositions, boolean itemsChanged);
    }
}
