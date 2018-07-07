package de.ringleinknorr.boulderapp.routes;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.ringleinknorr.boulderapp.ColorUtil;
import de.ringleinknorr.boulderapp.R;
import de.ringleinknorr.boulderapp.SelectableItemListAdapter;

public class RouteCardView extends ConstraintLayout implements SelectableItemListAdapter.Selectable {

    public final float SELECTED_ICON_APLHA = 1;
    public final float SELECTED_ICON_SCALE = 1;
    public final float DESELECTED_ICON_APLHA = 0.5f;
    public final float DESELECTED_ICON_SCALE = 0.8f;

    @BindView(R.id.route_level_text)
    TextView routeLevelText;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.selected_icon)
    ImageView selectedIcon;
    @BindView(R.id.card)
    CardView card;
    @BindInt(android.R.integer.config_shortAnimTime)
    int mShortAnimationDuration;
    private boolean itemSelected = false;
    private SelectableItemListAdapter.OnSelectedListener onSelectedListener = selected -> {
    };

    public RouteCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_routecard, this);
        ButterKnife.bind(this);
        setLayoutParams(new Constraints.LayoutParams(Constraints.LayoutParams.MATCH_PARENT, Constraints.LayoutParams.WRAP_CONTENT));
        selectedIcon.setAlpha(DESELECTED_ICON_APLHA);
        selectedIcon.setScaleX(DESELECTED_ICON_SCALE);
        selectedIcon.setScaleY(DESELECTED_ICON_SCALE);
    }

    public RouteCardView(Context context) {
        this(context, null);
    }

    @OnClick(R.id.selected_icon)
    public void onImageClick() {
        itemSelected = !itemSelected;
        if (itemSelected) {
            animateSelected();
        } else {
            animateDeselected();
        }
        onSelectedListener.onSelected(itemSelected);
    }

    public TextView getRouteLevelText() {
        return routeLevelText;
    }

    public void setRouteLevelText(String level) {
        this.routeLevelText.setText(level);
    }

    public ImageView getImage() {
        return image;
    }


    @Override
    public void setOnSelectedListener(SelectableItemListAdapter.OnSelectedListener listener) {
        onSelectedListener = listener;
    }

    public boolean isItemSelected() {
        return itemSelected;
    }

    @Override
    public void setItemSelected(boolean selected) {
        this.itemSelected = selected;
        if (selected) {
            selectedIcon.setAlpha(SELECTED_ICON_APLHA);
        } else {
            selectedIcon.setAlpha(DESELECTED_ICON_APLHA);
        }
    }

    public void setColor(int color) {
        routeLevelText.setBackground(new ColorDrawable(color));
        routeLevelText.setTextColor(ColorUtil.getReadableTextColor(color));
    }

    private void animateDeselected() {
        selectedIcon.animate()
                .setDuration(mShortAnimationDuration)
                .setInterpolator(new AccelerateInterpolator(0.1f))
                .alpha(DESELECTED_ICON_APLHA)
                .scaleX(DESELECTED_ICON_SCALE)
                .scaleY(DESELECTED_ICON_SCALE);
    }

    private void animateSelected() {
        selectedIcon.animate()
                .setDuration(mShortAnimationDuration)
                .setInterpolator(new OvershootInterpolator(5))
                .alpha(SELECTED_ICON_APLHA)
                .scaleX(SELECTED_ICON_SCALE)
                .scaleY(SELECTED_ICON_SCALE);
    }

    @Override
    public void setSelectable(boolean selectable) {
        if (selectable) {
            selectedIcon.setVisibility(VISIBLE);
        } else {
            image.setOnClickListener(view -> {
            });
            selectedIcon.setVisibility(GONE);
        }
    }

}
