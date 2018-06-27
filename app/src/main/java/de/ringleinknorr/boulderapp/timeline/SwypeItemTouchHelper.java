package de.ringleinknorr.boulderapp.timeline;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class SwypeItemTouchHelper extends ItemTouchHelper {

    private static class SimpleSwypeCallback extends SimpleCallback {

        private OnItemSwypedListener onItemSwypedListener;

        public SimpleSwypeCallback(@NonNull OnItemSwypedListener onItemSwypedListener) {
            super(0, ItemTouchHelper.LEFT);
            this.onItemSwypedListener = onItemSwypedListener;
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            onItemSwypedListener.onItemSwyped(viewHolder.getAdapterPosition());
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            viewHolder.itemView.setAlpha(Math.max(1f + (dX / 300), 0.2f));
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    public SwypeItemTouchHelper(OnItemSwypedListener onItemSwypedListener) {
        super(new SimpleSwypeCallback(onItemSwypedListener));
    }

    public interface OnItemSwypedListener {
        void onItemSwyped(int position);
    }

}
