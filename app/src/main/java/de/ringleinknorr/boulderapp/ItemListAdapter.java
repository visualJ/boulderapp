package de.ringleinknorr.boulderapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class ItemListAdapter<I, V extends View> extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {
    private List<I> items;

    public ItemListAdapter(List<I> items) {
        this.items = items;
    }

    public List<I> getItems() {
        return items;
    }

    public void setItems(List<I> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public ViewHolder createViewHolder(V v) {
        return new ViewHolder<>(v);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        onBindView(((ViewHolder<V>) holder).view, position);
    }

    public abstract void onBindView(V v, int position);

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder<V>(onCreateView(parent, viewType));
    }

    public abstract V onCreateView(@NonNull ViewGroup parent, int viewType);

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder<V extends View> extends RecyclerView.ViewHolder {
        public V view;

        private ViewHolder(V v) {
            super(v);
            view = v;
        }
    }
}
