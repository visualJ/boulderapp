package de.ringleinknorr.boulderapp;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import io.objectbox.BoxStore;

@Component(modules = {AndroidSupportInjectionModule.class, AppDIModule.class})
public interface AppDIComponent extends AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder boxStore(BoxStore boxStore);

        @BindsInstance
        Builder imageUrl(@ImageUrl String imageUrl);

        @BindsInstance
        Builder thumbnailUrl(@ThumbnailUrl String thumbnailUrl);

        AppDIComponent build();
    }
}
