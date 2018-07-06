package de.ringleinknorr.boulderapp.routes;

import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ImageRepository {

    private static String IMAGE_URL = "http://www.mi.hs-rm.de/~bring002/bolder/images/";
    private static String THUMB_URL = "http://www.mi.hs-rm.de/~bring002/bolder/images/thumbnails/";

    public static String imageUrl(String imageId) {
        return IMAGE_URL + imageId;
    }

    public static String thumbnailUrl(String imageId) {
        return THUMB_URL + imageId;
    }

    @Inject
    public ImageRepository() {

    }

    public void loadImageWithThumbnailPlaceholder(String imageId, ImageView view) {
        Picasso.get().load(thumbnailUrl(imageId)).into(view, new Callback() {
            @Override
            public void onSuccess() {
                Picasso.get().load(imageUrl(imageId)).placeholder(view.getDrawable()).into(view);
            }

            @Override
            public void onError(Exception e) {
                onSuccess();
            }
        });
    }

    public void loadImage(String imageId, ImageView view) {
        Picasso.get().load(imageUrl(imageId)).into(view);
    }

    public void fetchImage(String imageId) {
        Picasso.get().load(imageUrl(imageId)).fetch();
    }

    public void loadThumbnail(String imageId, ImageView view) {
        Picasso.get().load(thumbnailUrl(imageId)).into(view);
    }
}
