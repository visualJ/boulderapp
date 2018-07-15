package de.ringleinknorr.boulderapp.repositories;

import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.ringleinknorr.boulderapp.annotations.ImageUrl;
import de.ringleinknorr.boulderapp.annotations.ThumbnailUrl;

@Singleton
public class ImageRepository {

    private String imageUrl;
    private String thumbUrl;

    @Inject
    public ImageRepository(@ImageUrl String imageUrl, @ThumbnailUrl String thumbUrl) {
        this.imageUrl = imageUrl;
        this.thumbUrl = thumbUrl;
    }

    private String imageUrl(String imageId) {
        return imageUrl + imageId;
    }

    private String thumbnailUrl(String imageId) {
        return thumbUrl + imageId;
    }

    /**
     * Loads an image into a view asynchronously. Before the full size image is loaded,
     * a thumbanil is displayed as a placeholder. This makes images seem to load faster for the user.
     *
     * @param imageId The images id.
     * @param view    The view to load the image into.
     */
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

    /**
     * Loads an image into the given view asynchronously.
     *
     * @param imageId The images id
     * @param view    the view to load the image into.
     */
    public void loadImage(String imageId, ImageView view) {
        Picasso.get().load(imageUrl(imageId)).into(view);
    }

    /**
     * Fetch an image to load it into chache eagerly.
     *
     * @param imageId The images id.
     */
    public void fetchImage(String imageId) {
        Picasso.get().load(imageUrl(imageId)).fetch();
    }

    /**
     * Loads an image thumbnail into the given view asynchronously.
     * @param imageId The images id
     * @param view the view to load the image into.
     */
    public void loadThumbnail(String imageId, ImageView view) {
        Picasso.get().load(thumbnailUrl(imageId)).into(view);
    }
}
