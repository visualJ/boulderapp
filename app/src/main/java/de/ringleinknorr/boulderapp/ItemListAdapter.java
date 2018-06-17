package de.ringleinknorr.boulderapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class ItemListAdapter<I, V extends View> extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_PLACEHOLDER = 1;
    private final int VIEW_TYPE_SECTION_ITEM = 2;

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

    @SuppressWarnings("unchecked") // the view holders are set in a type safe way
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_PLACEHOLDER:
                ((ViewHolder<EmptyListPlaceholder>) holder).view.setText(placeholderText);
                break;
            case VIEW_TYPE_SECTION_ITEM:
                SectionTitle<V> sectionTitle = ((ViewHolder<SectionTitle<V>>) holder).view;
                sectionTitle.getView().setOnClickListener((view -> onItemClickListener.onItemClicked(position, items.get(position))));
                sectionTitle.setTitle(provideSectionTitle(position));
                onBindView(sectionTitle.getView(), position, items.get(position));
                break;
            case VIEW_TYPE_ITEM:
            default:
                holder.view.setOnClickListener((view -> onItemClickListener.onItemClicked(position, items.get(position))));
                onBindView(((ViewHolder<V>) holder).view, position, items.get(position));
        }
    }

    /**
     * Binds a view to a data item
     *
     * @param v        the view to bind
     * @param position the item index
     * @param item     the data item
     */
    public abstract void onBindView(V v, int position, I item);

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_PLACEHOLDER:
                return new ViewHolder<>(new EmptyListPlaceholder(parent.getContext()));
            case VIEW_TYPE_SECTION_ITEM:
                return new ViewHolder<>(new SectionTitle<>(parent.getContext(), onCreateView(parent, viewType)));
            case VIEW_TYPE_ITEM:
            default:
                return new ViewHolder<>(onCreateView(parent, viewType));
        }
    }

    /**
     * Decides, whether a row has a section title.
     * The default implementation uses provideSectionTitle for the decision.
     *
     * @param position the row position
     * @return true, if it has a title, otherwise false.
     */
    public boolean hasSectionTitle(int position) {
        return !provideSectionTitle(position).isEmpty();
    }

    /**
     * Provides a section title for the given position
     *
     * @param position index of the corresponding data item
     * @return the section title for this item
     */
    @NonNull
    public String provideSectionTitle(int position) {
        return "";
    }

    @Override
    public int getItemViewType(int position) {
        if (isPlaceholderShown()) {
            return VIEW_TYPE_PLACEHOLDER;
        } else {
            return hasSectionTitle(position) ? VIEW_TYPE_SECTION_ITEM : VIEW_TYPE_ITEM;
        }
    }

    /**
     * Creates a view for displaying data.
     * The view can be recycled and used multiple times.
     *
     * @param parent   the views parent
     * @param viewType the type of view to be created, can usually be ignored
     * @return a view instance
     */
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

    public void setPlaceholderText(@NonNull String placeholderText) {
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
