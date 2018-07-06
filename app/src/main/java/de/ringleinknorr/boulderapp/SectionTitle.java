package de.ringleinknorr.boulderapp;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SectionTitle<T extends View> extends LinearLayoutCompat {

    private TextView title;
    private T view;

    public SectionTitle(Context context, T view) {
        super(context);
        this.title = new TextView(context);
        this.view = view;
        title.setPadding(10, 20, 0, 0);
        title.setGravity(Gravity.LEFT);
        setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        setOrientation(VERTICAL);
        addView(title);
        addView(view);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public T getView() {
        return view;
    }
}
