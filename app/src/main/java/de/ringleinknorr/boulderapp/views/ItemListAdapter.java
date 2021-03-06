package de.ringleinknorr.boulderapp.views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * A generic adapter base class for recycler views.
 * This handles internal item lists, click listeners for items and section labels.
 *
 * @param <I> The type of item handled by the adapter (e.g. 'User').
 * @param <V> The type of view that displays the item data (e.g. 'UserDetailView').
 */
public abstract class ItemListAdapter<I, V extends View> extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_SECTION_ITEM = 1;

    private List<I> items;
    private OnItemClickListener<I, V> onItemClickListener = (position, item, view) -> {
    };
    private ImageProvider imageProvider;

    public ItemListAdapter(List<I> items) {
        this.items = items;
    }

    public ItemListAdapter(List<I> items, OnItemClickListener<I, V> onItemClickListener) {
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
            case VIEW_TYPE_SECTION_ITEM:
                SectionTitle<V> sectionTitle = ((ViewHolder<SectionTitle<V>>) holder).view;
                sectionTitle.getView().setOnClickListener((view -> onItemClickListener.onItemClicked(position, items.get(position), sectionTitle.getView())));
                sectionTitle.setTitle(provideSectionTitle(position));
                sectionTitle.setAlpha(1f);
                onBindView(sectionTitle.getView(), position, items.get(position));
                break;
            case VIEW_TYPE_ITEM:
            default:
                holder.view.setOnClickListener((view -> onItemClickListener.onItemClicked(position, items.get(position), ((ViewHolder<V>) holder).view)));
                holder.view.setAlpha(1f);
                onBindView(((ViewHolder<V>) holder).view, position, items.get(position));
        }
    }

    /**
     * Binds a view to a data item.
     *
     * @param v        the view to bind.
     * @param position the item index.
     * @param item     the data item.
     */
    public abstract void onBindView(V v, int position, I item);

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
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
     * @param position the row position in the adapter.
     * @return true, if it has a title, otherwise false.
     */
    public boolean hasSectionTitle(int position) {
        return !provideSectionTitle(position).isEmpty();
    }

    /**
     * Provides a section title for the given position.
     *
     * @param position index of the corresponding data item.
     * @return the section title for this item.
     */
    @NonNull
    public String provideSectionTitle(int position) {
        return "";
    }

    @Override
    public int getItemViewType(int position) {
        return hasSectionTitle(position) ? VIEW_TYPE_SECTION_ITEM : VIEW_TYPE_ITEM;
    }

    /**
     * Creates a view for displaying data.
     * The view can be recycled and used multiple times.
     *
     * @param parent   the views parent.
     * @param viewType the type of view to be created, can usually be ignored.
     * @return a new view instance.
     */
    public abstract V onCreateView(@NonNull ViewGroup parent, int viewType);

    @Override
    public int getItemCount() {
        return items.size();
    }

    public OnItemClickListener<I, V> getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener<I, V> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ImageProvider getImageProvider() {
        return imageProvider;
    }

    public void setImageProvider(ImageProvider imageProvider) {
        this.imageProvider = imageProvider;
    }

    public interface OnItemClickListener<I, V> {
        void onItemClicked(int position, I item, V view);
    }

    /**
     * A simple generic view holder with only one view
     * @param <V> The type of view the view holder contains
     */
    public static class ViewHolder<V extends View> extends RecyclerView.ViewHolder {
        public V view;

        private ViewHolder(V v) {
            super(v);
            view = v;
        }
    }

    /**
     * The image provider provides a mechanism for adapter implementations to dynamically
     * load images into their views without getting dependencies to the image loading code.
     */
    public interface ImageProvider {
        /**
         * Load an image asynchronously into the given view.
         * @param imageId The id of the image to load. The provider implementation decides how to
         *                interpret that id
         * @param view The {@link ImageView} to load the image into
         */
        void getImage(String imageId, ImageView view);
    }
}
