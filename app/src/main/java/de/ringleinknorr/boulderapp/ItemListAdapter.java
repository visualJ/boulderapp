package de.ringleinknorr.boulderapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class ItemListAdapter<I, V extends View> extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {
    private List<I> items;
    private OnItemClickListener<I> onItemClickListener = (position, item) -> {
    };

    public ItemListAdapter(List<I> items) {
        this.items = items;
    }

    public ItemListAdapter(List<I> items, OnItemClickListener<I> onItemClickListener) {
        this(items);
        this.onItemClickListener = onItemClickListener;
    }

    public List<I> getItems() {
        return items;
    }

    public void setItems(List<I> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.view.setOnClickListener((view -> onItemClickListener.onItemClicked(position, items.get(position))));
        onBindView(((ViewHolder<V>) holder).view, position, items.get(position));
    }

    public abstract void onBindView(V v, int position, I item);

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder<>(onCreateView(parent, viewType));
    }

    public abstract V onCreateView(@NonNull ViewGroup parent, int viewType);

    @Override
    public int getItemCount() {
        return items.size();
    }

    public OnItemClickListener<I> getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener<I> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener<I> {
        void onItemClicked(int position, I item);
    }

    public static class ViewHolder<V extends View> extends RecyclerView.ViewHolder {
        public V view;

        private ViewHolder(V v) {
            super(v);
            view = v;
        }
    }
}
