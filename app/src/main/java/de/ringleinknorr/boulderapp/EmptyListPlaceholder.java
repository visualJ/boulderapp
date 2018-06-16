package de.ringleinknorr.boulderapp;

import android.content.Context;
import android.support.constraint.Constraints;
import android.view.Gravity;

public class EmptyListPlaceholder extends android.support.v7.widget.AppCompatTextView {
    public EmptyListPlaceholder(Context context) {
        super(context);
        setGravity(Gravity.CENTER);
        setPadding(50, 50, 50, 50);
        setLayoutParams(new Constraints.LayoutParams(Constraints.LayoutParams.MATCH_PARENT, Constraints.LayoutParams.MATCH_PARENT));
    }
}
