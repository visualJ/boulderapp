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

    public String imageUrl(String imageId) {
        return imageUrl + imageId;
    }

    public String thumbnailUrl(String imageId) {
        return thumbUrl + imageId;
    }

    @Inject
    public ImageRepository(@ImageUrl String imageUrl, @ThumbnailUrl String thumbUrl) {
        this.imageUrl = imageUrl;
        this.thumbUrl = thumbUrl;
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
