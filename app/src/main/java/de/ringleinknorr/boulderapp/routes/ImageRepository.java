package de.ringleinknorr.boulderapp.routes;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ImageRepository {

    private static String url = "http://www.mi.hs-rm.de/~bring002/bolder/images/";
    private static String thumbUrl = "http://www.mi.hs-rm.de/~bring002/bolder/images/thumbnails/";

    @Inject
    public ImageRepository() {

    }

    public void loadImage(String imageId, ImageView view) {
        Picasso.get().load(url + imageId).into(view);
    }

    public void fetchImage(String imageId) {
        Picasso.get().load(url + imageId).fetch();
    }

    public void loadThumbnail(String imageId, ImageView view) {
        Picasso.get().load(thumbUrl + imageId).into(view);
    }
}
