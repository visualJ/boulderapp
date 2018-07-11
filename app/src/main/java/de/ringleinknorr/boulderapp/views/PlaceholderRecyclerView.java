package de.ringleinknorr.boulderapp.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import de.ringleinknorr.boulderapp.R;

public class PlaceholderRecyclerView extends RecyclerView {

    private View placeholder;

    private AdapterDataObserver emptyObserver = new AdapterDataObserver() {

        @Override
        public void onChanged() {
            Adapter<?> adapter = getAdapter();
            if (adapter != null && placeholder != null) {
                if (adapter.getItemCount() == 0) {
                    placeholder.setVisibility(View.VISIBLE);
                } else {
                    placeholder.setVisibility(View.GONE);
                }
            }
        }
    };
    private int placeholderId;

    public PlaceholderRecyclerView(Context context) {
        super(context);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (placeholderId != -1) {
            placeholder = getRootView().findViewById(placeholderId);
            emptyObserver.onChanged();
        }
    }

    public PlaceholderRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray attributes = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.PlaceholderRecyclerView,
                0, 0);
        try {
            placeholderId = attributes.getResourceId(R.styleable.PlaceholderRecyclerView_placeholder, -1);
        } finally {
            attributes.recycle();
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        if (adapter != null) {
            adapter.registerAdapterDataObserver(emptyObserver);
        }

        emptyObserver.onChanged();
    }

    public View getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(View placeholder) {
        this.placeholder = placeholder;
    }
}
