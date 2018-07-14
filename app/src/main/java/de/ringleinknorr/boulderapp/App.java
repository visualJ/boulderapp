package de.ringleinknorr.boulderapp;

import android.app.Application;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import de.ringleinknorr.boulderapp.dependencyinjection.DaggerAppDIComponent;
import de.ringleinknorr.boulderapp.models.MyObjectBox;
import io.objectbox.BoxStore;

public class App extends Application implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    private BoxStore boxStore;

    @Override
    public void onCreate() {
        super.onCreate();
        boxStore = MyObjectBox.builder().androidContext(App.this).build();
        DaggerAppDIComponent.builder()
                .boxStore(boxStore)
                .imageUrl(getResources().getString(R.string.url_images))
                .thumbnailUrl(getResources().getString(R.string.url_thumbnail))
                .dataUrl(getResources().getString(R.string.url_data))
                .build().inject(this);
        DBMockData.createMockData(this, boxStore);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
