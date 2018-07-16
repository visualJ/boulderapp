package de.ringleinknorr.boulderapp.dependencyinjection;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import de.ringleinknorr.boulderapp.annotations.DataUrl;
import de.ringleinknorr.boulderapp.fragments.NewSessionFragment;
import de.ringleinknorr.boulderapp.fragments.RouteFragment;
import de.ringleinknorr.boulderapp.fragments.RouteSearchFragment;
import de.ringleinknorr.boulderapp.fragments.SessionFragment;
import de.ringleinknorr.boulderapp.fragments.StatisticsFragment;
import de.ringleinknorr.boulderapp.fragments.TimelineFragment;
import de.ringleinknorr.boulderapp.repositories.GymService;
import de.ringleinknorr.boulderapp.repositories.RouteService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public abstract class AppDIModule {
    @ContributesAndroidInjector
    @Singleton
    abstract TimelineFragment timelineFragment();

    @ContributesAndroidInjector
    @Singleton
    abstract NewSessionFragment newSessionFragment();

    @ContributesAndroidInjector
    @Singleton
    abstract SessionFragment sessionFragment();

    @ContributesAndroidInjector
    abstract StatisticsFragment statisticsFragment();

    @ContributesAndroidInjector
    @Singleton
    abstract RouteSearchFragment routeSearchFragment();

    @ContributesAndroidInjector
    @Singleton
    abstract RouteFragment routeFragment();

    @Provides
    static RouteService getRouteService(@DataUrl String dataUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(dataUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(RouteService.class);
    }

    @Provides
    static GymService getGymService(@DataUrl String dataUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(dataUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(GymService.class);
    }
}
