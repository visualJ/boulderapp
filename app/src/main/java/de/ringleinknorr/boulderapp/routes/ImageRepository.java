package de.ringleinknorr.boulderapp.routes;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import de.ringleinknorr.boulderapp.R;

public class ImageRepository {

    private static String url = "http://www.mi.hs-rm.de/~bring002/bolder/images/";

    @Inject
    public ImageRepository() {

    }

    public void loadImage(String imageId, ImageView view) {
        Picasso.get().load(url + imageId).placeholder(R.drawable.ic_load).error(R.drawable.ic_empty).into(view);
    }
}
