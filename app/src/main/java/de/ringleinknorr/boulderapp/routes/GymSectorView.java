package de.ringleinknorr.boulderapp.routes;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import butterknife.ButterKnife;
import de.ringleinknorr.boulderapp.R;


public class GymSectorView extends ConstraintLayout {

    public GymSectorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_gym_sector, this);
        ButterKnife.bind(this);
        setLayoutParams(new Constraints.LayoutParams(0, -2));
    }
}
