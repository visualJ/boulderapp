package de.ringleinknorr.boulderapp.dependencyinjection;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import de.ringleinknorr.boulderapp.App;
import de.ringleinknorr.boulderapp.annotations.DataUrl;
import de.ringleinknorr.boulderapp.annotations.ImageUrl;
import de.ringleinknorr.boulderapp.annotations.ThumbnailUrl;
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

        @BindsInstance
        Builder dataUrl(@DataUrl String dataUrl);

        AppDIComponent build();
    }
}
