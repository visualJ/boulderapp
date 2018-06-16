package de.ringleinknorr.boulderapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class ItemListAdapter<I, V extends View> extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_PLACEHOLDER = 1;

    private List<I> items;
    private OnItemClickListener<I> onItemClickListener = (position, item) -> {
    };
    private String placeholderText = "";

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
        switch (getItemViewType(position)) {
            case VIEW_TYPE_PLACEHOLDER:
                ((ViewHolder<EmptyListPlaceholder>) holder).view.setText(placeholderText);
                break;
            case VIEW_TYPE_ITEM:
            default:
                holder.view.setOnClickListener((view -> onItemClickListener.onItemClicked(position, items.get(position))));
                onBindView(((ViewHolder<V>) holder).view, position, items.get(position));
        }
    }

    public abstract void onBindView(V v, int position, I item);

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_PLACEHOLDER:
                return new ViewHolder<>(new EmptyListPlaceholder(parent.getContext()));
            case VIEW_TYPE_ITEM:
            default:
                return new ViewHolder<>(onCreateView(parent, viewType));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return isPlaceholderShown() ? VIEW_TYPE_PLACEHOLDER : VIEW_TYPE_ITEM;
    }

    public abstract V onCreateView(@NonNull ViewGroup parent, int viewType);

    @Override
    public int getItemCount() {
        if (isPlaceholderShown()) {
            // return 1 for the placeholder item
            return 1;
        } else {
            return items.size();
        }
    }

    private boolean isPlaceholderShown() {
        return items.isEmpty() && !placeholderText.isEmpty();
    }

    public OnItemClickListener<I> getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener<I> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public String getPlaceholderText() {
        return placeholderText;
    }

    public void setPlaceholderText(String placeholderText) {
        this.placeholderText = placeholderText;
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
