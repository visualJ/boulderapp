package de.ringleinknorr.boulderapp;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.objectbox.BoxStore;

public class App extends Application implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    private BoxStore boxStore;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        boxStore = MyObjectBox.builder().androidContext(App.this).build();
        DaggerAppDIComponent.builder().boxStore(boxStore).build().inject(this);
        DBMockData.createMockData(this, boxStore);
        mContext = this;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    public static Context getContext(){
        return mContext;
    }
}
