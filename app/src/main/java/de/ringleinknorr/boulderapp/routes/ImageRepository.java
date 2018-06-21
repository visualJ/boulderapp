package de.ringleinknorr.boulderapp.routes;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import de.ringleinknorr.boulderapp.R;

public class ImageRepository {


    @Inject
    public ImageRepository() {

    }

    public void loadImage(String imageId, ImageView view) {
        Picasso.get().load(R.drawable.route_test).resize(100, 100).centerCrop().into(view);
    }
}
